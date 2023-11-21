package com.example.thegameofpigkt

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.thegameofpigkt.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var playerTurn = 0
    private var sum = 0
    private var diceResult = 0
    private var imageResource = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sum = 0
        playerTurn = 1
        imageResource = 0
        setPlayerColor()
    }

    fun roll(view : View){
        diceResult = randomNumberSixDice()
        setDiceImage()

        if(diceResult != 1){
            sum += diceResult
        }else{
            changeTurn()
        }

        if(playerTurn == 1){
            binding.firstPlayerDicePointsSum.text = sum.toString()
        }else{
            binding.secondPlayerDicePointsSum.text = sum.toString()
        }

    }

    fun hold(view : View){
        val points : Int
        val winner = "WINNER!"

        if(sum > 0 && playerTurn == 1){
            points = binding.firstPlayerPoints.text.toString().toInt()+sum
            binding.firstPlayerPoints.text = points.toString()
            if(points >= 100){
                binding.firstPlayerLabel.text = winner
                finishGame()
            }else{
                changeTurn()
            }
        }else if(sum > 0){
            points = binding.secondPlayerPoints.text.toString().toInt()+sum
            binding.secondPlayerPoints.text = points.toString()
            if(points >= 100){
                binding.secondPlayerLabel.text = winner
                finishGame()
            }else{
                changeTurn()
            }
        }
    }

    fun restartGame(view : View){
        sum = 0
        playerTurn = 1
        diceResult = 0
        setPlayerColor()
        setDiceImage()

        binding.firstPlayerLabel.text = resources.getText(R.string.first_player_label)
        binding.secondPlayerLabel.text = resources.getText(R.string.second_player_label)

        binding.firstPlayerPoints.text = resources.getText(R.string.start_game_value)
        binding.secondPlayerPoints.text = resources.getText(R.string.start_game_value)

        binding.firstPlayerDicePointsSum.text = resources.getText(R.string.start_game_value)
        binding.secondPlayerDicePointsSum.text = resources.getText(R.string.start_game_value)

        binding.rollButton.isClickable = true
        binding.holdButton.isClickable = true
    }

    private fun randomNumberSixDice() : Int{
        val maxDiceValue = 6
        val minDiceValue = 1
        return (minDiceValue..maxDiceValue).random();
    }

    private fun setDiceImage(){
        imageResource = when(diceResult){
            1 -> R.drawable.d6_1
            2 -> R.drawable.d6_2
            3 -> R.drawable.d6_3
            4 -> R.drawable.d6_4
            5 -> R.drawable.d6_5
            6 -> R.drawable.d6_6
            else -> R.drawable.empty_dice
        }
        binding.diceImage.setImageResource(imageResource)
    }

    private fun changeTurn(){
        playerTurn = if(playerTurn == 1) 2 else 1
        setPlayerColor()
        sum = 0

        binding.firstPlayerDicePointsSum.text = sum.toString()
        binding.secondPlayerDicePointsSum.text = sum.toString()
    }

    private fun setPlayerColor(){
        val drawableFirst : Int?
        val drawableSecond : Int?
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            drawableFirst = if(playerTurn == 1) R.drawable.first_player_landscape_bg else null
            drawableSecond = if(playerTurn == 2) R.drawable.second_player_landscape_bg else null
        }else{
            drawableFirst = if(playerTurn == 1) R.drawable.first_player_portrait_bg else null
            drawableSecond = if(playerTurn == 2) R.drawable.second_player_portrait_bg else null
        }

        binding.firstPlayerLayout.background = drawableFirst?.let { ResourcesCompat.getDrawable(binding.firstPlayerLayout.resources, drawableFirst, theme) }
            ?: run { null }
        binding.secondPlayerLayout.background = drawableSecond?.let { ResourcesCompat.getDrawable(binding.secondPlayerLayout.resources, drawableSecond, theme) }
            ?: run { null }
    }

    private fun finishGame(){
        binding.rollButton.isClickable = false
        binding.holdButton.isClickable = false
    }
}