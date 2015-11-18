package com.flashcard

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}


object DB {
  Class.forName("org.h2.Driver")
  private val connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "")

  implicit val converter2Flashcard = new Converter[ResultSet, Flashcard] {
    override def convert(rs: ResultSet): Flashcard = Flashcard(
      rs.getInt(1),
      rs.getString(2),
      rs.getString(3)
    )
  }

  implicit val converter2List = new Converter[ResultSet, List[Flashcard]] {
    override def convert(rs: ResultSet): List[Flashcard] = {
      var l = List.empty[Flashcard]
      while (rs.next()) {
        l = l :+ converter2Flashcard.convert(rs)
      }

      l
    }
  }

  def apply() = {
    new DB(connection)
  }
}

class DB(connection: Connection) {

  def call(sql: String)(f: PreparedStatement => PreparedStatement): DB = {
    val stmt = connection.prepareStatement(sql)
    f(stmt).executeUpdate()
    this
  }

  def createTable(sql: String): DB = {
    val stmt = connection.prepareStatement(sql)
    stmt.execute()
    this
  }

  def +(e: (Int, Flashcard)): DB = call("INSERT INTO FLASHCARD (ID, WORD, TRANSLATION) VALUES (?, ?, ?)") { stmt =>
    stmt.setInt(1, e._1)
    stmt.setString(2, e._2.word)
    stmt.setString(3, e._2.translation)
    stmt
  }

  def :+(e: (Int, Flashcard)): DB = call("UPDATE FLASHCARD SET WORD = ?, TRANSLATION = ? WHERE ID = ?") { stmt =>
    stmt.setString(1, e._2.word)
    stmt.setString(2, e._2.translation)
    stmt.setInt(3, e._1)
    stmt
  }

  def get(id: Int)(implicit converter: Converter[ResultSet, Flashcard]): Flashcard = {
    val stmt = connection.prepareStatement("SELECT ID, WORD, TRANSLATION FROM FLASHCARD WHERE ID = ?")
    stmt.setInt(1, id)
    converter.convert {
      val r = stmt.executeQuery()
      r.next()
      r
    }
  }

  def -(id: Int) = call("DELETE FROM FLASHCARD WHERE ID = ?") { stmt =>
    stmt.setInt(1, id)
    stmt
  }


  def findAll(implicit toList: Converter[ResultSet, List[Flashcard]]) = {
    val stmt = connection.prepareStatement("SELECT ID, WORD, TRANSLATION FROM FLASHCARD ORDER BY ID")
    toList.convert(stmt.executeQuery())
  }
}
