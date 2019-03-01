package experiment.ex1

trait PropB[T <: PropB[T]] extends Entity[T] {
    val b: Int
    
    def setB(b: Int): T
}
