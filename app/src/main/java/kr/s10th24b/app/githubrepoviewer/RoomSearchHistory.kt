package kr.s10th24b.app.githubrepoviewer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "orm_search_history")
class RoomSearchHistory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var search: String = ""

    @ColumnInfo(name = "date")
    var dateTime: Long = 0

    @Ignore
    var temp: String = "임시 데이터. 그냥 Ignore 사용법 알려주려고."

    constructor(search: String, dateTime: Long) {
        this.search = search
        this.dateTime = dateTime
    }
}