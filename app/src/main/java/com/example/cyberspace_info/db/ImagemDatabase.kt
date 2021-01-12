package com.example.cyberspace_info.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cyberspace_info.perfil.dao.ImagemDao
import com.example.cyberspace_info.perfil.entity.ImagemEntity

@Database(
    entities = [ImagemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImagemDatabase: RoomDatabase() {

    abstract fun imagemDao() : ImagemDao

    companion object{

        private var INSTANCE: ImagemDatabase? = null

        fun getDataBase(context: Context) :ImagemDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ImagemDatabase::class.java,
                    "Imagem"
                ).build()
            }
            return INSTANCE!!
        }
    }
}