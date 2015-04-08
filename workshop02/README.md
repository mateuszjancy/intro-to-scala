## Workshop 02 - Scala Collections ##

### Goal ###
To get familiar with Lists, Maps, functional combinators (map, foreach, filter, zip, folds...)

#### Materials ####
- [Collections overview](http://docs.scala-lang.org/overviews/collections/overview.html)
- [Materials](http://piconote-jancy.rhcloud.com/pawelwlodarski.gitbooks.io/workshops/content/ScalaKolekcje.html)
- [Cheat sheet](https://twitter.github.io/scala_school/collections.html)
- [MapReduce](http://stevekrenzel.com/finding-friends-with-mapreduce)

#### Exercise ####
1. Write a function that, given a string, produces a map of the indexes of all characters. For example indexes("Mississippi") should return a map associating 'M' with the set {0}, 'i' with the set{1, 4, 7, 10} and so on. Use a mutable map of characters to mutable sets. How can you ensure that the set is sorted?

2. Repeat the preceding exercise, using an immutable map of character to lists.

3. Implements two (word and friends) MapReduce examples from [Steve Krenzel - 6. MapReduce: Finding Friends](http://stevekrenzel.com/finding-friends-with-mapreduce)
