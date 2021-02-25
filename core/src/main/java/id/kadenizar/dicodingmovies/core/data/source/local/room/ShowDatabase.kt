package id.kadenizar.dicodingmovies.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.kadenizar.dicodingmovies.core.data.source.local.entity.ShowEntity
import id.kadenizar.dicodingmovies.core.data.source.local.room.ShowDao

@Database(entities = [ShowEntity::class], version = 1, exportSchema = false)
abstract class ShowDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDao
}