package value

abstract class CharValue extends Value {
    override final protected type T = Char
    
    override final def calculate: CharValue = CharValue.CharCalculate(this)
}

object CharValue {
    
    final case object CharNull extends CharValue {
        override def get: Option[Char] = None
    }
    
    final case class CharConstant(value: Char) extends CharValue {
        override def get: Option[Char] = Some(value)
    }
    
    final case class CharCalculate(value: CharValue) extends CharValue {
        private val calculated: Option[Char] = value.get
        
        override def get: Option[Char] = calculated
    }
    
}