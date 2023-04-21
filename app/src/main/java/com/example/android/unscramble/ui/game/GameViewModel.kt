package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {


    // must be private so outside can't randomly change score
    private val _score = MutableLiveData(0)
    private val _currentWordCount = MutableLiveData<Int>(0)
    private val _currentScrambledWord=MutableLiveData<String>("")
    private var wordList: MutableList<String> = mutableListOf()
    lateinit var currentWord:String

    public val score : MutableLiveData<Int> get() = _score
    public val currentWordCount : MutableLiveData<Int> get()=_currentWordCount
    public val currentScrambledWord : LiveData<String>  get() = _currentScrambledWord

    // yOUT GOT TO BE KIDDING ME!???!!? INIT HAS TO GO AFTER THE VARIABLES LOL LLLLLL
    init{
        Log.v(TAG,"gameviewmodel init")
        getNextWord()
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        getNextWord()
    }

    private fun getNextWord(){
        currentWord = allWordsList.random()
        Log.d(TAG,currentWord + wordList)
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(currentWord,false)){
            tempWord.shuffle()
        }

        if (wordList.contains(currentWord)){
            getNextWord()
        }else{
            wordList.add(currentWord)
            _currentScrambledWord.value=String(tempWord)
            _currentWordCount.value=(_currentWordCount.value)?.inc()
        }
    }

    fun nextWord(): Boolean{
        if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            return true
        }else{
            return false
        }

    }
    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "gameviewmodel cleared")
    }
}