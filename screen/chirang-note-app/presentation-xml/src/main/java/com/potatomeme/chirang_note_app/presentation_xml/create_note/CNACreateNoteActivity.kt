package com.potatomeme.chirang_note_app.presentation_xml.create_note

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.potatomeme.chirang_note_app.domain.usecase.DeleteNoteUseCase
import com.potatomeme.chirang_note_app.domain.usecase.InsertNoteUseCase
import com.potatomeme.chirang_note_app.presentation_xml.R
import com.potatomeme.chirang_note_app.presentation_xml.databinding.ActivityCnaCreateNoteBinding
import com.potatomeme.chirang_note_app.presentation_xml.databinding.LayoutAddUrlBinding
import com.potatomeme.chirang_note_app.presentation_xml.databinding.LayoutDeleteNoteBinding
import com.potatomeme.chirang_note_app.presentation_xml.mapper.NoteMapper.toDomain
import com.potatomeme.chirang_note_app.presentation_xml.model.ParcelableNote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class CNACreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCnaCreateNoteBinding
    private val activityStartForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when {
                result.data?.action == Intent.ACTION_PICK && result.resultCode == RESULT_OK -> {
                    //todo image
                    val uri = result.data?.data
                    uri?.let {
                        try {
                            binding.imageNote.setImageBitmap(BitmapFactory.decodeFile(getPathFromUri(uri)))
                            binding.imageNote.visibility = View.VISIBLE
                            binding.imageRemoveImage.visibility = View.VISIBLE
                            selectedImagePath = getPathFromUri(uri) ?: ""
                        } catch (e: Exception) {
                            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                selectImage()
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }

    @Inject
    lateinit var insertNoteUseCase: InsertNoteUseCase

    @Inject
    lateinit var deleteNoteUseCase: DeleteNoteUseCase

    private val dialogAddURL: AlertDialog by lazy {
        val builder = AlertDialog.Builder(this)
        val addUrlBinding = LayoutAddUrlBinding.inflate(layoutInflater)
        builder.setView(addUrlBinding.root)
        val dialog = builder.create()
        if (dialog.window != null != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        }
        addUrlBinding.inputURL.requestFocus()
        addUrlBinding.textAdd.setOnClickListener {
            val inputURLStr = addUrlBinding.inputURL.text.toString().trim()
            if (inputURLStr.isEmpty()) {
                Toast.makeText(this@CNACreateNoteActivity, "Enter URL", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.WEB_URL.matcher(inputURLStr).matches()) {
                Toast.makeText(this@CNACreateNoteActivity, "Enter valid URL", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.textWebURL.text = addUrlBinding.inputURL.text.toString()
                binding.layoutWebURL.visibility = View.VISIBLE
                dialog.dismiss()
            }
        }
        addUrlBinding.textCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog
    }
    private val dialogDeleteNote: AlertDialog by lazy {
        val builder = AlertDialog.Builder(this)
        val deleteNoteBinding = LayoutDeleteNoteBinding.inflate(layoutInflater)
        builder.setView(deleteNoteBinding.root)
        val dialog = builder.create()
        if (dialog.window != null) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        }
        deleteNoteBinding.textDeleteNote.setOnClickListener {
            //todo delete note
            lifecycleScope.launch {
                alreadyAvailableNote?.let {
                    deleteNoteUseCase.invoke(it.toDomain())
                } ?: return@launch
                dialog.dismiss()
                finish()
            }
        }
        deleteNoteBinding.textCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog
    }

    private var alreadyAvailableNote: ParcelableNote? = null
    private var selectedNoteColor = "#333333"
    private var selectedImagePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCnaCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initMiscellaneous()
        setSubtitleIndicatorColor()
    }

    private fun initViews() {
        binding.textDateTime.text =
            SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.KOREA).format(Date().time)
        binding.imageSave.setOnClickListener {
            saveNote()
        }

        if (intent.getBooleanExtra("isViewOrUpdate", false)) {
            alreadyAvailableNote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("note", ParcelableNote::class.java)
            } else {
                intent.getParcelableExtra("note")
            }
            setViewOrUpdateNote()
        }

        binding.imageRemoveWebURL.setOnClickListener {
            binding.textWebURL.text = null
            binding.layoutWebURL.visibility = View.GONE
        }
        binding.imageRemoveImage.setOnClickListener {
            binding.imageNote.setImageDrawable(null)
            binding.imageNote.visibility = View.GONE
            binding.imageRemoveImage.visibility = View.GONE
        }

        binding.imageBack.setOnClickListener {
            finish()
        }

        //note add 후 들어왔을때
        if (intent.getBooleanExtra("isFromQuickActions", false)) {
            val type = intent.getStringExtra("quickActionType")
            if (type != null) {
                if (type == "image") {
                    selectedImagePath = intent.getStringExtra("imagePath") ?: ""
                    binding.imageNote.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath))
                    binding.imageNote.visibility = View.VISIBLE
                    binding.imageRemoveImage.visibility = View.VISIBLE
                } else if (type == "URL") {
                    binding.textWebURL.text = intent.getStringExtra("URL")
                    binding.layoutWebURL.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun setViewOrUpdateNote() {
        binding.inputNoteTitle.setText(alreadyAvailableNote?.title)
        binding.inputNoteSubtitle.setText(alreadyAvailableNote?.subtitle)
        binding.inputNoteText.setText(alreadyAvailableNote?.noteText)
        binding.textDateTime.text = alreadyAvailableNote?.dateTime

        val imagePathStr = alreadyAvailableNote?.imagePath
        if (imagePathStr != null && imagePathStr.trim().isNotEmpty()) {
            binding.imageNote.setImageBitmap(BitmapFactory.decodeFile(imagePathStr))
            binding.imageNote.visibility = View.VISIBLE
            binding.imageRemoveImage.visibility = View.VISIBLE
            selectedImagePath = imagePathStr
        }
        val webURLStr = alreadyAvailableNote?.webLink
        if (webURLStr != null && webURLStr.trim().isNotEmpty()) {
            binding.textWebURL.text = webURLStr
            binding.layoutWebURL.visibility = View.VISIBLE
        }
    }

    private fun saveNote() {
        val noteTitle = binding.inputNoteTitle.text.toString().trim()
        val noteSubtitle = binding.inputNoteSubtitle.text.toString().trim()
        val noteText = binding.inputNoteText.text.toString().trim()
        val noteDateTime = binding.textDateTime.text.toString().trim()

        if (noteTitle.isEmpty()) {
            Toast.makeText(this, "Note title can't be empty!", Toast.LENGTH_SHORT).show()
            return
        } else if (noteSubtitle.isEmpty() && noteText.isEmpty()) {
            Toast.makeText(this, "Note can't be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        val note = ParcelableNote(
            id =  alreadyAvailableNote?.id ?: 0,
            title = noteTitle,
            dateTime = noteDateTime,
            subtitle = noteSubtitle,
            noteText = noteText,
            imagePath = selectedImagePath,
            color = selectedNoteColor,
            webLink = if (binding.layoutWebURL.visibility == View.VISIBLE) binding.textWebURL.getText()
                .toString() else null
        )

        lifecycleScope.launch {
            insertNoteUseCase.invoke(note.toDomain())
            finish()
        }
    }

    private fun initMiscellaneous() {
        val bottomSheetBehavior: BottomSheetBehavior<LinearLayout> =
            BottomSheetBehavior.from(binding.layoutMiscellaneous.root)
        binding.layoutMiscellaneous.textMiscellaneous.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        binding.layoutMiscellaneous.viewColor1.setOnClickListener {
            selectedNoteColor = "#333333"
            binding.layoutMiscellaneous.imageColor1.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }
        binding.layoutMiscellaneous.viewColor2.setOnClickListener {
            selectedNoteColor = "#FDBE3B"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }
        binding.layoutMiscellaneous.viewColor3.setOnClickListener {
            selectedNoteColor = "#FF4842"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }
        binding.layoutMiscellaneous.viewColor4.setOnClickListener {
            selectedNoteColor = "#3A52FC"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleIndicatorColor()
        }
        binding.layoutMiscellaneous.viewColor5.setOnClickListener {
            selectedNoteColor = "#000000"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(R.drawable.ic_done)
            setSubtitleIndicatorColor()
        }

        if (alreadyAvailableNote != null) {
            val noteColorCode = alreadyAvailableNote?.color
            if (noteColorCode != null && noteColorCode.trim().isNotEmpty()) {
                when (noteColorCode) {
                    "#FDBE3B" -> binding.layoutMiscellaneous.imageColor2.performClick()
                    "#FF4842" -> binding.layoutMiscellaneous.imageColor3.performClick()
                    "#3A52FC" -> binding.layoutMiscellaneous.imageColor4.performClick()
                    "#000000" -> binding.layoutMiscellaneous.imageColor5.performClick()
                }
            }
        }

        binding.layoutMiscellaneous.layoutAddImage.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(permission)
            } else {
                selectImage()
            }
        }
        binding.layoutMiscellaneous.layoutAddUrl.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            showAddURLDialog()
        }
        if (alreadyAvailableNote != null) {
            binding.layoutMiscellaneous.layoutDeleteNote.visibility = View.VISIBLE
            binding.layoutMiscellaneous.layoutDeleteNote.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                showDeleteNoteDialog()
            }
        }
    }


    private fun showAddURLDialog() {
        dialogAddURL.show()
    }

    private fun showDeleteNoteDialog() {
        dialogDeleteNote.show()
    }


    private fun setSubtitleIndicatorColor() {
        (binding.viewSubtitleIndicator.background as GradientDrawable).apply {
            setColor(Color.parseColor(selectedNoteColor))
        }
    }

    //이미지 선택창으로 이동
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (intent.resolveActivity(packageManager) != null) {
            activityStartForResult.launch(intent)
        }
    }

    //이미지 path 가져오기
    private fun getPathFromUri(contentUri: Uri): String? {
        val filePath: String?
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            filePath = contentUri.path
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }


    companion object {
        private const val TAG = "CNACreateNoteActivity"
    }
}