//package core.program
//
//import core.event.Event
//import core.program.Statement._
//import core.value.basic.Implicits._
//
//// TODO test class, remove later
//object TestProg extends App {
//    //    val prog1 = Program.Collatz(100)
//    //    //  val prog2 = Program.DoorBehavior(0, 10, 1000, 1000, 1000, 1000)
//    //    //  val prog3 = Program.SwitchBehavior(0, 10, 1000, 1000)
//    //
//    //    println(prog1.toPrettyString)
//    //    println()
//    //    //  println(prog2.toPrettyString)
//    //    //  println()
//    //    //  println(prog3.toPrettyString)
//    //    //  println()
//
//    def printSt(statement: Statement): Unit = {
//        println(statement)
//        println()
//        println(statement.compile.zipWithIndex.map {
//            case (inst, i) => i + " : " + inst
//        }.mkString("\n"))
//    }
//
//    val stBlock =
//        block(
//            Event.Close("0"),
//            Event.Delete("0"),
//            Event.MoveBy("0", 2, 3)
//        )
//
//    val stWhen =
//        when(true)(
//            when(2 === 1)(
//                Event.Close("0")
//            )(
//                Event.MoveBy("0", 2, 3)
//            )
//        )(
//            Event.Open("0"),
//            Event.Delete("0")
//        )
//
//    val stLoop =
//        loop(true)(
//            Event.Open("0"),
//            Event.Delete("0"),
//            stWhen
//        )
//
//    val stChoose =
//        choose(2)(
//            variant(1)(
//                Event.Delete("1")
//            ),
//            variant(2)(
//                Event.Delete("2")
//            ),
//            variant(3)(
//                Event.Delete("3")
//            )
//        )(
//            Event.Delete("0")
//        )
//
//    printSt(stChoose)
//
//    val prog = Script(stChoose)
//    println(prog)
//}
