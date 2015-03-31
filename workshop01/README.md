## Workshop 01 - Scala basics ##

### Goal ###
To get familiar with:
- values: val a = 1; var b;
- functions: def some(a:Int):Int = ...,
- classes: class A(a: Int){...},
- methods: class A {def a() = ...},
- inheritance: class A extends B with C {...} ,
- case classes: case class A(a:Int),
- objects: object A {...},
- packages,
- pattern matching: a match {case 1 => ...}.

#### Materials ####
Scala School:
    - [Basics](https://twitter.github.io/scala_school/basics.html)
    - [Basics continued](https://twitter.github.io/scala_school/basics2.html)

#### Exercise ####
1. The sygnum of a number is 1 is the number is positive, -1 if it is negative, and 0 if it is zero. Write a function taht computes this valie

2. Write a Scala equivalent for the Java loop: for (int i = 10; i >= 0; i--) System.out.println(i);

3. Write a function countdown(n: Int) that prints the numbers from n to 0

4. Write a function that computes x^n, where n is an integer. Use the following recursive definition:
    - x^n = y^2 if n is even and positive, where y = x^n/2
    - x^n = x * x^n-1 if n is odd and positive
    - x^0 = 1
    - x^n = 1/x^-n if n is negative