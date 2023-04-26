package com.englishlearningapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.englishlearningapp.DataGenerator
import java.util.concurrent.Executors

@Database(
    entities = [
        Word::class,
        ModuleWord::class,
        Module::class,
        ModuleUser::class,
        User::class,
        UserLearnStatistics::class,
        LearnStatistics::class,
        UserGameStatistics::class,
        GameStatistics::class,
        Favourite::class
    ],
    version = 9,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun moduleWordDao(): ModuleWordDao
    abstract fun moduleDao(): ModuleDao
    abstract fun moduleUserDao(): ModuleUserDao
    abstract fun userDao(): UserDao
    abstract fun userLearnStatisticsDao(): UserLearnStatisticsDao
    abstract fun learnStatisticsDao(): LearnStatisticsDao
    abstract fun userGameStatisticsDao(): UserGameStatisticsDao
    abstract fun gameStatisticsDao(): GameStatisticsDao
    abstract fun favouriteDao(): FavouriteDao


    companion object {
        private const val DATABASE_NAME = "my_database"

        @Volatile
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        fun buildDatabase(context: Context): MyDatabase {
            return Room.databaseBuilder(context, MyDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //pre-populate data
                        Executors.newSingleThreadExecutor().execute {
                            instance?.let {
                                it.wordDao().insertWords(DataGenerator.getWords())
                                it.moduleDao().insertModules(DataGenerator.getModules())
                                it.moduleWordDao().insertModules(DataGenerator.insertWordModule())
                                it.userDao().insertUser(DataGenerator.getModerator())
                            }
                        }
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
