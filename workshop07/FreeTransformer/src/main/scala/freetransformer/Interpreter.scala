package freetransformer

import freetransformer.Language.{Data, Error}

import scala.util.{Failure, Success}
import scalaz._

trait ErrorPolicy {
  def error(ex: Throwable): Free[Data, Nothing]
}

trait GlobalErrorPolicy extends ErrorPolicy {
  override def error(ex: Throwable): Free[Data, Nothing] = ex match {
    case ex: Exception =>
      val e: Free[Data, Nothing] = Free.liftF(Error(ex))
      e
  }
}

trait Interpreter {
  this: ErrorPolicy =>

  import Language._
  import Logic._

  def run[A](core: Core, data: Free[Data, A]): Either[String, A] = data.resume.fold({
    case Read(data, next) =>
      val d = core.read(data)
      d match {
        case Success(value) => run(core, next(value))
        case Failure(ex) => run(core, error(ex))
      }
    case CalculateA(_, next) => run(core, next(core))
    case CalculateB(_, next) => run(core, next(core))
    case Reject(_, next) => run(core, next(core))
    case Join(_, _, next) => run(core, next(core))
    case Error(ex) => Left(ex.getMessage)
  }, (a: A) => Right(a))
}

object Interpreter extends GlobalErrorPolicy