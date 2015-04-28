class HelloSource(hello: String) {
  def hello(who: String): String = s"$hello $who"
}


object HelloSource {
  implicit val czescSource = new HelloSource("Cześć")
}

def hello(who: String)(implicit hs: HelloSource): String = {
  hs.hello(who)
}

hello("Mateusz")

