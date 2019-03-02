package core.parts.program

import core.events.Event
import core.parts.value.basic.BooleanValue

sealed abstract class Instruction

object Instruction {
    
    final case object EXIT extends Instruction
    
    final case class ERROR(code: Int) extends Instruction
    
    final case class EXECUTE(events: Vector[Event]) extends Instruction
    
    final case class LABEL(labelId: Int) extends Instruction
    
    final case class GOTO(labelId: Int) extends Instruction
    
    final case class TEST(condition: BooleanValue) extends Instruction
    
}