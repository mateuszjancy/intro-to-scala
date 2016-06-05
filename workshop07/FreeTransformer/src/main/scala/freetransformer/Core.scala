package freetransformer

import scala.util.{Failure, Try}

trait Core {
  def read(data: String): Try[List[String]]

  def calculateA(el: Entity): (Entity, Either[String, EntityWithA])

  def calculateB(el: Entity): (Entity, Either[String, EntityWithB])

  def join(a: List[(Entity, EntityWithA)], b: List[(Entity, EntityWithB)]): List[Either[String, Result]]

  def reject[E](data: List[(Entity, Either[String, E])]): List[(Entity, E)]
}

object Core {
  def apply() = new CoreImpl
}

class CoreImpl extends Core {
  def read(data: String): Try[List[String]] = if (data.isEmpty) Failure(new Exception("No items")) else Try(data.split(",").toList)

  def calculateA(el: Entity): (Entity, Either[String, EntityWithA]) =
    if (el.value.length > 2) {
      println(s"CalculateA for ${el.value}")
      (el, Right(EntityWithA(el.value, "A")))
    }
    else (el, Left("length need to be greater than 2"))

  def calculateB(el: Entity): (Entity, Either[String, EntityWithB]) =
    if (el.value.length > 2) {
      println(s"CalculateB for ${el.value}")
      (el, Right(EntityWithB(el.value, "B")))
    }
    else (el, Left("length need to be greater than 2"))

  def reject[E](data: List[(Entity, Either[String, E])]): List[(Entity, E)] = {
    data.filter(_._2.isLeft).foreach(el => println(s"Rejected ${el._1} entity because: ${el._2.left.get}"))
    data.filter(_._2.isRight).map(el => (el._1, el._2.right.get))
  }

  def join(a: List[(Entity, EntityWithA)], b: List[(Entity, EntityWithB)]): List[Either[String, Result]] = {
    a.map { case (entity, ea) =>
      b.find(_._1 == entity).fold[Either[String, Result]](Left("Err")) { case (_, eb) =>
        println(s"Valid result: ${entity.value} a: ${ea.a}, b: ${eb.b}")
        Right(Result(entity.value, ea.a, eb.b))
      }
    }
  }
}
