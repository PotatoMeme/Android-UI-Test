package com.potatomeme.chirang_note_app.presentation_xml.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.potatomeme.chirang_note_app.presentation_xml.databinding.ActivityCnaMainBinding

class CNAMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCnaMainBinding
    private val adapter: NotesAdapter by lazy {
        NotesAdapter{ note, position ->
            //todo note item click
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCnaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
    }

    companion object {
        private const val REQUEST_CODE_ADD_NOTE: Int = 1
        private const val REQUEST_CODE_UPDATE_NOTE: Int = 2
        private const val REQUEST_CODE_SHOW_NOTES: Int = 3
        private const val REQUEST_CODE_SELECT_IMAGE: Int = 4
        private const val REQUEST_CODE_STORAGE_PERMISSION: Int = 5
    }
}