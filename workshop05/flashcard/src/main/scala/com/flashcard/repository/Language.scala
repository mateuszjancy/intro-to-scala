package com.flashcard.repository

import com.flashcard.Store

case class CreateTable[A](sql: String, a: A) extends Store[A]
