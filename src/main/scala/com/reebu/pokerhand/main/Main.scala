package com.reebu.pokerhand.main

import com.reebu.pokerhand.core.Hand
import com.reebu.pokerhand.parser.TextParser.listHand

object Main {
  def main(args: Array[String]): Unit = {

    val filePath = "/com/reebu/pokerhand/parser/poker_hands.txt"
    val hand = listHand(filePath)

    val handPlayer1 = hand._1.map(hand => new Hand(hand))
    val handPlayer2 = hand._2.map(hand => new Hand(hand))

    //376
    val answer = Range.inclusive(1,handPlayer1.length-1).map(i => handPlayer1(i).compare(handPlayer2(i))).count(_==true)
    println(answer)
  }
}