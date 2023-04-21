package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel


class GameViewModel : ViewModel() {


    // must be private so outside can't randomly change score
    private var _score = 0
    private var _currentWordCount = 0
    private lateinit var _currentScrambledWord:String
    private var wordList: MutableList<String> = mutableListOf()
    //private var wordList : MutableList<String> = mutableListOf()
    lateinit var currentWord:String

    public val score : Int get() = _score
    public val currentWordCount : Int get()=_currentWordCount
    public val currentScrambledWord : String get() = _currentScrambledWord

    // yOUT GOT TO BE KIDDING ME!???!!? INIT HAS TO GO AFTER THE VARIABLES LOL LLLLLL
    init{
        Log.v(TAG,"gameviewmodel init")
        getNextWord()
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
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
            _currentScrambledWord=String(tempWord)
            _currentWordCount++
        }
    }

    fun nextWord(): Boolean{
        if (currentWordCount< MAX_NO_OF_WORDS) {
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