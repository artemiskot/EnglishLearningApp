package com.englishlearningapp

import com.englishlearningapp.data.Module
import com.englishlearningapp.data.ModuleWord
import com.englishlearningapp.data.User
import com.englishlearningapp.data.Word

class DataGenerator {
    companion object {
        fun getWords(): List<Word>{
            return listOf(
                Word(1,"Work","Работать"),
                Word(2,"Learn","Учить"),
                Word(3,"Try","Пытаться"),
                Word(4,"Hard","Тяжелый"),
                Word(5,"High","Высокий"),
                Word(6,"Low","Низкий"),
                Word(7,"Phone","Телефон"),
                Word(8,"Play","Играть"),
                Word(9,"Inspect","Изучать"),
                Word(10,"Call","Звонить"),
                Word(11,"Cup","Кружка"),
                Word(12,"Implement","Применять"),
                Word(13,"Object","Объект"),
                Word(14,"Class","Класс"),
                Word(15,"Function","Функция"),
                Word(16,"Get","Получать"),
                Word(17,"Set","Устанавливать"),
                Word(18,"Generate","Генерировать")
            )
        }
        fun insertWordModule(): List<ModuleWord>{
            return listOf(
                    ModuleWord(3,1),
                    ModuleWord(3,2),
                    ModuleWord(3,3),
                ModuleWord(2,4),
                ModuleWord(2,5),
                ModuleWord(2,6),
                ModuleWord(1,7),
                ModuleWord(3,8),
                ModuleWord(3,9),
                ModuleWord(3,10),
                ModuleWord(1,11),
                ModuleWord(3,12),
                ModuleWord(1,13),
                ModuleWord(1,14),
                ModuleWord(1,15),
                ModuleWord(3,16),
                ModuleWord(3,17),
                ModuleWord(3,18)
            )
        }
        fun getModules(): List<Module>{
            return  listOf(
                Module(1,"Существительные"),
                Module(2,"Прилагательные"),
                Module(3,"Глаголы")
            )
        }
        fun getModerator(): User{
            return User(email = "moderator@moderator.com", password = "moderator", role = "moderator")
        }
    }
}