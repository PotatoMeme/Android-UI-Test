package com.potatomeme.chirang_note_app.presentation_xml.create_note

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.potatomeme.chirang_note_app.presentation_xml.R
import com.potatomeme.chirang_note_app.presentation_xml.databinding.ActivityCnaCreateNoteBinding

class CNACreateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCnaCreateNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCnaCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}