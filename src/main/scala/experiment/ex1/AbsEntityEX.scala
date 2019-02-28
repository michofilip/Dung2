package experiment.ex1

abstract class AbsEntityEX[T <: AbsEntityEX[T]] extends PropA[T] with PropB[T] {
    
    def setAB(a: Int, b: Int): T = setA(a).setB(b)
    
}
