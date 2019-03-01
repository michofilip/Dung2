package experiment.ex1

object Test1 extends App {
    var en = new FinalEntity(1, 2)
    println(en)
    en = en.setA(10)
    println(en)
    en = en.setB(20)
    println(en)
    en = en.setAB(100, 200)
    println(en)
    
    val en1: Entity[_] = new FinalEntity(0, 0)
    println(en1)
    val en2 = en1 match {
        case pa: AbstractEntity[_] => pa.setAB(10, 10)
    }
    
    def setAB(en: Entity[_], a: Int, b: Int): Entity[_] = {
        en match {
            case pa: AbstractEntity[_] => pa.setAB(a, b)
        }
    }
    
    println(en2)
    println(setAB(en2, 100, 100))
}
