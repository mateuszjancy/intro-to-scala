package com.flashcard

object Interpreter extends App {

  import Program._
  import Repository._

  def run[A](free: Free[Store, A])(repository: Repository): Repository = free match {
    case More(Put(k, v, a)) => run(a)(repository + (k -> v))
    case More(Update(k, v, a)) => run(a)(repository :+ (k -> v))
    case More(Get(k, f)) => run(f(repository.get(k)))(repository)
    case More(Delete(k, a)) => run(a)(repository - k)
    case Done(a) => repository
  }

  def translation(f: Flashcard): Flashcard = f.copy(translation = f.translation.toUpperCase)

  val app = run(put(1, Flashcard(1, "czesc", "Hi"))) _ andThen run(modify(1, translation)) _
  val end = app(Repository())

  println(app.findAll)
}
