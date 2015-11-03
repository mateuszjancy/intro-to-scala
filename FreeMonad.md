##Free monads##
Main goal is to understan how to use it as a main architecture concept. Source code was presender Rúnar Óli Bjarnason in [Dead-Simple Dependency Injection](https://www.youtube.com/watch?v=ZasXwtTRkio)

###Model###
Model is implemented as a algebraic datatype, and it's possible to use it in pattertn matchin out of the box. Main advantage is ability to create expressions. It will be our syntax for small programs which we are going to write.

```scala
sealed trait KVS[A]
case class Put[A](key: String, value: String, a: A) extends KVS[A]
case class Get[A](key: String, h: String => A) extends KVS[A]
case class Delete[A](key: String, a: A) extends KVS[A]
```

###Free###
```scala
trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A=>B): F[B]
}
case class Done[F[_]: Functor, A](a: A) extends Free[F, A]
case class More[F[_]: Functor, A](k: F[Free[F, A]]) extends Free[F, A]
implicit val functor = new Functor[KVS] {
  override def map[A, B](a: KVS[A])(f: (A) => B): KVS[B] = a match {
    case Put(k, v, a) => Put(k, v, f(a))
    case Get(k, h) => Get(k, x => f(h(x)))
    case Delete(k, a) => Delete(k, f(a))
  }
}
class Free[F[_], A] (implicit F: Functor[F]){
  def flatMap[B](f: A => Free[F, B]): Free[F, B] = this match {
    case Done(a) => f(a)
    case More(k) => More[F, B](F.map(k)(_ flatMap f))
  }
  def map[B](f: A=>B): Free[F, B] = flatMap{
    x => Done(f(x))
  }
}
```

###Program###
```scala
def put(key: String, value: String): Free[KVS, Unit] =
  More(Put(key, value, Done(())))
def get(key: String): Free[KVS, String] =
  More(Get(key, v => Done(v)))
def delete(key: String): Free[KVS, Unit] =
  More(Delete(key, Done(())))
def modify(key: String, f: String => String): Free[KVS, Unit] = for {
  v <- get(key)
  _ <- put(key, f(v))
} yield ()
```

###Interpreter###
```scala
def runKVS[A](kvs: Free[KVS, A], table: Map[String, String]): Map[String, String] = kvs match {
  case More(Put(k, v, a)) => runKVS(a, table + (k -> v))
  case More(Get(k, f)) => runKVS(f(k), table)
  case More(Delete(k, a)) => runKVS(a, table - k)
  case Done(a) => table
}

runKVS(modify("a", f => f.toUpperCase), Map("a"->"aa", "b" -> "bb"))
```
