##Goal##
To get familiar with scala tooling

##Build tools##
###Activator###
[Typesafe Activator](https://www.typesafe.com/community/core-tools/activator-and-sbt) is a single download to get you started building Reactive applications. To get started, you’ll need a JDK, your favorite editor or IDE, and Activator. Activator includes the sbt build tool, a quick-start GUI, and a catalog of template applications.
* Rich UI
* Project templates approach

###SBT###
[SBT](http://www.scala-sbt.org/index.html) is a build tool for Scala, Java, and more. It requires Java 1.6 or later.
* Command line
* Regular build tool 
* Hard to use with less common requirements

###Gradle###
[Gradle](http://gradle.org/) makes the impossible possible, the possible easy and the easy elegant.
* Popular
* Many plugins
* Easy to use

##Web##
###Play###
[Play Framework](https://www.playframework.com/) makes it easy to build web applications with Java & Scala.
Play is based on a lightweight, stateless, web-friendly architecture.
Built on Akka, Play provides predictable and minimal resource consumption (CPU, memory, threads) for highly-scalable applications.
* Rubby on Rails like
* Template engine, DB abstraction, Cache abstraction... all in one, but do you need it all?
* Safe choice but with many not used dependencies.
[Anathomy](https://www.playframework.com/documentation/2.4.x/Anatomy)
<pre>
app                      → Application sources
 └ assets                → Compiled asset sources
    └ stylesheets        → Typically LESS CSS sources
    └ javascripts        → Typically CoffeeScript sources
 └ controllers           → Application controllers
 └ models                → Application business layer
 └ views                 → Templates
build.sbt                → Application build script
conf                     → Configurations files and other non-compiled resources (on classpath)
 └ application.conf      → Main configuration file
 └ routes                → Routes definition
dist                     → Arbitrary files to be included in your projects distribution
public                   → Public assets
 └ stylesheets           → CSS files
 └ javascripts           → Javascript files
 └ images                → Image files
project                  → sbt configuration files
 └ build.properties      → Marker for sbt project
 └ plugins.sbt           → sbt plugins including the declaration for Play itself
lib                      → Unmanaged libraries dependencies
logs                     → Logs folder
 └ application.log       → Default log file
target                   → Generated stuff
 └ resolution-cache      → Info about dependencies
 └ scala-2.10
    └ api                → Generated API docs
    └ classes            → Compiled class files
    └ routes             → Sources generated from routes
    └ twirl              → Sources generated from templates
 └ universal             → Application packaging
 └ web                   → Compiled web assets
test                     → source folder for unit or functional tests
</pre>
[Intro project](https://github.com/playframework/playframework/tree/master/templates/play-scala-intro)

###Spray###
[Spray](http://spray.io/) is an open-source toolkit for building REST/HTTP-based integration layers on top of Scala and Akka. Being asynchronous, actor-based, fast, lightweight, modular and testable it's a great way to connect your Scala applications to the world.
* Complex in use
* Multiple ways of doing one thing
* Weird/Advanced DSL
* Uneven complexity level
* Many things need to be integrated manually
* Nested approach
* Actor based

```scala
import spray.routing.SimpleRoutingApp

object Main extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")

  startServer(interface = "localhost", port = 8080) {
    path("hello") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      }
    }
  }
}
```
###Scalatra###
[Scalatra](http://www.scalatra.org/) is a simple, accessible and free web micro-framework. It combines the power of the JVM with the beauty and brevity of Scala, helping you quickly build high-performance web sites and APIs.
* Light, and easy in use
* Sometimes looks like 90's like coding style
* Inheritance approach

```scala
package com.example.app

import org.scalatra._
import scalate.ScalateSupport

class MyServlet extends ScalatraServlet with ScalateSupport {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }
}
```
###Finagle###
[Finagle](http://twitter.github.io/finagle/): Finagle is an extensible RPC system for the JVM, used to construct high-concurrency servers.
* Super light.
* Looks very functional.
* There is [Finatra](https://twitter.github.io/finatra/) for more complex projects.

```scala
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http
import com.twitter.util.{Await, Future}

object Server extends App {
  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] =
      Future.value(
        http.Response(req.version, http.Status.Ok)
      )
  }
  val server = Http.serve(":8080", service)
  Await.ready(server)
}
```
##BigData##
###Hadoop###
[The Apache Hadoop](https://hadoop.apache.org/) software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models.
* MapReduce and Spark and others
* Program to data
* HDFS, [Hive](https://hive.apache.org/), [HBase](https://hbase.apache.org/)...
* Good starting point is to get [QuickStart VM](http://www.cloudera.com/content/www/en-us/downloads.html) from Cloudera and go with tutorials.

###Spark###
[Apache Spark](http://spark.apache.org/) is a fast and general engine for large-scale data processing.
* [Resilient distributed dataset](http://spark.apache.org/docs/latest/programming-guide.html#resilient-distributed-datasets-rdds), is a fault-tolerant collection of elements that can be operated on in parallel.
```scala
val textFile = spark.textFile("hdfs://...")
val counts = textFile.flatMap(line => line.split(" "))
                 .map(word => (word, 1))
                 .reduceByKey(_ + _)
counts.saveAsTextFile("hdfs://...")
```
* [DataFrame](http://spark.apache.org/docs/latest/sql-programming-guide.html#overview) is a distributed collection of data organized into named columns.

```scala
val parquetFile = sqlContext.read.parquet("people.parquet")

//Parquet files can also be registered as tables and then used in SQL statements.
parquetFile.registerTempTable("parquetFile")
val teenagers = sqlContext.sql("SELECT name FROM parquetFile WHERE age >= 13 AND age <= 19")
teenagers.map(t => "Name: " + t(0)).collect().foreach(println)
```
* Using the Shell
```
$ ./bin/spark-shell --master local[4]
```
* [Self-Contained Applications](http://spark.apache.org/docs/latest/quick-start.html#self-contained-applications)
```scala
/* SimpleApp.scala */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "YOUR_SPARK_HOME/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
  }
}
```

```
$ YOUR_SPARK_HOME/bin/spark-submit \
  --class "SimpleApp" \
  --master local[4] \
  target/scala-2.10/simple-project_2.10-1.0.jar
```
http://twitter.github.io/finagle/

##Database##
###The Java Database Connectivity (JDBC)###
[The Java Database Connectivity (JDBC)](http://www.oracle.com/technetwork/java/javase/jdbc/index.html) API is the industry standard for database-independent connectivity between the Java programming language and a wide range of databases.
* Can be good in small apps
* It's painful in usage
* There will be so many things to do manually but... it's not super hard:
```scala
def db(sql: String)(prepare: PreparedStatement => PreparedStatement): ResultSet = {
    val stmt = connection.prepareStatement(sql)
    prepare(stmt).executeQuery()
}
```

###Squeryl###
[Squeryl](http://squeryl.org/) isA Scala ORM and DSL for talking with Databases with minimum verbosity and maximum type safety
* We can map tables to scala classes 
* There is DSL for quering
* It's easy to use
* Need to double check 
* It's using JDBC internally
```scala
var avg: Option[Float] = 
  from(grades)(g =>
    where(g.subjectId === mathId)  
    compute(avg(g.scoreInPercentage))
  )
```

###Slick###
[Slick](http://slick.typesafe.com/) is a modern database query and access library for Scala. It allows you to work with stored data almost as if you were using Scala collections while at the same time giving you full control over when a database access happens and which data is transferred. 
* Custom drivers provided by Typesafe, Oracle and SQLServer are with suspicious licence ;)
* Good documentation
* For more code go to [GETTING STARTED](http://slick.typesafe.com/doc/3.1.1/gettingstarted.html)
```scala
coffees.filter(_.price < 10.0).sortBy(_.name).map(_.name)
```
##Others##
###Akka###
[Akka](http://akka.io/) is a toolkit and runtime for building highly concurrent, distributed, and resilient message-driven applications on the JVM.
* Actors, Fault Tolerance, Location Transparency, Persistence
[Hello Akka](https://github.com/typesafehub/activator-hello-akka/blob/master/src/main/scala/HelloAkkaScala.scala)

```scala
import akka.actor.{ ActorRef, ActorSystem, Props, Actor, Inbox }
import scala.concurrent.duration._

case object Greet
case class WhoToGreet(who: String)
case class Greeting(message: String)

class Greeter extends Actor {
  var greeting = ""

  def receive = {
    case WhoToGreet(who) => greeting = s"hello, $who"
    case Greet           => sender ! Greeting(greeting) // Send the current greeting back to the sender
  }
}

object HelloAkkaScala extends App {
  val system = ActorSystem("helloakka")
  val greeter = system.actorOf(Props[Greeter], "greeter")
  val inbox = Inbox.create(system)
  greeter.tell(WhoToGreet("akka"), ActorRef.noSender)
  inbox.send(greeter, Greet)
  val Greeting(message1) = inbox.receive(5.seconds)
  println(s"Greeting: $message1")
  greeter.tell(WhoToGreet("typesafe"), ActorRef.noSender)
  inbox.send(greeter, Greet)
  val Greeting(message2) = inbox.receive(5.seconds)
  println(s"Greeting: $message2")
  val greetPrinter = system.actorOf(Props[GreetPrinter])
  system.scheduler.schedule(0.seconds, 1.second, greeter, Greet)(system.dispatcher, greetPrinter)
}

// prints a greeting
class GreetPrinter extends Actor {
  def receive = {
    case Greeting(message) => println(message)
  }
}
```

###RXScala###
* This is a [Scala adapter](https://github.com/ReactiveX/RxScala) to RxJava.
* Is lightweight.
* Focused on single concept.

```scala
Observable.just(1, 2, 3, 4)
  .filter(_ % 2 == 0)
  .map(x => s"[$x]")
  .subscribe(x => println(x))
```

###Scalaz###
[Scalaz](https://github.com/scalaz/scalaz) is a Scala library for functional programming.
* It's end of scala kindergarden
* [learning-scalaz](http://eed3si9n.com/learning-scalaz/)

