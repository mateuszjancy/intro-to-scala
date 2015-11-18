package com.flashcard.repository

import com.flashcard._

object Interpreter {

  import Logic._

  def run[A](free: Free[Store, A]*)(repository: DB): DB = free.foldLeft(repository) { case (r, f) => f match {
    case More(CreateTable(sql, a)) => run(a)(r.createTable(sql))
    case Done(a) => r
  }
  }

  def apply() = run(bootstrap()) _
}
