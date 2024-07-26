package com.hal_domae.diary.recyclerview

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// データベースを管理するツール
class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "diary.sqlite"
        private const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let{
            // CREATE
            it.execSQL("CREATE TABLE diary_items(diary_date TEXT PRIMARY KEY, diary_text TEXT)")

            // テスト用のINSERT
            it.execSQL("INSERT INTO diary_items VALUES('2024/01/01','テスト1')")
            it.execSQL("INSERT INTO diary_items VALUES('2024/02/01','テスト2')")
        }
    }

    // テーブルを更新する際に必要
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}