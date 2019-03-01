package experiment.ex1

final class FinalEntity(override val a: Int, override val b: Int) extends AbstractEntity[FinalEntity] {
    private def update(a: Int = a, b: Int = b): FinalEntity = new FinalEntity(a, b)
    
    override def setA(a: Int): FinalEntity = update(a = a)
    
    override def setB(b: Int): FinalEntity = update(b = b)
    
    override def toString = s"ConEntityEX($a, $b)"
}
