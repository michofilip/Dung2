package value

abstract class CharValue extends Value {
    override final protected type T = Char
}

object CharValue {
    
    final case object CharNull extends CharValue {
        override def get: Option[Char] = None
    }
    
    final case class CharConstant(value: Char) extends CharValue {
        override def get: Option[Char] = Some(value)
    }
    
}