package core.program

import core.event.Event
import core.program.Instruction._
import core.value.Value
import core.value.Value.BasicValue.BooleanValue

import scala.language.implicitConversions

sealed abstract class Statement {
    protected def compile(initialInstructions: Vector[Instruction], initialLabelId: Int): (Vector[Instruction], Int)
    
    def compile: Vector[Instruction] = {
        val (instructions, _) = compile(Vector.empty, 0)
        instructions
    }
}

object Statement {
    
    implicit def inst2Vec(instruction: Instruction): Vector[Instruction] = Vector(instruction)
    
    implicit def ev2Do(event: Event): Do = Do(event)
    
    implicit def evs2Do(events: Vector[Event]): Do = Do(events)
    
    def block(statements: Statement*): Block = {
        Block(statements.toVector)
    }
    
    def when(condition: BooleanValue)(thenStatements: Statement*)(elseStatements: Statement*): When = {
        When(condition, Block(thenStatements.toVector), Block(elseStatements.toVector))
    }
    
    def loop(condition: BooleanValue)(loopedStatements: Statement*): Loop = {
        Loop(condition, Block(loopedStatements.toVector))
    }
    
    def variant(variantTest: Value)(variantStatements: Statement*): Variant = {
        Variant(variantTest, Block(variantStatements.toVector))
    }
    
    def choose(switchTest: Value)(variants: Variant*)(defaultStatements: Statement*): Choose = {
        Choose(switchTest, variants.toVector, Block(defaultStatements.toVector))
    }
    
    case class Do(events: Vector[Event]) extends Statement {
        override protected def compile(initialInstructions: Vector[Instruction], initialLabelId: Int): (Vector[Instruction], Int) = {
            (initialInstructions ++ DO(events), initialLabelId)
        }
    }
    
    case class Block(statements: Vector[Statement]) extends Statement {
        override protected def compile(initialInstructions: Vector[Instruction], initialLabelId: Int): (Vector[Instruction], Int) = {
            val (blockInstructions, afterBlockLabelId) = statements.foldLeft((initialInstructions, initialLabelId)) {
                case ((instructions, labelId), st) => st.compile(instructions, labelId)
            }
            (initialInstructions ++ blockInstructions, afterBlockLabelId)
        }
    }
    
    case class When(condition: BooleanValue, thenStatement: Statement, elseStatement: Statement) extends Statement {
        override protected def compile(initialInstructions: Vector[Instruction], initialLabelId: Int): (Vector[Instruction], Int) = {
            val elseLabelId = initialLabelId
            val exitLabelId = initialLabelId + 1
            
            val (thenInstructions, afterThenLabelId) = thenStatement.compile(Vector.empty, exitLabelId + 1)
            val (elseInstructions, afterElseLabelId) = elseStatement.compile(Vector.empty, afterThenLabelId + 1)
            
            val instructions = IF(condition) ++
                    GT(elseLabelId) ++
                    thenInstructions ++
                    GT(exitLabelId) ++
                    LB(elseLabelId) ++
                    elseInstructions ++
                    LB(exitLabelId)
            
            (initialInstructions ++ instructions, afterElseLabelId)
        }
    }
    
    case class Loop(condition: BooleanValue, loopedStatement: Statement) extends Statement {
        override protected def compile(initialInstructions: Vector[Instruction], initialLabelId: Int): (Vector[Instruction], Int) = {
            val loopLabelId = initialLabelId
            val exitLabelId = initialLabelId + 1
            
            val (loopedInstructions, afterLoopLabelId) = loopedStatement.compile(Vector.empty, exitLabelId + 1)
            
            val instructions = LB(loopLabelId) ++
                    IF(condition) ++
                    GT(exitLabelId) ++
                    loopedInstructions ++
                    GT(loopLabelId) ++
                    LB(exitLabelId)
            
            (initialInstructions ++ instructions, afterLoopLabelId)
        }
    }
    
    case class Variant(variantTest: Value, variantStatement: Statement) {
        
        def compile(initialInstructions: Vector[Instruction], switchTest: Value, exitLabelId: Int, initialLabelId: Int): (Vector[Instruction], Int) = {
            val variantExitLabelId = initialLabelId
            val (variantInstructions, afterVariantLabelId) = variantStatement.compile(Vector.empty, variantExitLabelId + 1)
            
            val instructions = IF(switchTest === variantTest) ++
                    GT(variantExitLabelId) ++
                    variantInstructions ++
                    GT(exitLabelId) ++
                    LB(variantExitLabelId)
            (initialInstructions ++ instructions, afterVariantLabelId)
        }
    }
    
    case class Choose(switchTest: Value, variants: Vector[Variant], defaultStatement: Statement) extends Statement {
        override protected def compile(initialInstructions: Vector[Instruction], initialLabelId: Int): (Vector[Instruction], Int) = {
            val exitLabelId = initialLabelId
            
            val (variantInstructions, afterVariantLabelId) = variants.foldLeft((Vector.empty[Instruction], exitLabelId + 1)) {
                case ((previousVariantInstructions, labelId), variant) =>
                    variant.compile(previousVariantInstructions, switchTest, exitLabelId, labelId)
            }
            val (defaultInstructions, afterDefaultLabelId) = defaultStatement.compile(Vector.empty, afterVariantLabelId)
            
            val instructions = variantInstructions ++
                    defaultInstructions ++
                    LB(exitLabelId)
            
            (initialInstructions ++ instructions, afterDefaultLabelId)
        }
    }
    
}