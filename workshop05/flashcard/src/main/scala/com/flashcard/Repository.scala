package com.flashcard

import java.sql.{Connection, DriverManager, ResultSet}


object Repository {
  Class.forName("org.h2.Driver")
  private val connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "")

  //bootstrap
  private val stmt = connection.prepareStatement("CREATE TABLE FLASHCARD (ID INT PRIMARY KEY, WORD VARCHAR, TRANSLATION VARCHAR)")
  stmt.execute()


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
    new Repository(connection)
  }
}

class Repository(connection: Connection) {

  def +(e: (Int, Flashcard)): Repository = {
    val stmt = connection.prepareStatement("INSERT INTO FLASHCARD (ID, WORD, TRANSLATION) VALUES (?, ?, ?)")
    stmt.setInt(1, e._1)
    stmt.setString(2, e._2.word)
    stmt.setString(3, e._2.translation)

    stmt.executeUpdate()

    this
  }

  def :+(e: (Int, Flashcard)): Repository = {
    val stmt = connection.prepareStatement("UPDATE FLASHCARD SET WORD = ?, TRANSLATION = ? WHERE ID = ?")
    stmt.setString(1, e._2.word)
    stmt.setString(2, e._2.translation)
    stmt.setInt(3, e._1)

    stmt.executeUpdate()

    this
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

  def -(id: Int) = {
    val stmt = connection.prepareStatement("DELETE FROM FLASHCARD WHERE ID = ?")
    stmt.setInt(1, id)

    stmt.execute()

    this
  }


  def findAll(implicit toList: Converter[ResultSet, List[Flashcard]]) = {
    val stmt = connection.prepareStatement("SELECT ID, WORD, TRANSLATION FROM FLASHCARD ORDER BY ID")
    toList.convert(stmt.executeQuery())
  }
}
