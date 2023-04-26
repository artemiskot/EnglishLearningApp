package com.englishlearningapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import androidx.room.Update as Update

//+

@Dao
interface WordDao {
    @Query("SELECT * FROM word_table ORDER BY id ASC")
    fun readAllData() : Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWords(order: List<Word>)

    @Query("SELECT * FROM word_table ORDER BY RANDOM() LIMIT 1")
    fun getRandomWord() : Flow<Word>

    @Query("SELECT DISTINCT * FROM word_table ORDER BY RANDOM() LIMIT 4")
    fun getRandomWord4() : Flow<List<Word>>

    @Query("SELECT word_table.* FROM word_table INNER JOIN module_word_table ON word_table.id = module_word_table.word_id WHERE module_word_table.module_id = :moduleId")
    fun getWordsByModule(moduleId: Int): LiveData<List<Word>>

    @Query("SELECT * FROM word_table WHERE EnglishWord = :english")
    fun getExactWord(english: String): Word

    @Delete
    suspend fun deleteWord(word: Word)
}


//+
@Dao
interface ModuleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModule(module: Module)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModules(module: List<Module>)

    @Query("SELECT * FROM module_table")
    fun getAllModules(): LiveData<List<Module>>

    @Query("SELECT * FROM module_table WHERE id = :moduleId")
    fun getModuleById(moduleId: Int): Flow<Module>

    @Update
    suspend fun updateModule(module: Module)

    @Delete
    suspend fun deleteModule(module: Module)
}
@Dao
interface ModuleWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(moduleWord: ModuleWord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModules(moduleWord: List<ModuleWord>)

    @Delete
    fun delete(moduleWord: ModuleWord)

    @Query("SELECT * FROM word_table INNER JOIN module_word_table ON word_table.id = module_word_table.word_id WHERE module_word_table.module_id = :moduleId")
    fun getWordsByModule(moduleId: Int): Flow<List<Word>>

    @Query("SELECT * FROM module_table INNER JOIN module_word_table ON module_table.id = module_word_table.module_id WHERE module_word_table.word_id = :wordId")
    fun getModulesByWord(wordId: Int): Flow<Module>
}


//+
@Dao
interface ModuleUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModuleUser(moduleUser: ModuleUser)

    @Query("SELECT * FROM module_user_table")
    fun getAllModuleUsers(): Flow<List<ModuleUser>>

    @Query("SELECT * FROM module_user_table WHERE user_id = :userId")
    fun getModuleUsersByUserId(userId: Int): Flow<List<ModuleUser>>

    @Query("SELECT * FROM module_user_table WHERE module_id = :moduleId")
    fun getModuleUsersByModuleId(moduleId: Int): Flow<List<ModuleUser>>

    @Query("SELECT * FROM module_user_table WHERE user_id = :userId AND module_id = :moduleId")
    fun getModuleUser(userId: Int, moduleId: Int): Flow<ModuleUser?>

    @Update
    suspend fun updateModuleUser(moduleUser: ModuleUser)

    @Delete
    suspend fun deleteModuleUser(moduleUser: ModuleUser)
}

//+
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getUserById(userId: Int): Flow<User>

    @Query("SELECT * FROM user_table WHERE email = :email")
    fun getUserByEmailAndPassword(email: String): Flow<User?>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}

//+
@Dao
interface UserLearnStatisticsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserLearnStatistics(userLearnStatistics: UserLearnStatistics)

    @Query("SELECT * FROM user_learn_statistics_table")
    fun getAllUserLearnStatistics(): Flow<List<UserLearnStatistics>>

    @Query("SELECT * FROM user_learn_statistics_table WHERE user_id = :userId")
    fun getUserLearnStatisticsByUserId(userId: Int): Flow<UserLearnStatistics>

    @Query("SELECT * FROM user_learn_statistics_table WHERE learn_statistics_id = :learnStatisticsId")
    fun getUserLearnStatisticsByLearnStatisticsId(learnStatisticsId: Int): Flow<List<UserLearnStatistics>>

    @Query("SELECT * FROM user_learn_statistics_table WHERE user_id = :userId AND learn_statistics_id = :learnStatisticsId")
    fun getUserLearnStatistics(userId: Int, learnStatisticsId: Int): Flow<UserLearnStatistics?>

    @Update
    suspend fun updateUserLearnStatistics(userLearnStatistics: UserLearnStatistics)

    @Delete
    suspend fun deleteUserLearnStatistics(userLearnStatistics: UserLearnStatistics)
}

//+
@Dao
interface LearnStatisticsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLearnStatistics(learnStatistics: LearnStatistics)

    @Query("SELECT * FROM learn_statistics_table")
    fun getAllLearnStatistics(): Flow<List<LearnStatistics>>

    @Query("SELECT * FROM learn_statistics_table WHERE id = :learnStatisticsId")
    fun getLearnStatisticsById(learnStatisticsId: Int): Flow<LearnStatistics>

    @Update
    suspend fun updateLearnStatistics(learnStatistics: LearnStatistics)

    @Delete
    suspend fun deleteLearnStatistics(learnStatistics: LearnStatistics)
}

//+
@Dao
interface UserGameStatisticsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGameStatistics(userGameStatistics: UserGameStatistics)

    @Query("SELECT * FROM user_game_statistics_table")
    fun getAllUserGameStatistics(): Flow<List<UserGameStatistics>>

    @Query("SELECT * FROM user_game_statistics_table WHERE user_id = :userId")
    fun getUserGameStatisticsByUserId(userId: Int): Flow<List<UserGameStatistics>>

    @Query("SELECT * FROM user_game_statistics_table WHERE game_statistics_id = :gameStatisticsId")
    fun getUserGameStatisticsByGameStatisticsId(gameStatisticsId: Int): Flow<List<UserGameStatistics>>

    @Query("SELECT * FROM user_game_statistics_table WHERE user_id = :userId AND game_statistics_id = :gameStatisticsId")
    fun getUserGameStatistics(userId: Int, gameStatisticsId: Int): Flow<UserGameStatistics?>

    @Update
    suspend fun updateUserGameStatistics(userGameStatistics: UserGameStatistics)

    @Delete
    suspend fun deleteUserGameStatistics(userGameStatistics: UserGameStatistics)
}

//+
@Dao
interface GameStatisticsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameStatistics(gameStatistics: GameStatistics)

    @Query("SELECT * FROM game_statistics_table")
    fun getAllGameStatistics(): Flow<List<GameStatistics>>

    @Query("SELECT * FROM game_statistics_table WHERE id = :gameStatisticsId")
    fun getGameStatisticsById(gameStatisticsId: Int): Flow<GameStatistics>

    @Update
    suspend fun updateGameStatistics(gameStatistics: GameStatistics)

    @Delete
    suspend fun deleteGameStatistics(gameStatistics: GameStatistics)
}

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Query("SELECT * FROM favourite_table")
    fun getAllFavourites(): Flow<List<Favourite>>

    @Query("SELECT * FROM favourite_table WHERE user_id = :userId")
    fun getFavouritesByUserId(userId: Int): Flow<List<Favourite>>

    @Query("SELECT * FROM favourite_table WHERE word_id = :wordId")
    fun getFavouritesByWordId(wordId: Int): Flow<List<Favourite>>

    @Query("SELECT * FROM favourite_table WHERE user_id = :userId AND word_id = :wordId")
    fun getFavourite(userId: Int, wordId: Int): Flow<Favourite?>

    @Update
    suspend fun updateFavourite(favourite: Favourite)

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)
}