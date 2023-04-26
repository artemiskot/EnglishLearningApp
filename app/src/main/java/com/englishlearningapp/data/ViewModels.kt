package com.englishlearningapp.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch


class WordViewModel(application: Application): AndroidViewModel(application) {

    val readAllWord: Flow<List<Word>>
    val getRandomWord: Flow<Word>
    val getRandomWord4: Flow<List<Word>>
    private val repository: WordRepository

    init {
        val wordDao = MyDatabase.getInstance(application).wordDao()
        repository = WordRepository(wordDao)
        readAllWord = repository.readAllData
        getRandomWord = repository.getRandomWord
        getRandomWord4 = repository.getRandomWord4
    }

    fun addWord(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWord(Word(word.id,word.EnglishWord.lowercase(),word.RussianWord.lowercase()))
        }
    }

    fun getRandomWord(): Flow<Word> {
        lateinit var tmpWord: Flow<Word>
        tmpWord = repository.getRandomWord
        return tmpWord
    }

    fun wordsPractice(): Flow<List<Word>> {
        lateinit var tmpList: Flow<List<Word>>
        tmpList = repository.getRandomWord4
        return tmpList
    }
    suspend fun <Word> Flow<List<Word>>.flattenToList() =
        flatMapConcat { it.asFlow() }.toList()

    // Add the following method to WordViewModel
    fun getWordsByModule(moduleId: Int): LiveData<List<Word>> {
        return repository.getWordsByModule(moduleId)
    }
    fun getExactWord(english: String): Word{
        return repository.getExactWord(english)
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWord(word)
        }
    }
}
class ModuleViewModel(private val repository: ModuleRepository) : ViewModel() {

    val allModules: LiveData<List<Module>> = repository.getAllModules()

    fun insert(module: Module) = viewModelScope.launch {
        repository.insertModule(module)
    }
}
class ModuleViewModelFactory(private val repository: ModuleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModuleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ModuleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
/*class ModuleViewModel(private val repository: ModuleRepository) : ViewModel() {
    val modules = repository.getAllModules()
    fun getAllModules() = repository.getAllModules()
    fun getModuleById(moduleId: Int) = repository.getModuleById(moduleId)

    fun insertModule(module: Module) {
        viewModelScope.launch { repository.insertModule(module) }
    }

    fun updateModule(module: Module) {
        viewModelScope.launch { repository.updateModule(module) }
    }

    fun deleteModule(module: Module) {
        viewModelScope.launch { repository.deleteModule(module) }
    }
} */

class ModuleUserViewModel(private val repository: ModuleUserRepository) : ViewModel() {

    fun getAllModuleUsers() = repository.getAllModuleUsers()

    fun getModuleUsersByUserId(userId: Int) = repository.getModuleUsersByUserId(userId)

    fun getModuleUsersByModuleId(moduleId: Int) = repository.getModuleUsersByModuleId(moduleId)

    fun getModuleUser(userId: Int, moduleId: Int) = repository.getModuleUser(userId, moduleId)

    fun insertModuleUser(moduleUser: ModuleUser) {
        viewModelScope.launch { repository.insertModuleUser(moduleUser) }
    }

    fun updateModuleUser(moduleUser: ModuleUser) {
        viewModelScope.launch { repository.updateModuleUser(moduleUser) }
    }

    fun deleteModuleUser(moduleUser: ModuleUser) {
        viewModelScope.launch { repository.deleteModuleUser(moduleUser) }
    }
}

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val users = repository.getAllUsers()

    fun getUserById(userId: Int) = repository.getUserById(userId)

    fun getUserByEmail(email: String) = repository.getUserByEmailAndPassword(email)

    fun insertUser(user: User) {
        viewModelScope.launch { repository.insertUser(user) }
    }

    fun updateUser(user: User) {
        viewModelScope.launch { repository.updateUser(user) }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch { repository.deleteUser(user) }
    }
}

class UserLearnStatisticsViewModel(private val repository: UserLearnStatisticsRepository) : ViewModel() {

    fun getAllUserLearnStatistics() = repository.getAllUserLearnStatistics()

    fun getUserLearnStatisticsByUserId(userId: Int) = repository.getUserLearnStatisticsByUserId(userId)

    fun getUserLearnStatisticsByLearnStatisticsId(learnStatisticsId: Int) = repository.getUserLearnStatisticsByLearnStatisticsId(learnStatisticsId)

