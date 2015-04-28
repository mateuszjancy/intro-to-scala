implicit class Hello(who: String) {
  def :| = s"Hello $who"

  def :>(and: String) = s"Hello $who and $and"
}

"Mateusz" :|

"Mateusz" :> "Marta"