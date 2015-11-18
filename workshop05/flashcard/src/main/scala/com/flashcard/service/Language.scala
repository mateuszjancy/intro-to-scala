package com.flashcard.service

import com.flashcard.{Flashcard, Store}

case class Put[A](key: Int, value: Flashcard, a: A) extends Store[A]

case class Update[A](key: Int, value: Flashcard, a: A) extends Store[A]

case class Get[A](key: Int, h: Flashcard => A) extends Store[A]

case class Delete[A](key: Int, a: A) extends Store[A]

