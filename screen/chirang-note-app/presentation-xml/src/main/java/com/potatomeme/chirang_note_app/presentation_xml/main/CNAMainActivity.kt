package com.potatomeme.chirang_note_app.presentation_xml.main

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.potatomeme.chirang_note_app.presentation_xml.create_note.CNACreateNoteActivity
import com.potatomeme.chirang_note_app.presentation_xml.databinding.ActivityCnaMainBinding
import com.potatomeme.chirang_note_app.presentation_xml.databinding.LayoutAddUrlBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CNAMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCnaMainBinding
    private val viewModel: CNAMainViewModel by viewModels()
    private val adapter: NotesAdapter by lazy {
        NotesAdapter { note ->
            activityStartForResult.launch(
                Intent(
                    this@CNAMainActivity,
                    CNACreateNoteActivity::class.java
                ).apply {
                    putExtra("isViewOrUpdate", true)
                    putExtra("note", note)
                })
        }
    }

    private val activityStartForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let {
                    val selectedImagePath = getPathFromUri(it)
                    //todo go to add note activity with image path
                    val intent =
                        Intent(this@CNAMainActivity, CNACreateNoteActivity::class.java).apply {
                            putExtra("isFromQuickActions", true)
                            putExtra("quickActionType", "image")
                            putExtra("imagePath", selectedImagePath)
                        }
                    startActivity(intent)
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

    private var searchJob: Job? = null

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            searchJob?.cancel()
            searchJob = CoroutineScope(Dispatchers.Main).launch {
                delay(500)
                s?.let {
                    viewModel.search(it.toString())
                }
            }
        }
    }

    private val dialogAddURL: AlertDialog by lazy {
        val builder = AlertDialog.Builder(this)
        val binding = LayoutAddUrlBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        if (dialog.window != null) {
            val colorDrawable = ColorDrawable(0)
            dialog.window?.setBackgroundDrawable(colorDrawable)
        }
        binding.inputURL.requestFocus()
        val doneAction = {
            val inputURLStr = binding.inputURL.text.toString().trim()
            if (inputURLStr.isEmpty()) {
                Toast.makeText(this, "Enter URL", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.WEB_URL.matcher(inputURLStr).matches()) {
                Toast.makeText(this, "Enter valid URL", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
                //todo go to add note activity
                activityStartForResult.launch(
                    Intent(
                        this@CNAMainActivity,
                        CNACreateNoteActivity::class.java
                    ).apply {
                        putExtra("isFromQuickActions", true)
                        putExtra("quickActionType", "URL")
                        putExtra("URL", inputURLStr)
                    })
            }
        }
        binding.inputURL.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doneAction()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.textAdd.setOnClickListener {
            doneAction()
        }
        binding.textCancel.setOnClickListener { dialog.dismiss() }
        dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCnaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.imageAddNoteMain.setOnClickListener {
            activityStartForResult.launch(Intent(this, CNACreateNoteActivity::class.java))
        }

        binding.notesRecyclerView.apply {
            adapter = this@CNAMainActivity.adapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        binding.inputSearch.addTextChangedListener(textWatcher)
        binding.imageAddNote.setOnClickListener {
            activityStartForResult.launch(Intent(this, CNACreateNoteActivity::class.java))
        }

        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        binding.imageAddImage.setOnClickListener {
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
        binding.imageAddWebLink.setOnClickListener {
            dialogAddURL.show()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    //recycler view
                    viewModel.searchedResult.collectLatest { result ->
                        when (result) {
                            SearchedResult.Init -> {}
                            is SearchedResult.Success -> {
                                adapter.submitList(result.notes)
                            }
                        }
                    }
                }
            }
        }

        binding.root.setOnTouchListener { _, _ ->
            currentFocus?.let { hideKeyboard() }
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

    //이미지 선택창으로 이동
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        try {
            activityStartForResult.launch(intent)
        } catch (e: ActivityNotFoundException) {
            // Display some error message
        }
    }
}