package experiment.ex1

trait PropA[T <: PropA[T]] extends Entity[T] {
    val a: Int
    
    def setA(a: Int): T
}
