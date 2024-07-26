package com.hal_domae.diary

import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hal_domae.diary.databinding.ActivityMainBinding
import com.hal_domae.diary.recyclerview.ListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // サンプルデータ
        val sampleData = mutableListOf(
            mapOf ("date" to "2024/01/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/02/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/03/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/04/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/05/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/06/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/07/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/08/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/09/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/10/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/11/01", "text" to "ここに日記のテキストが入ります。"),
            mapOf ("date" to "2024/12/01", "text" to "ここに日記のテキストが入ります。"),
        )
//        binding.diaryList.adapter = SimpleAdapter(
//            this,
//            sampleData,
//            R.layout.list_item,
//            arrayOf("date", "text"),
//            intArrayOf(R.id.date, R.id.text)
//        )

        // RecyclerViewの設定
        // LinearLayoutManagerはリストの並び方、表示方法を設定
        binding.diaryList.layoutManager = LinearLayoutManager(this)
        // データをセット
        binding.diaryList.adapter = ListAdapter(sampleData)

        // 項目に区切り線をつける
        val dividerItemDecoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        binding.diaryList.addItemDecoration(dividerItemDecoration)


    }
}