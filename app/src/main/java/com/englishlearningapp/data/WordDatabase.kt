package com.englishlearningapp.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.englishlearningapp.BuildConfig
import com.englishlearningapp.DataGenerator
import com.englishlearningapp.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executors

@Database(entities = [Word::class], version = WordDatabase.VERSION)
abstract class WordDatabase : RoomDatabase() {

    /**
     * Returns the DAO for this application.
     * @return The DAO for this application.
     */
    abstract fun getAppDao(): WordDao

    companion object {

        private const val TAG = "WordDatabase"

        const val VERSION = 1
        private const val DATABASE_NAME = "word_table.db"

        @Volatile
        private var instance: WordDatabase? = null

        /**
         * Gets and returns the database instance if exists; otherwise, builds a new database.
         * @param context The context to access the application context.
         * @return The database instance.
         */
        fun getInstance(context: Context): WordDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        /**
         * Creates and returns the callback object to execute when the database is first created.
         * @return The callback object to execute when the database is first created.
         */
        private fun wordDatabaseCallback(): Callback = object : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d(TAG, "Database has been created.")

                // Throws exception
                CoroutineScope(Dispatchers.IO).launch {
                    instance?.getAppDao()?.let { populateDbAsync(it) }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d(TAG, "Database has been opened.")
            }
        }

        /**
         * Populates the database when it is first created, as a suspended operation.
         * @param appDao The application DAO to execute queries.
         */
        suspend fun populateDbAsync(appDao: WordDao) {

            withContext(Dispatchers.IO) {
                appDao.insertWords(DataGenerator.getUsers())
            }
        }
        /**
         * Builds and returns the database.
         * @param appContext The application context to reference.
         * @return The built database.
         */
        private fun buildDatabase(context: Context): WordDatabase {
            return Room.databaseBuilder(context, WordDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //pre-populate data
                        Executors.newSingleThreadExecutor().execute {
                            instance?.let {
                                it.getAppDao().insertWords(DataGenerator.getUsers())
                            }
                        }
                    }
                })
                .allowMainThreadQueries()
                .build()

        }
    }
}