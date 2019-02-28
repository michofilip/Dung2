package experiment.ex1

trait PropA[T <: PropA[T]] extends EntityEX[T] {
    val a: Int
    
    protected def setA(a: Int): T
}
