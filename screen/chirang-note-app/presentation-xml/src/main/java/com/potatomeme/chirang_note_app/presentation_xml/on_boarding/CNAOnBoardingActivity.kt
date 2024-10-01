package com.potatomeme.chirang_note_app.presentation_xml.on_boarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.potatomeme.chirang_note_app.domain.usecase.GetNotesUseCase
import com.potatomeme.chirang_note_app.presentation_xml.R
import com.potatomeme.chirang_note_app.presentation_xml.main.CNAMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CNAOnBoardingActivity : AppCompatActivity() {
    @Inject
    lateinit var getNotesUseCase: GetNotesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cna_on_boarding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lifecycleScope.launch {
            val usedUris  = getNotesUseCase.invoke().firstOrNull()?.map { it.imagePath }?.filter { it?.isNotEmpty()?:false }?:emptyList()
            val storedFiles = filesDir.listFiles()?.toList() ?: emptyList()
            storedFiles.forEach { file ->
                if (!usedUris.contains(file.path)) {
                    if (file.delete()) {
                        Log.d("FileCleanup", "Deleted unused file: ${file.name}")
                    } else {
                        Log.d("FileCleanup", "Failed to delete file: ${file.name}")
                    }
                }
            }
            delay(3000)
            startActivity(Intent(this@CNAOnBoardingActivity, CNAMainActivity::class.java))
            finish()
        }
    }
}