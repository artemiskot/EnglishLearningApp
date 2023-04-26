package com.englishlearningapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val EnglishWord: String,
    val RussianWord: String,
)

@Entity(tableName = "module_word_table",
    primaryKeys = ["module_id", "word_id"],
    foreignKeys = [
        ForeignKey(
            entity = Module::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("module_id")
        ),
        ForeignKey(
            entity = Word::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("word_id")
        )
    ])
data class ModuleWord(
    val module_id: Int,
    val word_id: Int
)

@Entity(tableName = "module_table")
data class Module(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)

@Entity(tableName = "module_user_table",
    foreignKeys = [
        ForeignKey(
            entity = Module::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("module_id")
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id")
        )
    ])
data class ModuleUser(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val user_id: Int,
    val module_id: Int,
    val is_learnt: Boolean
)

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val email: String,
    val password: String,
    val role: String
)

@Entity(tableName = "user_learn_statistics_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id")
        ),
        ForeignKey(
            entity = LearnStatistics::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("learn_statistics_id")
        )
    ])
data class UserLearnStatistics(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val user_id: Int,
    val learn_statistics_id: Int
)

@Entity(tableName = "learn_statistics_table")
data class LearnStatistics(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val total_learnt: Int
)

@Entity(tableName = "user_game_statistics_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id")
        ),
        ForeignKey(
            entity = GameStatistics::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("game_statistics_id")
        )
    ])
data class UserGameStatistics(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val user_id: Int,
    val game_statistics_id: Int
)

@Entity(tableName = "game_statistics_table")
data class GameStatistics(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var total_learnt: Int,
    var total_games: Int
)


@Entity(tableName = "favourite_table",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id")
        ),
        ForeignKey(
            entity = Word::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("word_id")
        )
    ])
data class Favourite(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val user_id: Int,
    val word_id: Int,
    val is_favorite:Boolean
)
