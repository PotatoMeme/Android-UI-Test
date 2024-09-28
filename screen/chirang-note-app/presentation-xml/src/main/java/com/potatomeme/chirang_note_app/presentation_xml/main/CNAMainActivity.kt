package com.potatomeme.chirang_note_app.presentation_xml.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
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
import com.potatomeme.chirang_note_app.presentation_xml.databinding.ActivityCnaMainBinding
import com.potatomeme.chirang_note_app.presentation_xml.databinding.LayoutAddUrlBinding
import com.potatomeme.chirang_note_app.presentation_xml.model.ParcelableNote
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
            //todo note item click
        }
    }

    private val activityStartForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when {
                (result.resultCode == REQUEST_CODE_ADD_NOTE || result.resultCode == REQUEST_CODE_UPDATE_NOTE) && result.resultCode == RESULT_OK -> {
                    //todo add note
                    val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getParcelableExtra("note", ParcelableNote::class.java)
                    } else {
                        result.data?.getParcelableExtra("note")
                    }

                    note?.let {
                        viewModel.insertNote(it)
                    }
                }

                result.data?.action == Intent.ACTION_PICK && result.resultCode == RESULT_OK -> {
                    //todo image
                    val uri = result.data?.data
                    uri?.let {
                        try {
                            val selectedImagePath = getPathFromUri(uri)
                            //todo add image at intent
                            //todo go to add note activity
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
        binding.textAdd.setOnClickListener {
            val inputURLStr = binding.inputURL.text.toString().trim()
            if (inputURLStr.isNotEmpty()) {
                Toast.makeText(this, "Enter URL", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.WEB_URL.matcher(inputURLStr).matches()) {
                Toast.makeText(this, "Enter valid URL", Toast.LENGTH_SHORT).show()
            } else {
                dialog.dismiss()
                //todo go to add note activity
            }
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
            //todo go to add note activity
            //activityStartForResult.launch(Intent(this, CNANoteActivity::class.java))
        }

        binding.notesRecyclerView.apply {
            adapter = this@CNAMainActivity.adapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        binding.inputSearch.addTextChangedListener(textWatcher)
        binding.imageAddNote.setOnClickListener {
            //todo go to add note activity
            //activityStartForResult.launch(Intent(this, CNANoteActivity::class.java))
        }
        binding.imageAddImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                selectImage()
            }
        }
        binding.imageAddNote.setOnClickListener {
            showAddURLDialog()
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
    }

    //url dialog 보이기
    private fun showAddURLDialog() {
        dialogAddURL.show()
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
        if (intent.resolveActivity(packageManager) != null) {
            activityStartForResult.launch(intent)
        }
    }

    companion object {
        private const val REQUEST_CODE_ADD_NOTE: Int = 1
        private const val REQUEST_CODE_UPDATE_NOTE: Int = 2
        private const val REQUEST_CODE_SHOW_NOTES: Int = 3
        private const val REQUEST_CODE_SELECT_IMAGE: Int = 4
        private const val REQUEST_CODE_STORAGE_PERMISSION: Int = 5
    }
}