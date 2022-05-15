# EnglishLearningApp README

# App Architecture and stack
This app was created on Android development course so I tried to use MVVM structure, coroutines (stream in c++. It is thread safe in Kotlin so you get no violation error).
Also used Flow<Object> type in Room DB that is made to replace Java's LiveData. It is also thread safe and helps not to get NPE(for those who knows xD).

# STARTING POINT aka Login screen
At first we get into Login screen
I used Firebase auth to verify user
In case of invalid fillment of email we get Toast which explains error to user
<img width="291" alt="image" src="https://drive.google.com/file/d/1wJHEUjBc06hcEusng9uEVtsg16SVzywl/view?usp=sharing">

So if you use this app for the first time you should press Register button so you get redicted to RegisterActivity

# Register Activity
Here you create a new recording in my Firebase
In case of succesful registration your data is inserted and you instantly getting redicted to LearnFragment aka Learning Section
If you are in this section but already have account in this app you can get to Login screen via button
<img width="291" alt="image" src="https://drive.google.com/file/d/1QWoomvBNgVyYTOAaq3jeh0P1ezqiQAzs/view?usp=sharing">

# Learning Section aka LearnFragment
In this section your goal is to learn words and how they are written. Every word is gotten by query to local(!) Room database that is prepopulated on a first app entry.
<img width="291" alt="image" src="https://drive.google.com/file/d/1CN-yFLuNQxxkpTztuOEoPF1UqBrVbM-x/view?usp=sharing">

# Practice Section aka PracticeFragment
Here you are given an english word and 4 buttons with Russian translation. Each russian word is unique and could not be the same. One is always correct. It is meant that you have learnt all words so you'll be able to play here
In case of wrong selection of translation you get toast with correct answer 
<img width="291" alt="image" src="https://drive.google.com/file/d/12aNwclR6f8I2Y_8Z_x0KtsUBEGRg-xQY/view?usp=sharing">
Otherwise you get Toast that your answer was correct
<img width="291" alt="image" src="https://drive.google.com/file/d/12NisQfbI2uD5P8qDaGTyB6fCX_i1RNRj/view?usp=sharing">
  
# Stats Section aka StatiscticsFragment
This fragment was created to count all your recordings of your practice fragment answers but I actually have no time to finish it asap so TBD :D
<img width="291" alt="image" src="https://drive.google.com/file/d/1niw3p76D_te1CX-5RMZRUwC3x3nnSeYT/view?usp=sharing">
