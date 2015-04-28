object Conversion {
    case class ConversionOp(who: String) {
      def :| = s"Hello $who"
      def :> (and: String) = s"Hello $who and $and"
    }
    implicit def string2Conversion(s: String): ConversionOp = ConversionOp(s)
}

import Conversion.string2Conversion

"Mateusz" :> "Marta"
"Mateusz" :|

