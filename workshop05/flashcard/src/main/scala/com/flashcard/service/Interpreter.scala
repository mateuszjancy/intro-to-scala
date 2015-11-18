package com.flashcard.service

import com.flashcard._
import com.flashcard.repository.CreateTable

object Interpreter {

  import DB._
  import Logic._

  def run[A](free: Free[Store, A]*)(repository: DB): DB = free.foldLeft(repository) { case (r, f) => f match {
    case More(Put(k, v, a)) => run(a)(r + (k -> v))
    case More(Update(k, v, a)) => run(a)(r :+ (k -> v))
    case More(Get(k, f)) => run(f(r.get(k)))(r)
    case More(Delete(k, a)) => run(a)(r - k)
    case More(CreateTable(sql, a)) => run(a)(r.createTable(sql))
    case Done(a) => r
  }
  }

  def translation(f: Flashcard): Flashcard = f.copy(translation = f.translation.toUpperCase)

  def apply() = run(put(1, Flashcard(1, "czesc", "Hi")), modify(1, translation)) _
}
