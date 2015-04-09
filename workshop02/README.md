## Workshop 02 - Scala Collections ##

### Goal ###
To get familiar with Lists, Maps, functional combinators (map, foreach, filter, zip, folds...)

#### Collections overview ####
- [scala docs](http://docs.scala-lang.org/overviews/collections/overview.html)

#### Basic Collection operations ####
- Mutable and immutable collections
      - Scala support both
      - Scala gives preference to immutable collections (Predef contains immutable collection types)
      - Add import scala.collections.mutable.Map to use mutable version of Map

- Sequence
      - Vector: immutable version equivalent of an ArrayBuffer
      - Range: integer sequence, store only start end and increment

- List
      - In scala a collection is either Nil (empty) or an object with head (first element) and tail (rest)

			val set = Set(1, 2, 3)
			set.head
			set.tail
        
			val seq = Seq(1, 2, 3)
			seq.head
			seq.tail
        
			val list = List(1, 2, 3)
			list.head
			list.tail

      - :: makes new list for given head and tail

			1::2::3::Nil

      - Pattern matching

			def sum(l: List[Int]):Int = l match {
			  case Nil => 0
			  case h::t => h + sum(t)
			}

- Adding or removing operations
      - Seq: append or prepend element

			val s = Seq(1, 2, 3)
			s :+ 4
			0 +: s

      - Set, Map: add element

			val s = Set(1, 2, 3)
			s + 4
			s + (4, 5, 6)

      - Set, Map, ArrayBuffer: remove element

			val s = Set(1, 2, 3)
			s - 3
			s - (2, 3)

      - Iterable:

			val a = List(1, 2, 3)
			val b = List(4, 5, 6)

			a ++ b //append
			b ++: a //prepend

      - Set, Map, ArrayBuffer

			val a = Set(1, 2, 3)
			val b = Set(2, 3)

			a -- b //remove set

      - List

			val a = List(1, 2, 3)
			val b = List(4, 5, 6)

			0 :: b
			a ::: b

      - Set: Set union, intersection, difference,

			val a = Set(1, 2, 3)
			val b = Set(2, 3, 4)

			a | b
			a & b
			a &~ b

      - Mutable collections:

			import scala.collection.mutable.ListBuffer

			var list = ListBuffer(1, 2, 3)
			var list2 = ListBuffer(-3, -2, -1)

			list += 4
			list += (5, 6)
			list ++= list2

			list -= 4
			list -= (5, 6)
			list --= list2

#### Common Methods ####
- [Scala kolekcje](http://piconote-jancy.rhcloud.com/pawelwlodarski.gitbooks.io/workshops/content/ScalaKolekcje.html)
- [Cheat sheet](https://twitter.github.io/scala_school/collections.html)

#### Exercise ####
1. Write a function that, given a string, produces a map of the indexes of all characters. For example indexes("Mississippi") should return a map associating 'M' with the set {0}, 'i' with the set{1, 4, 7, 10} and so on. Use a mutable map of characters to mutable sets. How can you ensure that the set is sorted?

2. Repeat the preceding exercise, using an immutable map of character to lists.

3. Implements two (word and friends) MapReduce examples from [Steve Krenzel - 6. MapReduce: Finding Friends](http://stevekrenzel.com/finding-friends-with-mapreduce)