    fun getUserLearnStatistics(userId: Int, learnStatisticsId: Int) = repository.getUserLearnStatistics(userId, learnStatisticsId)

    fun insertUserLearnStatistics(userLearnStatistics: UserLearnStatistics) {
        viewModelScope.launch { repository.insertUserLearnStatistics(userLearnStatistics) }
    }

    fun updateUserLearnStatistics(userLearnStatistics: UserLearnStatistics) {
        viewModelScope.launch { repository.updateUserLearnStatistics(userLearnStatistics) }
    }

    fun deleteUserLearnStatistics(userLearnStatistics: UserLearnStatistics) {
        viewModelScope.launch { repository.deleteUserLearnStatistics(userLearnStatistics) }
    }
}

class LearnStatisticsViewModel(private val repository: LearnStatisticsRepository) : ViewModel() {

    val learnStatistics = repository.getAllLearnStatistics()

    fun getLearnStatisticsById(learnStatisticsId: Int) =
        repository.getLearnStatisticsById(learnStatisticsId)

    fun insertLearnStatistics(learnStatistics: LearnStatistics) {
        viewModelScope.launch { repository.insertLearnStatistics(learnStatistics) }
    }

    fun updateLearnStatistics(learnStatistics: LearnStatistics) {
        viewModelScope.launch { repository.updateLearnStatistics(learnStatistics) }
    }

    fun deleteLearnStatistics(learnStatistics: LearnStatistics) {
        viewModelScope.launch { repository.deleteLearnStatistics(learnStatistics) }
    }
}
class UserGameStatisticsViewModel(private val repository: UserGameStatisticsRepository) : ViewModel() {
    fun getAllUserGameStatistics() = repository.getAllUserGameStatistics()
    fun getUserGameStatisticsByUserId(userId: Int) = repository.getUserGameStatisticsByUserId(userId)
    fun getUserGameStatisticsByGameStatisticsId(gameStatisticsId: Int) = repository.getUserGameStatisticsByGameStatisticsId(gameStatisticsId)
    fun getUserGameStatistics(userId: Int, gameStatisticsId: Int) = repository.getUserGameStatistics(userId, gameStatisticsId)

    suspend fun insertUserGameStatistics(userGameStatistics: UserGameStatistics) = repository.insertUserGameStatistics(userGameStatistics)
    suspend fun updateUserGameStatistics(userGameStatistics: UserGameStatistics) = repository.updateUserGameStatistics(userGameStatistics)
    suspend fun deleteUserGameStatistics(userGameStatistics: UserGameStatistics) = repository.deleteUserGameStatistics(userGameStatistics)
}


class GameStatisticsViewModel(private val repository: GameStatisticsRepository) : ViewModel() {
    fun getAllGameStatistics() = repository.getAllGameStatistics()
    fun getGameStatisticsById(gameStatisticsId: Int) = repository.getGameStatisticsById(gameStatisticsId)

    suspend fun insertGameStatistics(gameStatistics: GameStatistics) = repository.insertGameStatistics(gameStatistics)
    suspend fun updateGameStatistics(gameStatistics: GameStatistics) = repository.updateGameStatistics(gameStatistics)
    suspend fun deleteGameStatistics(gameStatistics: GameStatistics) = repository.deleteGameStatistics(gameStatistics)
}

class ModuleWordViewModel(private val repository: ModuleWordRepository): ViewModel(){
    fun insertModuleWord(moduleWord: ModuleWord) = repository.insertModuleWord(moduleWord)
    fun delete(moduleWord: ModuleWord) = repository.delete(moduleWord)
    fun getWordsByModule(moduleId: Int) = repository.getWordsByModule(moduleId)
    fun getModulesByWord(wordId: Int) = repository.getModulesByWord(wordId)
}

class FavouriteViewModel(private val repository: FavouriteRepository): ViewModel(){
    suspend fun insertFavourite(favourite: Favourite) = repository.insertFavourite(favourite)
    fun getAllFavourites() = repository.getAllFavourites()
    fun getFavouritesByUserId(userId: Int) = repository.getFavouritesByUserId(userId)
    fun getFavouritesByWordId(wordId: Int) = repository.getFavouritesByWordId(wordId)
    fun getFavourite(userId: Int, wordId: Int) = repository.getFavourite(userId,wordId)
    suspend fun updateFavourite(favourite: Favourite) = repository.updateFavourite(favourite)
    suspend fun deleteFavourite(favourite: Favourite) = repository.deleteFavourite(favourite)
} 

