package freetransformer

import scalaz.Free

object Interpreter {

  import Language._
  import Logic._

  def run[A](core: Core, data: Free[Data, A]): Either[String, A] = data.resume.fold({
    case Read(_, next) => run(core, next(core))
    case CalculateA(_, next) => run(core, next(core))
    case CalculateB(_, next) => run(core, next(core))
    case Reject(_, next) => run(core, next(core))
    case Join(_, _, next) => run(core, next(core))
  }, (a: A) => Right(a))
}
