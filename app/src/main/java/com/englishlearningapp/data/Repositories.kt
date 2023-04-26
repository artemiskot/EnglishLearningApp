package com.englishlearningapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
class WordRepository(private val wordDao: WordDao) {

    val readAllData: Flow<List<Word>> = wordDao.readAllData()

    suspend fun addWord(word: Word){
        wordDao.insertWord(word)
    }

    val getRandomWord: Flow<Word> = wordDao.getRandomWord()

    val getRandomWord4: Flow<List<Word>> = wordDao.getRandomWord4()
    // Add the following method to WordRepository
    fun getWordsByModule(moduleId: Int): LiveData<List<Word>> {
        return wordDao.getWordsByModule(moduleId)
    }
    fun getExactWord(english:String): Word = wordDao.getExactWord(english)

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }
}
class ModuleRepository(private val moduleDao: ModuleDao) {
    fun getAllModules() = moduleDao.getAllModules()
    fun getModuleById(moduleId: Int) = moduleDao.getModuleById(moduleId)
    suspend fun insertModule(module: Module) = moduleDao.insertModule(module)
    suspend fun updateModule(module: Module) = moduleDao.updateModule(module)
    suspend fun deleteModule(module: Module) = moduleDao.deleteModule(module)
}

class ModuleUserRepository(private val moduleUserDao: ModuleUserDao) {
    fun getAllModuleUsers() = moduleUserDao.getAllModuleUsers()
    fun getModuleUsersByUserId(userId: Int) = moduleUserDao.getModuleUsersByUserId(userId)
    fun getModuleUsersByModuleId(moduleId: Int) = moduleUserDao.getModuleUsersByModuleId(moduleId)
    fun getModuleUser(userId: Int, moduleId: Int) = moduleUserDao.getModuleUser(userId, moduleId)
    suspend fun insertModuleUser(moduleUser: ModuleUser) = moduleUserDao.insertModuleUser(moduleUser)
    suspend fun updateModuleUser(moduleUser: ModuleUser) = moduleUserDao.updateModuleUser(moduleUser)
    suspend fun deleteModuleUser(moduleUser: ModuleUser) = moduleUserDao.deleteModuleUser(moduleUser)
}

class UserRepository(private val userDao: UserDao) {
    fun getAllUsers() = userDao.getAllUsers()
    fun getUserById(userId: Int) = userDao.getUserById(userId)
    fun getUserByEmailAndPassword(email: String) = userDao.getUserByEmailAndPassword(email)
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
}

class UserLearnStatisticsRepository(private val userLearnStatisticsDao: UserLearnStatisticsDao) {
    fun getAllUserLearnStatistics() = userLearnStatisticsDao.getAllUserLearnStatistics()
    fun getUserLearnStatisticsByUserId(userId: Int) = userLearnStatisticsDao.getUserLearnStatisticsByUserId(userId)
    fun getUserLearnStatisticsByLearnStatisticsId(learnStatisticsId: Int) = userLearnStatisticsDao.getUserLearnStatisticsByLearnStatisticsId(learnStatisticsId)
    fun getUserLearnStatistics(userId: Int, learnStatisticsId: Int) = userLearnStatisticsDao.getUserLearnStatistics(userId, learnStatisticsId)
    suspend fun insertUserLearnStatistics(userLearnStatistics: UserLearnStatistics) = userLearnStatisticsDao.insertUserLearnStatistics(userLearnStatistics)
    suspend fun updateUserLearnStatistics(userLearnStatistics: UserLearnStatistics) = userLearnStatisticsDao.updateUserLearnStatistics(userLearnStatistics)
    suspend fun deleteUserLearnStatistics(userLearnStatistics: UserLearnStatistics) = userLearnStatisticsDao.deleteUserLearnStatistics(userLearnStatistics)
}

