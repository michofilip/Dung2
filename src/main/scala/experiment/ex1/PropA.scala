package experiment.ex1

trait PropA[T <: PropA[T]] extends EntityEX[T] {
    val a: Int
    
    def setA(a: Int): T
}
