## Workshop 04 - Scala Implicits ##

### Goal ###
To get familiar with scala implicits

#### Implicit conversions ####
- if type of expression differs from expected type
- if an object access non existing member
- if an object invokes a method whose parameters don't match given argument
- No implicit conversion if the code compiles without it
- No multiple conversions
- Ambiguous conversions are an error

#### Implicit parameters ####
- implicit def and val definitions visible in parameter scope
- in companion object that is associate with desired type

#### Implicit class ####
- similar to implicit conversions
