package com.potatomeme.chirang_note_app.presentation_xml.create_note

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.potatomeme.chirang_note_app.presentation_xml.R
import com.potatomeme.chirang_note_app.presentation_xml.databinding.ActivityCnaCreateNoteBinding
import com.potatomeme.chirang_note_app.presentation_xml.databinding.LayoutAddUrlBinding
import com.potatomeme.chirang_note_app.presentation_xml.databinding.LayoutDeleteNoteBinding
import com.potatomeme.chirang_note_app.presentation_xml.model.ParcelableNote
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CNACreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCnaCreateNoteBinding
    private val viewModel: CNACreateNoteViewModel by viewModels()

    private val activityStartForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    viewModel.setSelectedImagePath(uri.toString())
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

    private val dialogAddURL: AlertDialog by lazy {
        val builder = AlertDialog.Builder(this)
        val addUrlBinding = LayoutAddUrlBinding.inflate(layoutInflater)
        builder.setView(addUrlBinding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(0))
        addUrlBinding.inputURL.requestFocus()
        addUrlBinding.textAdd.setOnClickListener {
            val inputURLStr = addUrlBinding.inputURL.text.toString().trim()
            if (inputURLStr.isEmpty()) {
                Toast.makeText(this, "Enter URL", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.WEB_URL.matcher(inputURLStr).matches()) {
                Toast.makeText(this, "Enter valid URL", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.setSelectedWebURL(addUrlBinding.inputURL.text.toString())
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
            alreadyAvailableNote?.let { viewModel.deleteNote(it) } ?: return@setOnClickListener
            dialog.dismiss()
            finish()
        }
        deleteNoteBinding.textCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog
    }

    private val colorPickerImageViews: List<ImageView> by lazy {
        listOf(
            binding.layoutMiscellaneous.imageColor1,
            binding.layoutMiscellaneous.imageColor2,
            binding.layoutMiscellaneous.imageColor3,
            binding.layoutMiscellaneous.imageColor4,
            binding.layoutMiscellaneous.imageColor5,
        )
    }

    private val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    private var alreadyAvailableNote: ParcelableNote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCnaCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initMiscellaneous()
    }

    private fun initViews() {
        binding.textDateTime.text =
            SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.KOREA).format(Date().time)

        binding.imageSave.setOnClickListener { saveNote() }

        if (intent.getBooleanExtra("isViewOrUpdate", false)) {
            alreadyAvailableNote = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("note", ParcelableNote::class.java)
            } else {
                intent.getParcelableExtra("note")
            }
            alreadyAvailableNote?.let { viewModel.whenIntentToNote(it) }
            setViewOrUpdateNote()
        }

        binding.imageRemoveWebURL.setOnClickListener { viewModel.setSelectedWebURL("") }
        binding.imageRemoveImage.setOnClickListener { viewModel.setSelectedImagePath("") }
        binding.imageBack.setOnClickListener { finish() }

        if (intent.getBooleanExtra("isFromQuickActions", false)) {
            val type = intent.getStringExtra("quickActionType")
            when (type) {
                "image" -> viewModel.setSelectedImagePath(intent.getStringExtra("imagePath") ?: "")
                "URL" -> viewModel.setSelectedWebURL(intent.getStringExtra("URL") ?: "")
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.selectedNoteColor.collect {
                        colorPickerImageViews.forEach { iv -> iv.setImageResource(0) }
                        colorPickerImageViews[it].setImageResource(R.drawable.ic_done)
                        setSubtitleIndicatorColor(it)
                    }
                }
                launch {
                    viewModel.selectedImagePath.collect {
                        if (it.isEmpty()) {
                            binding.imageNote.setImageDrawable(null)
                            binding.imageNote.visibility = View.GONE
                            binding.imageRemoveImage.visibility = View.GONE
                        } else {
                            binding.imageNote.setImageURI(it.toUri())
                            binding.imageNote.visibility = View.VISIBLE
                            binding.imageRemoveImage.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    viewModel.selectedWebURL.collect {
                        if (it.isEmpty()) {
                            binding.textWebURL.text = null
                            binding.layoutWebURL.visibility = View.GONE
                        } else {
                            binding.textWebURL.text = it
                            binding.layoutWebURL.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        binding.root.setOnTouchListener { _, _ ->
            if (currentFocus != null) {
                hideKeyboard()
            }
            false
        }
    }

    private fun hideKeyboard() {
        val inputManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun setViewOrUpdateNote() {
        alreadyAvailableNote?.let {
            binding.inputNoteTitle.setText(it.title)
            binding.inputNoteSubtitle.setText(it.subtitle)
            binding.inputNoteText.setText(it.noteText)
            binding.textDateTime.text = it.dateTime
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

        var copyImageFilePath: String? = null
        if (viewModel.imageChanged.value && viewModel.selectedImagePath.value.isNotEmpty()) {
            val strToBitmap = uriToBitmap(Uri.parse(viewModel.selectedImagePath.value))
            val viewToBitMap = viewToBitmap(binding.imageNote)

            val lowBitMap =
                if (strToBitmap == null || strToBitmap.width > viewToBitMap.width) viewToBitMap else strToBitmap
            copyImageFilePath = saveBitmapToFile(lowBitMap)?.path
        }

        val note = ParcelableNote(
            id = alreadyAvailableNote?.id ?: 0,
            title = noteTitle,
            dateTime = noteDateTime,
            subtitle = noteSubtitle,
            noteText = noteText,
            imagePath = copyImageFilePath ?: viewModel.selectedImagePath.value,
            color = COLOR_CODE_LIST[viewModel.selectedNoteColor.value],
            webLink = if (binding.layoutWebURL.visibility == View.VISIBLE) binding.textWebURL.getText()
                .toString() else null
        )
        viewModel.insertNote(note)
        finish()
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun viewToBitmap(view: View): Bitmap {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap =
            Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapToFile(bitmap: Bitmap): Uri? {
        val fileName = "view_image_${System.currentTimeMillis()}.png"
        val file = File(filesDir, fileName)

        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun initMiscellaneous() {
        val bottomSheetBehavior: BottomSheetBehavior<LinearLayout> =
            BottomSheetBehavior.from(binding.layoutMiscellaneous.root)
        binding.layoutMiscellaneous.textMiscellaneous.setOnClickListener {
            bottomSheetBehavior.state =
                if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    BottomSheetBehavior.STATE_EXPANDED
                } else {
                    BottomSheetBehavior.STATE_COLLAPSED
                }
        }

        colorPickerImageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                viewModel.setSelectedNoteColor(index)
            }
        }

        binding.layoutMiscellaneous.layoutAddImage.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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
            dialogAddURL.show()
        }

        if (alreadyAvailableNote != null) {
            //color select
            val noteColorCode = alreadyAvailableNote?.color
            if (noteColorCode != null && noteColorCode.trim().isNotEmpty()) {
                colorPickerImageViews.forEachIndexed { index, imageView ->
                    if (COLOR_CODE_LIST[index] == noteColorCode) {
                        imageView.performClick()
                    }
                }
            }
            //delete button init
            binding.layoutMiscellaneous.layoutDeleteNote.visibility = View.VISIBLE
            binding.layoutMiscellaneous.layoutDeleteNote.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                dialogDeleteNote.show()
            }
        }
    }

    private fun setSubtitleIndicatorColor(idx: Int) {
        (binding.viewSubtitleIndicator.background as GradientDrawable).apply {
            setColor(Color.parseColor(COLOR_CODE_LIST[idx]))
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        try {
            activityStartForResult.launch(intent)
        } catch (e: ActivityNotFoundException) {
            // Display some error message
        }
    }

    companion object {
        val COLOR_CODE_LIST = listOf("#333333", "#FDBE3B", "#FF4842", "#3A52FC", "#000000")
    }
}