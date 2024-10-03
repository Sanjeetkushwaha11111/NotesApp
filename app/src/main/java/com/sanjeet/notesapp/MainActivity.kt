package com.sanjeet.notesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.sanjeet.notesapp.database.AppDatabase
import com.sanjeet.notesapp.database.NoteDao
import com.sanjeet.notesapp.database.NotesModel
import com.sanjeet.notesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var noteDao: NoteDao
    private lateinit var binding: ActivityMainBinding
    private val currentTime = System.currentTimeMillis()
    private var list = mutableListOf<NotesModel>()
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        binding.todayDate.text = "Today is: $currentTime"
        lifecycleScope.launch(Dispatchers.IO) {
            val db = buildDb()
            noteDao = db.noteDao()
            setupNotesList()
        }
        binding.saveButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                saveNote()
            }
        }
    }

    private suspend fun setupNotesList() {
        list = getNotesList()

        withContext(Dispatchers.Main) {
            notesAdapter = NotesAdapter(list)
            binding.notesList.apply {
                adapter = notesAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }

    }

    private fun getNotesList(): MutableList<NotesModel> {
        return noteDao.getAll()
    }

    private fun buildDb(): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "note-db"
        ).build()
    }

    private suspend fun saveNote() {
        val title = binding.noteTitle.text.toString()
        val content = binding.noteContent.text.toString()

        if (content.isNotEmpty()) {

            val noteId: Long = noteDao.insertAll(
                NotesModel(
                    uid = null, // Room will auto-generate the ID
                    noteTitle = title,
                    noteContent = content,
                    dateTime = currentTime.toString()
                )
            )

            withContext(Dispatchers.Main) {
                // Add the new note to the top of the list
                list.add(
                    0, NotesModel(
                        uid = noteId, // Use the returned ID
                        noteTitle = title,
                        noteContent = content,
                        dateTime = currentTime.toString()
                    )
                )

                notesAdapter.notifyItemInserted(0)
                binding.noteTitle.text?.clear()
                binding.noteContent.text?.clear()
            }
        }
    }


}