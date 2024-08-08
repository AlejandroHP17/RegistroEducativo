package com.mx.liftechnology.registroeducativo.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mx.liftechnology.registroeducativo.data.local.dao.StudentDao
import com.mx.liftechnology.registroeducativo.data.local.entity.StudentEntity

@Database(entities = [StudentEntity::class], version = 1, exportSchema = false)
abstract class StudentRoomDatabase : RoomDatabase() {

    abstract fun StudentDao(): StudentDao
    companion object{
        @Volatile
        private var INSTANCE : StudentRoomDatabase? = null
        fun getDataBase(context: Context): StudentRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentRoomDatabase::class.java,
                    "category_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}