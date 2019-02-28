package experiment.ex1

trait PropB[T <: PropB[T]] extends EntityEX[T] {
    val b: Int
    
    def setB(b: Int): T
}
