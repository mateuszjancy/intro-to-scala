import scala.annotation.tailrec

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail:List[A]) extends List[A]

object List {
  def foldLeft[A,B](as:List[A], z:B)(f: (A,B) => B): B = as match {
    case Nil => z
    case Cons(x,xs) => f(x, foldLeft(xs, z)(f))
  }

  def sum2(inst: List[Int]) = foldLeft(inst, 0) (_+_)

  def sum(inst: List[Int]): Int = inst match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product2(ds: List[Double]): Double = foldLeft(ds, 1.0)(_*_)

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def updateHead[A](l: List[A], a:A):List[A] = l match {
    case Nil => apply(a)
    case Cons(_, xs) => Cons(a,xs)
  }

  @tailrec
  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Cons(x,xs) if f(x) => dropWhile(xs)(f)
    case x => x
  }

  @tailrec
  def drop[A](l: List[A], n: Int): List[A] = l match {
    case Nil => Nil
    case Cons(x,xs) if n > 0 => drop(xs, n -1)
    case x => x
  }

  def map[A, B](l: List[A], f: A => B): List[B] = l match {
    case Nil => Nil
    case Cons(x, xs) => Cons(f(x), map(xs, f))
    //case Cons(x, Nil) => Cons(f(x), Nil)
  }

  def apply[A](as: A*): List[A] = {
    if(as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  }
}

val list = List(1, 2, 3, 4, 5, 6)
List.updateHead(list, -1)
List.drop(list, 3)
List.dropWhile(list)(a => a%2!=0)
List.sum(list)
List.sum2(list)
List.map(list, ((a:Int)=>a+2))