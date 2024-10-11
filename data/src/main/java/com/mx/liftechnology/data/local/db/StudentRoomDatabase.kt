package com.mx.liftechnology.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mx.liftechnology.data.local.dao.StudentDao
import com.mx.liftechnology.data.model.StudentEntity

/** StudentRoomDatabase - Create the database (Students) with the entity
 * @author pelkidev
 * @since 1.0.0
 * */
@Database(entities = [StudentEntity::class], version = 1, exportSchema = false)
abstract class StudentRoomDatabase : RoomDatabase() {

    abstract fun StudentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentRoomDatabase? = null
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