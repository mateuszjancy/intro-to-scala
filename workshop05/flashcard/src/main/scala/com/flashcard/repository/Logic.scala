package com.flashcard.repository

import com.flashcard._

object Logic {
  implicit val functor = new Functor[Store] {
    override def map[A, B](a: Store[A])(f: (A) => B): Store[B] = a match {
      case CreateTable(sql, a) => CreateTable(sql, f(a))
    }
  }

  def bootstrap(): Free[Store, Unit] = {
    val flashcardTable = "CREATE TABLE FLASHCARD (ID INT PRIMARY KEY, WORD VARCHAR, TRANSLATION VARCHAR)"
    val dictionaryTable = "CREATE TABLE DICTIONARY (WORD VARCHAR, TRANSLATION VARCHAR)"

    More(CreateTable(flashcardTable, More(CreateTable(dictionaryTable, Done()))))
  }
}
