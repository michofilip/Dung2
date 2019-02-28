package experiment.ex1

object Test1 extends App {
    var en = new ConEntityEX(1, 2)
    println(en)
    //    en = en.setA(10)
    //    println(en)
    //    en = en.setB(20)
    //    println(en)
    en = en.setAB(100, 200)
    println(en)
}
