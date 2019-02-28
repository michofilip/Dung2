package experiment.ex1

object Test1 extends App {
    var en = new ConEntityEX(1, 2)
    println(en)
    en = en.setA(10)
    println(en)
    en = en.setB(20)
    println(en)
    en = en.setAB(100, 200)
    println(en)
    
    val en1: EntityEX[_] = new ConEntityEX(0, 0)
    println(en1)
    val en2 = en1 match {
        case pa: AbsEntityEX[_] => pa.setA(10).setB(20)
    }
    
    println(en2)
}
