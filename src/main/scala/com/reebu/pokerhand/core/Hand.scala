package com.reebu.pokerhand.core

import Card.cardOrdering

class Hand(cardList: List[Card]) {
  require(cardList.length == 5, "A hand must contain exactly 5 cards.")

  private val cards: List[Card] = cardList.sorted.reverse

  private val rankGroups: List[List[Card]] = cards.groupBy(_.rank).values.toList.sortWith {
    (l1, l2) => l1.length > l2.length || (l1.length == l2.length && cardOrdering.gt(l1.head,l2.head))
  }

  private val highCard: Int = cards.head.rank

  private def rankForGivenFrequency(frequency: Int): Int =
    rankGroups.find(_.length == frequency).map(_.head.rank).getOrElse(0)

  private val isPair: (Boolean, Int, Int) = (rankForGivenFrequency(2) > 0, rankForGivenFrequency(2), 2)

  private val isTwoPair: (Boolean, Int, Int) = (rankGroups.headOption.exists(_.length == 2) && rankGroups.drop(1).headOption.exists(_.length == 2), Math.max(rankGroups.head.head.rank, rankGroups.drop(1).head.head.rank), 3)

  private val isThreeOfAKind: (Boolean, Int, Int) = (rankForGivenFrequency(3) > 0, rankForGivenFrequency(3), 4)

  private val isStraight: (Boolean, Int, Int) = (cards.map(_.rank).sliding(2).forall { case List(a, b) => a - b == 1 }, highCard, 5)

  private val isFlush: (Boolean, Int, Int) = (cards.map(_.suit).distinct.length == 1, highCard, 6)

  private val isFullHouse: (Boolean, Int, Int) = (rankForGivenFrequency(3) > 0 && rankForGivenFrequency(2) > 0, rankForGivenFrequency(3), 7)

  private val isFourOfAKind: (Boolean, Int, Int) = (rankForGivenFrequency(4) > 0, rankForGivenFrequency(4), 8)

  private val isStraightFlush: (Boolean, Int, Int) = (isStraight._1 && isFlush._1, highCard, 9)

  private val isRoyalFlush: (Boolean, Int, Int) = (cards.head.rank == 12 && cards.last.rank == 8 && isStraightFlush._1, highCard, 10)

  private val score: (Int, Int) = List(
    isRoyalFlush, isStraightFlush, isFourOfAKind, isFullHouse, isFlush,
    isStraight, isThreeOfAKind, isTwoPair, isPair
  ).collectFirst {
    case (true, rank, value) => (rank, value)
  }.getOrElse((highCard, 1))

  def compareHands(that: Hand): (Hand, Int) =
    if (score._2 > that.score._2 || (score._2 == that.score._2 && score._1 > that.score._1))
      (this, 1)
    else if (score._2 == that.score._2 && score._1 == that.score._1)
      (null, 0)
    else
      (that, 2)

  override def toString: String = s"$cards $score"
}
