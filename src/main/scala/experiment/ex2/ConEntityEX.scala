//package experiment.ex2
//
//final class ConEntityEX(override val a: Int, override val b: Int) extends AbsEntityEX {
//    override protected type T >: ConEntityEX
//
//    private def update(a: Int = a, b: Int = b): ConEntityEX = new ConEntityEX(a, b)
//
//    override def setA(a: Int): ConEntityEX = update(a = a)
//
//    override def setB(b: Int): ConEntityEX = update(b = b)
//
//    override def toString = s"ConEntityEX($a, $b)"
//}
