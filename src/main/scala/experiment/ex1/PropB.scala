package experiment.ex1

trait PropB[T <: PropB[T]] extends EntityEX[T] {
    val b: Int
    
    protected def setB(b: Int): T
}
