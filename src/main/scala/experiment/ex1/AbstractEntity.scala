package experiment.ex1

abstract class AbstractEntity[T <: AbstractEntity[T]] extends PropA[T] with PropB[T] {
    def setAB(a: Int, b: Int): T = setA(a).setB(b)
}
