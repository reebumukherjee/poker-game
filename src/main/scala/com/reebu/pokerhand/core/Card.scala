package com.reebu.pokerhand.core


case class Card(value: Char, face: Char) {
  private val values = Seq('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')

  val rank: Int = values.indexOf(value)
  val suit: Char = face
  override def toString: String = s"$value$suit"

}
object Card {
  implicit val cardOrdering: Ordering[Card] = Ordering.by(card=>(card.rank,card.suit))
}
