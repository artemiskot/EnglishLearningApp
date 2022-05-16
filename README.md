# EnglishLearningApp README

# App Architecture and stack
This app was created on university Android development course so I tried to use MVVM structure, coroutines (stream in c++. It is thread safe in Kotlin so you get no violation error).
Also used Flow <Object> type in Room DB that is made to replace Java's LiveData <>. It is also thread safe and helps not to get NPE(for those who knows xD).

# STARTING POINT aka Login screen
At first we get into Login screen
I used Firebase auth to verify user
In case of invalid fillment of email we get Toast which explains error to user
<img width="291" alt="image" src="https://user-images.githubusercontent.com/61650907/168495313-d8728589-a91c-45a7-98ab-fb35957d4bdb.jpg">

So if you use this app for the first time you should press Register button so you get redicted to RegisterActivity

# Register Activity
Here you create a new recording in my Firebase
In case of succesful registration your data is inserted and you instantly getting redicted to LearnFragment aka Learning Section
If you are in this section but already have account in this app you can get to Login screen via button
<img width="291" alt="image" src="https://user-images.githubusercontent.com/61650907/168495320-a691681b-60da-4eca-89eb-6dcb4595df87.jpg">

# Learning Section aka LearnFragment
In this section your goal is to learn words and how they are written. Every word is gotten by query to local(!) Room database that is prepopulated on a first app entry.
<img width="291" alt="image" src="https://user-images.githubusercontent.com/61650907/168495330-d2a1f1a4-654f-4b06-8c15-f1af1f7a1015.jpg">

# Practice Section aka PracticeFragment
Here you are given an english word and 4 buttons with Russian translation. Each russian word is unique and could not be the same. One is always correct. It is meant that you have learnt all words so you'll be able to play here
In case of wrong selection of translation you get toast with correct answer 
<img width="291" alt="image" src="https://user-images.githubusercontent.com/61650907/168495335-d0db6eff-ee04-44df-97ab-53a8c87d8248.jpg">
Otherwise you get Toast that your answer was correct
<img width="291" alt="image" src="https://user-images.githubusercontent.com/61650907/168495341-8034e438-db21-47ab-8700-670720e3c0ac.jpg">
  
# Stats Section aka StatiscticsFragment
This fragment was created to count all your recordings of your practice fragment answers but I actually have no time to finish it asap so TBD :D
<img width="291" alt="image" src="https://user-images.githubusercontent.com/61650907/168495351-4c320998-9ee1-4524-b314-89d7770f5111.jpg">
