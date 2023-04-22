package com.englishlearningapp

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.englishlearningapp.R
import com.englishlearningapp.data.*
import kotlinx.android.synthetic.main.activity_add_word.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddWordActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel
    private lateinit var moduleViewModel: ModuleViewModel
    private lateinit var moduleRepository: ModuleRepository
    private lateinit var moduleWordRepository: ModuleWordRepository
    private lateinit var moduleWordViewModel: ModuleWordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)

        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        moduleRepository = ModuleRepository(MyDatabase.getInstance(this).moduleDao())
        moduleViewModel = ModuleViewModel(moduleRepository)

        moduleWordRepository = ModuleWordRepository(MyDatabase.getInstance(this).moduleWordDao())
        moduleWordViewModel = ModuleWordViewModel(moduleWordRepository)

        val moduleStrings = resources.getStringArray(R.array.ModuleTypes)
        val spinner: Spinner = findViewById(R.id.moduleNameSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, moduleStrings
            )
            spinner.adapter = adapter
        }
        var selectedModule = ""
        var pos = 0
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedModule = moduleStrings[position]
                pos = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        addWordButton.setOnClickListener {
            GlobalScope.launch {
                val englishWord = englishWordEditText.text.toString().trim()
                val russianWord = russianWordEditText.text.toString().trim()
                if (englishWord.isNotEmpty() && russianWord.isNotEmpty() && selectedModule!="") {
                    val newWord = Word(EnglishWord = englishWord, RussianWord = russianWord)
                    wordViewModel.addWord(newWord)
                    delay(500)
                    val tmpId = wordViewModel.getExactWord(newWord.EnglishWord).id
                    moduleWordViewModel.insertModuleWord(
                        ModuleWord(
                            pos+1,
                            tmpId!!
                        )
                    )
                    runOnUiThread {
                        Toast.makeText(this@AddWordActivity,"Слово добавлено", LENGTH_SHORT).show()
                    }
                    finish()
                } else {
                    // Show an error message if any of the fields are empty
                    if (englishWord.isEmpty()) {
                        englishWordEditText.error = "Введите английское слово"
                    }
                    if (russianWord.isEmpty()) {
                        russianWordEditText.error = "Введите русский перевод"
                    }
                    if(selectedModule==""){
                        Toast.makeText(this@AddWordActivity,"Выберите категорию", LENGTH_SHORT)
                    }
                }
            }
        }
    }
}
