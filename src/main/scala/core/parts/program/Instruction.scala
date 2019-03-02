package core.parts.program

import core.events.Event
import core.parts.value.basic.BooleanValue

sealed abstract class Instruction

object Instruction {
    
    final case object EX extends Instruction
    
    final case class ER(code: Int) extends Instruction
    
    final case class DO(events: Vector[Event]) extends Instruction
    
    final case class LB(labelId: Int) extends Instruction
    
    final case class GT(labelId: Int) extends Instruction
    
    final case class IF(condition: BooleanValue) extends Instruction
    
}