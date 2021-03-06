package value

abstract class StringValue extends Value {
    override final protected type T = String
    
    final def +(that: StringValue): StringValue = StringValue.Concatenate(this, that)
    
    final def length: IntValue = StringValue.Length(this)
}

object StringValue {
    
    final case object StringNull extends StringValue {
        override def get: Option[String] = None
    }
    
    final case class StringConstant(value: String) extends StringValue {
        override def get: Option[String] = Some(value)
    }
    
    final case class Concatenate(value1: StringValue, value2: StringValue) extends StringValue {
        override def get: Option[String] = (value1.get, value2.get) match {
            case (Some(v1), Some(v2)) => Some(v1 + v2)
            case _ => None
        }
    }
    
    final case class Length(value: StringValue) extends IntValue {
        override def get: Option[Int] = value.get match {
            case Some(v) => Some(v.length)
            case _ => None
        }
    }
    
    final case class NumericToString(value: NumericValue) extends StringValue {
        override def get: Option[String] = value.get match {
            case Some(v: Byte) => Some(v.toString)
            case Some(v: Short) => Some(v.toString)
            case Some(v: Int) => Some(v.toString)
            case Some(v: Long) => Some(v.toString)
            case Some(v: Float) => Some(v.toString)
            case Some(v: Double) => Some(v.toString)
            case _ => None
        }
    }
    
}