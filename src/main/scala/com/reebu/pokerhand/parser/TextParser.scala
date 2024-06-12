package com.reebu.pokerhand.parser

import com.reebu.pokerhand.core.Card

object TextParser {

  def handFromString(twoPlayerHand: String, start: Int=15, end:Int=15) : List[Card] = {
    twoPlayerHand.substring(start,end).trim().split(" ").map(cardStr => new Card(cardStr.charAt(0),cardStr.charAt(1))).toList
  }

  def listHand(fileName : String): (List[List[Card]],List[List[Card]]) = {
    val linesInFile: List[String] =  FileReader.readerReadLines(fileName,getClass)

    val player1Hand = linesInFile.map(line => handFromString(line,0,15))
    val player2Hand = linesInFile.map(line => handFromString(line,15,line.length))

    (player1Hand,player2Hand)
  }
}


