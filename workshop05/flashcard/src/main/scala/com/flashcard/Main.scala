package com.flashcard

object Main extends App {
  val f = repository.Interpreter() andThen service.Interpreter()

  import DB._

  println(f(DB()).findAll)

}
