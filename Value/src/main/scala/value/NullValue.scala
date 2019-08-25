package value

case object NullValue extends Value {
    override final protected type T = Unit
    
    override def get: Option[Unit] = None
}
