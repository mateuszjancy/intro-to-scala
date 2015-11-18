package com.flashcard.service

import com.flashcard._

object Logic {
  implicit val functor = new Functor[Store] {
    override def map[A, B](a: Store[A])(f: (A) => B): Store[B] = a match {
      case Put(k, v, a) => Put(k, v, f(a))
      case Update(k, v, a) => Update(k, v, f(a))
      case Get(k, h) => Get(k, x => f(h(x)))
      case Delete(k, a) => Delete(k, f(a))
    }
  }

  def put(key: Int, flashcard: Flashcard): Free[Store, Unit] = More(Put(key, flashcard, Done(())))

  def update(key: Int, flashcard: Flashcard): Free[Store, Unit] = More(Update(key, flashcard, Done(())))

  def get(key: Int): Free[Store, Flashcard] = More(Get(key, v => Done(v)))

  def delete(key: Int): Free[Store, Unit] = More(Delete(key, Done(())))

  def create(id: Int, word: String, translation: String): Free[Store, Unit] = put(id, Flashcard(id, word, translation))

  def modify(key: Int, f: Flashcard => Flashcard): Free[Store, Unit] = for {
    v <- get(key)
    _ <- update(key, f(v))
  } yield ()


}
