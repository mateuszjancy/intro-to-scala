package freetransformer

import scala.util.{Failure, Success}
import scalaz.Free

object Interpreter {

  import Language._
  import Logic._

  def run[A](core: Core, data: Free[Data, A]): Either[String, A] = data.resume.fold({
    case Read(data, next) =>
      val d = core.read(data)
      d match {
        case Success(value) =>
          run(core, next(value))
        case Failure(ex) =>
          val e: Free[Data, Nothing] = Free.liftF(Error(ex))
          run(core, e)
      }
    case CalculateA(_, next) => run(core, next(core))
    case CalculateB(_, next) => run(core, next(core))
    case Reject(_, next) => run(core, next(core))
    case Join(_, _, next) => run(core, next(core))
    case Error(ex) => Left(ex.getMessage)
  }, (a: A) => Right(a))
}
