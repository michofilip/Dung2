package core.program

import core.program.Instruction._

class Script(private val instructions: Vector[Instruction]) {
    val labelMap: Map[Int, Int] = {
        instructions.zipWithIndex.foldLeft(Map.empty[Int, Int]) {
            case (labels, (LB(labelId), lineNo)) => labels + (labelId -> lineNo)
            case (labels, _) => labels
        }
    }
    
    def getInstruction(lineNo: Int): Instruction = {
        if (0 <= lineNo && lineNo < instructions.length)
            instructions(lineNo)
        else
            EX
    }
    
    //    def nextLine(line: Int): Int = {
    //        def nl(line: Int): Int = {
    //            getInstruction(line) match {
    //                case LB(_) => nl(line + 1)
    //                case GT(labelId) => labelMap.get(labelId) match {
    //                    case Some(l) => nl(l + 1)
    //                    case None => nl(line + 1)
    //                }
    //                case _ => line
    //            }
    //        }
    //
    //        nl(line)
    //    }
}

object Script {
    def apply(statement: Statement): Script = new Script(statement.compile)
    
    val emptyScript: Script = new Script(Vector(EX))
}
