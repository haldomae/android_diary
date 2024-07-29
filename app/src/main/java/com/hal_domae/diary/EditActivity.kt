package com.hal_domae.diary

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hal_domae.diary.databinding.ActivityEditBinding
import com.hal_domae.diary.recyclerview.DatabaseHelper

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var dbHelper: DatabaseHelper
    private var textFeeling: String? = ""
    private var textAction: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // DatePickerを表示
        binding.selectDate.setOnClickListener{
            val datePicker = DatePickerFragment()
            datePicker.show(supportFragmentManager, "datePicker")
        }
        binding.feelGreat.setOnClickListener { feelClicked(it) }
        binding.feelGood.setOnClickListener { feelClicked(it) }
        binding.feelNormal.setOnClickListener { feelClicked(it) }
        binding.feelBad.setOnClickListener { feelClicked(it) }
        binding.feelAwful.setOnClickListener { feelClicked(it) }

        binding.actionDirectionsRun.setOnClickListener { actionClicked(it) }
        binding.actionMovie.setOnClickListener { actionClicked(it) }
        binding.actionWork.setOnClickListener { actionClicked(it) }
        binding.actionShopping.setOnClickListener { actionClicked(it) }
        binding.actionBedtime.setOnClickListener { actionClicked(it) }

        // データベースの用意
        dbHelper = DatabaseHelper(this@EditActivity)

        binding.saveButton.setOnClickListener {
            // 入力チェック
            if(binding.selectDate.text.isNullOrBlank() || binding.inputDiary.text.isNullOrBlank()){
                Toast.makeText(this, "未入力の項目があります", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // データを保存する
            // データを書き込むときは、writableDatabaseを使う
            dbHelper.writableDatabase.use {db ->
                val value = ContentValues().apply {
                    put("diary_date", binding.selectDate.text.toString())
                    put("diary_text", binding.inputDiary.text.toString())
                }
                // insertで保存する
                //db.insert("diary_items", null, value)
                // 日付が重複してたら置き換える
                // SQLiteDatabase.CONFLICT_REPLACEが重複してたら置き換える
                db.insertWithOnConflict("diary_items", null, value, SQLiteDatabase.CONFLICT_REPLACE)
                startActivity(Intent(this@EditActivity, MainActivity::class.java))
            }
        }

        binding.deleteButton.setOnClickListener {
            // 日付が入力されているかチェック
            if(binding.selectDate.text.isNullOrBlank()){
                Toast.makeText(this, "日付が選択されていません", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            dbHelper.writableDatabase.use { db ->
                val params = arrayOf(binding.selectDate.text.toString())
                // データを削除する際にはdeleteメソッドを使う
                // カラム名 = ? はプレースホルダー
                db.delete("diary_items", "diary_date = ?", params)
                startActivity(Intent(this@EditActivity, MainActivity::class.java))
            }
        }

        intent?.extras?.let {
            binding.selectDate.setText(it.getString("DIARY_DATE"))
            binding.inputDiary.setText(it.getString("DIARY_TEXT"))
        }
    }

    // 気分が選択されたとき
    private fun feelClicked(view: View){
        textFeeling = when(view.id){
            R.id.feel_great -> "今日の気分は最高!!"
            R.id.feel_good -> "今日の気分は良い!"
            R.id.feel_normal -> "今日の気分は普通"
            R.id.feel_bad -> "今日の気分は微妙"
            else -> "今日の気分は最悪..."
        }
        updateDiaryText()
    }

    // 行動が選択されたとき
    private fun actionClicked(view: View){
        textFeeling = when(view.id){
            R.id.action_directions_run -> "一日中仕事をした"
            R.id.action_movie -> "しっかり運動をした"
            R.id.action_work -> "友達と買い物に行った"
            R.id.action_shopping -> "久しぶりに映画を見た"
            else -> "ずっと寝ていた"
        }
        updateDiaryText()
    }

    // テキストを書き換える
    private fun updateDiaryText(){
        binding.inputDiary.setText(getString(R.string.diary_text, textFeeling, textAction))
    }
}