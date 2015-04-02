def add(a:Int, b:Int):Int = a + b

def curry[A, B, C] (f: (A, B) => C): A => (B => C) = {
  (a: A) => (b: B) => f(a, b)
}

val va = curry(add)
val vb = va(1)
vb(1)

def uncurry[A, B, C](f: A => B => C): (A, B) => C = {
  (a: A, b: B) => f(a)(b)
}

val ua = uncurry(curry(add))
ua(1, 1)

def compose[A, B, C](f: B => C, g: A => B): A => C = {
  (a: A) => f(g(a))
}

def plusOne(a: Int):Int = a + 1
def plusTwo(a: Int):Int = a + 2
var ca = compose(plusOne, plusTwo)
ca(1)

