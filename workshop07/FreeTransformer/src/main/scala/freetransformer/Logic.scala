package freetransformer

import freetransformer.Language._

import scalaz.{Free, Functor}

object Logic {
  type Calculation[T] = (Entity, Either[String, T])
  type CalculationFn[T] = Entity => Calculation[T]

  implicit val functor = new Functor[Data] {
    override def map[A, B](fa: Data[A])(f: (A) => B): Data[B] = fa match {
      case Read(data, next) => Read(data, n => f(next(n)))
      case CalculateA(data, next) => CalculateA(data, n => f(next(n)))
      case CalculateB(data, next) => CalculateB(data, n => f(next(n)))
      case Reject(data, next) => Reject(data, n => f(next(n)))
      case Join(a, b, next) => Join(a, b, n => f(next(n)))
      case Error(ex) => Error(ex)
    }
  }

  def read[T](data: String, f: String => T): Free[Data, List[T]] =
    Free.liftF(Read(data, data => data.map(f)))

  def calculateA(data: List[Entity]): Free[Data, List[(Entity, Either[String, EntityWithA])]] =
    Free.liftF(CalculateA(data, core => data.map(core.calculateA)))

  def calculateB(data: List[Entity]): Free[Data, List[(Entity, Either[String, EntityWithB])]] =
    Free.liftF(CalculateB(data, core => data.map(core.calculateB)))

  def reject[E](data: List[(Entity, Either[String, E])]): Free[Data, List[(Entity, E)]] =
    Free.liftF(Reject(data, core => core.reject(data)))

  def join(a: List[(Entity, EntityWithA)], b: List[(Entity, EntityWithB)]): Free[Data, List[Either[String, Result]]] =
    Free.liftF(Join(a, b, core => core.join(a, b)))
}
