package freetransformer

object Language {

  sealed trait Data[+A]

  case class Read[Next](data: String, next: Core => Next) extends Data[Next]

  case class CalculateA[Next](data: List[Entity], next: Core => Next) extends Data[Next]

  case class CalculateB[Next](data: List[Entity], next: Core => Next) extends Data[Next]

  case class Reject[Next, E](data: List[(Entity, Either[String, E])], next: Core => Next) extends Data[Next]

  case class Join[Next](a: List[(Entity, EntityWithA)], b: List[(Entity, EntityWithB)], next: Core => Next) extends Data[Next]

}
