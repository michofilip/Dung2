package core.program

import core.event.Event
import core.program.Statement._
import core.value.Value

// TODO test class, remove later
object TestProg extends App {
    //    val prog1 = Program.Collatz(100)
    //    //  val prog2 = Program.DoorBehavior(0, 10, 1000, 1000, 1000, 1000)
    //    //  val prog3 = Program.SwitchBehavior(0, 10, 1000, 1000)
    //
    //    println(prog1.toPrettyString)
    //    println()
    //    //  println(prog2.toPrettyString)
    //    //  println()
    //    //  println(prog3.toPrettyString)
    //    //  println()
    
    def printSt(statement: Statement): Unit = {
        println(statement)
        println()
        println(statement.compile.zipWithIndex.map {
            case (inst, i) => i + " : " + inst
        }.mkString("\n"))
    }
    
    val stBlock =
        block(
            Event.Close("0"),
            Event.Delete("0"),
            Event.MoveBy("0", 2, 3)
        )
    
    val stWhen =
        when(Value(true))(
            when(Value(2) === Value(1))(
                Event.Close("0")
            )(
                Event.MoveBy("0", 2, 3)
            )
        )(
            Event.Open("0"),
            Event.Delete("0")
        )
    
    val stLoop =
        loop(Value(true))(
            Event.Open("0"),
            Event.Delete("0"),
            stWhen
        )
    
    val stChoose =
        choose(Value(2))(
            variant(Value(1))(
                Event.Delete("1")
            ),
            variant(Value(2))(
                Event.Delete("2")
            ),
            variant(Value(3))(
                Event.Delete("3")
            )
        )(
            Event.Delete("0")
        )
    
    printSt(stChoose)
    
    val prog = Script(stChoose)
    println(prog)
}
