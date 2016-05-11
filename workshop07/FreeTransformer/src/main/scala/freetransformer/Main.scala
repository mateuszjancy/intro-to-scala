package freetransformer

object Main extends App {

  import Interpreter._
  import Logic._

  val data = "aaa,bbb,cc,dd,e,f"

  def job(data: String) = for {
    raw <- read(data)
    rawA <- calculateA(raw)
    a <- reject(rawA)
    rawB <- calculateB(raw)
    b <- reject(rawB)
    result <- join(a, b)
  } yield result.filter(_.isRight).map(_.right.get)

  val o = run(Core(), job(data))
  println(o)
}
