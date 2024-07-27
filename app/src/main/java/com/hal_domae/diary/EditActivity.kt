package com.hal_domae.diary

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hal_domae.diary.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
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