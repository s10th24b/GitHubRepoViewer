package kr.s10th24b.app.githubrepoviewer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        val create =
            """create table searchHistory (number integer primary key, content text, datetime integer)"""
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertSearchHistory(searchHistory: SearchHistory) {
        val values = ContentValues()
        values.put("content",searchHistory.search)
        values.put("datetime",searchHistory.dateTime)

        val wd = writableDatabase
        wd.insert("searchhistory",null,values)
        wd.close()
    }

    fun selectSearchHistory(): MutableList<SearchHistory> {
        val list = mutableListOf<SearchHistory>()
        val select = "select * from searchhistory"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)
        while (cursor.moveToNext()) {
            val number = cursor.getLong(cursor.getColumnIndex("number"))
            val search = cursor.getString(cursor.getColumnIndex("content"))
            val dateTime = cursor.getLong(cursor.getColumnIndex("datetime"))
            list.add(SearchHistory(number,search,dateTime))
        }
        cursor.close()
        rd.close()
        return list
    }

    fun updateSearchHistory(searchHistory: SearchHistory) {
        val values = ContentValues()
        values.put("content",searchHistory.search)
        values.put("datetime",searchHistory.dateTime)

        val wd = writableDatabase
        wd.update("searchhistory",values,"number = ${searchHistory.number}",null)
        wd.close()
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        val delete = "delete from searchhistory where number = ${searchHistory.number}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }
}

data class SearchHistory(val number: Long?, val search: String, val dateTime: Long)