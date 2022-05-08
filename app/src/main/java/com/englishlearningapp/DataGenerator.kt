package com.englishlearningapp

class DataGenerator {
    companion object {
        fun getUsers(): List<Word>{
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
    }
}