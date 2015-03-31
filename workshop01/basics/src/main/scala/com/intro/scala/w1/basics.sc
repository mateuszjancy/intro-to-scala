/**
 * 1. values: val a = 1; var b;
 */
val a = "a"
//cannot change the binding
//a = "b" //incorrect

//Variables
var b = "b"
b = "B"

/**
 * 2. functions: def some(a:Int):Int = ...,
 */

//One line functions
def addTwo(a: Int, b: Int): Int = a + b

//Multi line functions
def addThree(a: Int, b: Int, c: Int): Int = {
  a + b + c
}

//Anonymous Functions
def eval(a: ()=>Unit) = a()

eval(()=>println("Hello"))
//Partial application
val pfa = addThree(_:Int, 1, _:Int)
pfa(1, 1)
//Curried functions
var three = (addThree _).curried
var two = three(1)
var one = two(1)
one(1)
def addThreeNew(a: Int)(b: Int)(c: Int): Int = a + b + c
var twoNew = addThreeNew(1) _
var oneNew = twoNew(1)
oneNew(1)
/**
 * 3. classes: class A(a: Int){...},
 */
class Character(c:String) {
  // method
  def print() = println(c)
}
// inheritance: class A extends B with C {...} ,
class A extends Character("B"){}
new A().print()
/**
 * 4. case classes: case class A(a:Int),
 */
case class User(login: String, password: String)
// instance
var mateusz1 = User("mateusz", "pass")
// update?
var mateusz2 = mateusz1.copy(password = "newPassword")
/**
 *5. objects: object A {...},
 */
class  O(z:O.Z) {
  def addToZ(add: Int) = z.copy(i = z.i+add)
}
object O {
  case class Z (i:Int)
  def printZ(z: Z) = println(z)

  def apply(z: Z) = new O(z)
}

val o = O(O.Z(1))

O.printZ(o.addToZ(19))
/**
 * 5. pattern matching: a match {case 1 => ...}.
 */
var r = new scala.util.Random
val f = r.nextInt(5) match {
  case 1 => ()=>println("One")
  case n if n >1 && n < 3 => ()=>println("Two")
  case _ => ()=> println("Don't care")
}

f()
