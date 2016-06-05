package freetransformer

object Main extends App {

  import Interpreter._
  import Logic._

  val data = "aaa,bbb,cc,dd,e,f"

  def job(data: String) = for {
    raw <- read(data, Entity.apply)
    rawA <- calculateA(raw)
    a <- reject(rawA)
    rawB <- calculateB(raw)
    b <- reject(rawB)
    result <- join(a, b)
  } yield result.filter(_.isRight).map(_.right.get)

  val ok = run(Core(), job(data))
  val err = run(Core(), job(""))
  println(ok)
  println(err)
}
