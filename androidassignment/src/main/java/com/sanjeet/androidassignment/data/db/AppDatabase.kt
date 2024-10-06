package com.sanjeet.androidassignment.data.dbimport android.content.Contextimport androidx.room.Databaseimport androidx.room.Roomimport androidx.room.RoomDatabaseimport androidx.room.TypeConvertersimport com.sanjeet.androidassignment.data.characterModel.CharacterListimport com.sanjeet.androidassignment.utils.Converters@Database(entities = [CharacterList::class], version = 1)@TypeConverters(Converters::class)abstract class AppDatabase : RoomDatabase() {    abstract fun bookMarkDao(): BookMarDao    companion object {        @Volatile        private var INSTANCE: AppDatabase? = null        fun getDatabase(context: Context): AppDatabase {            return INSTANCE ?: synchronized(this) {                val instance = Room.databaseBuilder(                    context.applicationContext,                    AppDatabase::class.java,                    "app_database"                ).build()                INSTANCE = instance                instance            }        }    }}