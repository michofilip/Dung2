package experiment.ex1

final class ConEntityEX(override val a: Int, override val b: Int) extends AbsEntityEX[ConEntityEX] {
    private def update(a: Int = a, b: Int = b): ConEntityEX = new ConEntityEX(a, b)
    
    protected override def setA(a: Int): ConEntityEX = update(a = a)
    
    protected override def setB(b: Int): ConEntityEX = update(b = b)
    
    override def toString = s"ConEntityEX($a, $b)"
}
