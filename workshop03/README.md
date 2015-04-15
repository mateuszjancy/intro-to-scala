## Workshop 03 - Scala OOP ##

### Goal ###
To get familiar with OOP scala programming

#### Ping Pong overview ####
- SBT
- case class as a message
- Akka in general
- Actor trait, class and companion object
- How to create an Actor from Actor System and from another Actor
- How to send messages between actors

#### Acceptance Criteria ####
- When a user sends Sell message to Store then Store should receive it. Sell message should contain information about product, price, product group and quantity.
- When a Store Service receives Sell message then Transaction message should be sent to Analytic. Transaction message should contain information about product, price, quantity, product group and store country
- When an Analytic Service receives Transaction message then product sales value, group sales value and country sales value should be re-calculated

#### Technical Requirements ####
- Messages should be represented by case classes
- DataStore implementation for product sales value, group sales value and country sales value should be implemented
- Analytic Service should be implemented as an actor
- Store Service should be implemented as an actor
- Analytic Service can create dedicated actors for product sales value, group sales value and country sales value calculation
- Each actor should have companion object
- Country should be passed to Store Service by constructor

#### Process ####
- 20 min: Intro
- Develop Store Service with all required messages (Enums for Country representation?)
- Create Actor System and test it
- Develop skeleton for Analytic Service
- Add Analytic service to Actor System and test it all
- Develop DataSource (test it?)
- Create dedicated Actors for sales value calculation (maybe tuple as a message?)

#### Materials ####
- [Akka](http://akka.io/)
- [Scala](https://twitter.github.io/scala_school/)
- [OOP](http://pawelwlodarski.gitbooks.io/workshops/content/rozwiazaniaOOP1.html)

#### More in future ####
- [Scala in WEB](https://www.playframework.com/)
- [DI in Scala](http://di-in-scala.github.io/http://di-in-scala.github.io/)