class LearnStatisticsRepository(private val learnStatisticsDao: LearnStatisticsDao) {
    fun getAllLearnStatistics() = learnStatisticsDao.getAllLearnStatistics()
    fun getLearnStatisticsById(learnStatisticsId: Int) = learnStatisticsDao.getLearnStatisticsById(learnStatisticsId)
    suspend fun insertLearnStatistics(learnStatistics: LearnStatistics) = learnStatisticsDao.insertLearnStatistics(learnStatistics)
    suspend fun updateLearnStatistics(learnStatistics: LearnStatistics) = learnStatisticsDao.updateLearnStatistics(learnStatistics)
    suspend fun deleteLearnStatistics(learnStatistics: LearnStatistics) = learnStatisticsDao.deleteLearnStatistics(learnStatistics)
}

class UserGameStatisticsRepository(private val userGameStatisticsDao: UserGameStatisticsDao) {
    fun getAllUserGameStatistics() = userGameStatisticsDao.getAllUserGameStatistics()
    fun getUserGameStatisticsByUserId(userId: Int) = userGameStatisticsDao.getUserGameStatisticsByUserId(userId)
    fun getUserGameStatisticsByGameStatisticsId(gameStatisticsId: Int) = userGameStatisticsDao.getUserGameStatisticsByGameStatisticsId(gameStatisticsId)

    fun getUserGameStatistics(userId: Int, gameStatisticsId: Int) = userGameStatisticsDao.getUserGameStatistics(userId, gameStatisticsId)

    suspend fun insertUserGameStatistics(userGameStatistics: UserGameStatistics) = userGameStatisticsDao.insertUserGameStatistics(userGameStatistics)

    suspend fun updateUserGameStatistics(userGameStatistics: UserGameStatistics) = userGameStatisticsDao.updateUserGameStatistics(userGameStatistics)

    suspend fun deleteUserGameStatistics(userGameStatistics: UserGameStatistics) = userGameStatisticsDao.deleteUserGameStatistics(userGameStatistics)
}


class GameStatisticsRepository(private val gameStatisticsDao: GameStatisticsDao) {
    fun getAllGameStatistics() = gameStatisticsDao.getAllGameStatistics()
    fun getGameStatisticsById(gameStatisticsId: Int) = gameStatisticsDao.getGameStatisticsById(gameStatisticsId)
    suspend fun insertGameStatistics(gameStatistics: GameStatistics) = gameStatisticsDao.insertGameStatistics(gameStatistics)
    suspend fun updateGameStatistics(gameStatistics: GameStatistics) = gameStatisticsDao.updateGameStatistics(gameStatistics)
    suspend fun deleteGameStatistics(gameStatistics: GameStatistics) = gameStatisticsDao.deleteGameStatistics(gameStatistics)
}

class ModuleWordRepository(private val moduleWordDao: ModuleWordDao){
    fun insertModuleWord(moduleWord: ModuleWord) = moduleWordDao.insert(moduleWord)
    fun delete(moduleWord: ModuleWord) = moduleWordDao.delete(moduleWord)
    fun getWordsByModule(moduleId: Int) = moduleWordDao.getWordsByModule(moduleId)
    fun getModulesByWord(wordId: Int) = moduleWordDao.getModulesByWord(wordId)
}

class FavouriteRepository(private val favouriteDao: FavouriteDao){
    suspend fun insertFavourite(favourite: Favourite) = favouriteDao.insertFavourite(favourite)
    fun getAllFavourites() = favouriteDao.getAllFavourites()
    fun getFavouritesByUserId(userId: Int) = favouriteDao.getFavouritesByUserId(userId)
    fun getFavouritesByWordId(wordId: Int) = favouriteDao.getFavouritesByWordId(wordId)
    fun getFavourite(userId: Int, wordId: Int) = favouriteDao.getFavourite(userId,wordId)
    suspend fun updateFavourite(favourite: Favourite) = favouriteDao.updateFavourite(favourite)
    suspend fun deleteFavourite(favourite: Favourite) = favouriteDao.deleteFavourite(favourite)
}
