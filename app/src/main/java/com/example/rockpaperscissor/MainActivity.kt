package com.example.rockpaperscissor

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import kotlin.math.max
import kotlin.random.Random
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var rock: ImageView
    lateinit var paper: ImageView
    lateinit var scissor: ImageView

    lateinit var myChoise: TextView
    lateinit var machineChoise: TextView
    lateinit var ties: TextView

    lateinit var userPic: ImageView
    lateinit var compPic: ImageView

    var compChoise: Int = -1
    var userChoise: Int = -1

    var compLives: Int = 3
    var userLives: Int = 3
    var tie: Int = 0

    lateinit var userLivesPics: List<ImageView>
    lateinit var compLivesPics: List<ImageView>


    val options = listOf<String>("Kő","Papír","Olló")
    val results = listOf<String>("Nyertél","Vesztettél","Döntetlen")


    val pictures = listOf(R.drawable.rock,R.drawable.paper,R.drawable.scissors)
    val aliveOrDead = listOf(R.drawable.heart1,R.drawable.heart2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeComponents()
        addEventListeners()
        userLivesPics = listOf<ImageView>(findViewById(R.id.user1),findViewById(R.id.user2),findViewById(R.id.user3))
        compLivesPics = listOf<ImageView>(findViewById(R.id.gep1),findViewById(R.id.gep2),findViewById(R.id.gep3))
    }
    fun initializeComponents(){
        rock = findViewById(R.id.ko)
        paper = findViewById(R.id.papir)
        scissor = findViewById(R.id.ollo)

        myChoise = findViewById(R.id.valasztasom)
        machineChoise = findViewById(R.id.gepValasztasa)
        ties = findViewById(R.id.dontetlenek)

        userPic = findViewById(R.id.userKep)
        compPic = findViewById(R.id.gepKep)
    }
    fun addEventListeners(){
        rock.setOnClickListener{
            usersChoice(0)
        }
        paper.setOnClickListener{
            usersChoice(1)
        }
        scissor.setOnClickListener{
            usersChoice(2)
        }
    }
    fun usersChoice(num: Int){
        userChoise = num
        val temp: String = options[userChoise]
        userPic.setImageResource(pictures[userChoise])
        myChoise.text = "Az én választásom: $temp"
        computersChoise()
        writeWinner(whoWon())
    }
    fun computersChoise(){
        compChoise = Random.nextInt(0,3)
        val temp: String = options[compChoise]
        val choiseString: String = "A gép választása: $temp"
        compPic.setImageResource(pictures[compChoise])
        machineChoise.text = choiseString
    }
    fun whoWon(): String{
        lateinit var temp: String
        if (userChoise == compChoise){
            temp = results[2]
            tie++
        }
        else if((userChoise == 0 && compChoise == 2) || (userChoise == 2 && compChoise == 1) || (userChoise == 1 && compChoise == 0) ){
            temp = results[0]
            compLives--
        }
        else{
            temp = results[1]
            userLives--
        }
        updateLives()
        endOfGame()
        return temp
    }
    fun endOfGame(){
        if(userLives == 0 || compLives == 0){
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("IGEN") { dialog, which ->
                userLives = 3
                compLives = 3
                tie = 0
                machineChoise.text = "Gép választása"
                myChoise.text = "A te választásod"
                updateLives()
                dialog.dismiss()
            }
            builder.setNegativeButton("NEM") { dialog, which ->
                finish()
            }

            builder.setMessage("Szeretnél új játékot játszani?")
            if (userLives == 0){
                builder.setTitle("Vesztettél")
            }
            else if(compLives == 0){
                builder.setTitle("Nyertél")
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }
    fun updateLives(){
        updateTies()
        val maxLives = 2
        var userTemp = userLives

        for (i in 0..2){
            if (userTemp > 0){
                userLivesPics[i].setImageResource(aliveOrDead[1])
            }
            else{
                userLivesPics[i].setImageResource(aliveOrDead[0])
            }
            userTemp--
        }
        var compTemp = compLives

        for (i in 0..2){
            if (compTemp > 0){
                compLivesPics[i].setImageResource(aliveOrDead[1])
            }
            else{
                compLivesPics[i].setImageResource(aliveOrDead[0])
            }
            compTemp--
        }
    }
    fun updateTies(){
        ties.text = "Döntetlenek száma: $tie"
    }
    fun writeWinner(winner: String){
        Toast.makeText(applicationContext, winner, Toast.LENGTH_SHORT).show()
    }
}
























