package core.parts.scripts

import core.events.Event.Delete
import core.parts.scripts.Instruction._
import core.parts.state.State.Open
import value.ValueImports._
import core.parts.value.CustomValueImports._
import core.parts.value.StateValue.GetState

class Script(private val instructions: Vector[Instruction]) {
    private val labelMap: Map[Int, Int] = {
        instructions.zipWithIndex.foldLeft(Map.empty[Int, Int]) {
            case (labels, (LABEL(labelId), lineNo)) => labels + (labelId -> lineNo)
            case (labels, _) => labels
        }
    }
    
    def getInstruction(lineNo: Int): Instruction = {
        if (0 <= lineNo && lineNo < instructions.length)
            instructions(lineNo)
        else
            EXIT(1)
    }
    
    def getLineNo(labelId: Int): Option[Int] = {
        labelMap.get(labelId)
    }
}

object Script {
    def apply(statement: Statement): Script = new Script(statement.compile ++ Vector(EXIT(0)))
    
    val emptyScript: Script = new Script(Vector(EXIT(0)))
    
//    def autoClose(entityId: Long): Script = {
//        import Statement._
//        val statement =
//            loop(true)(
//                choose(GetState(entityId))(
//                    variant(Open)(
//                        when(true)(
//                            Delete(entityId)
//                        )(),
//                        Delete(entityId),
//                        Delete(entityId)
//                    )
//                )()
//
//            )
//
//        val instructions = statement.compile
//        new Script(instructions)
//    }
}
