package core.parts.program

import core.parts.program.Instruction._
import core.parts.program.Statement.{Block, Choose, Execute, Loop, Variant, When}
import core.parts.value.Value

import scala.language.implicitConversions

object Compiler {
    
    def compile(statement: Statement): Vector[Instruction] = {
        implicit def i2seq(instruction: Instruction): Vector[Instruction] = Vector(instruction)
        
        def compVariant(switchTest: Value, variant: Variant, defId: Int, nextLabelId: Int): (Vector[Instruction], Int) = {
            val (varInnerInst, nextLabelId1) = comp(Vector(variant.variantStatement), Vector.empty, nextLabelId + 1)
            val varInst =
                TEST(switchTest === variant.variantTest) ++
                        GOTO(nextLabelId) ++
                        varInnerInst ++
                        GOTO(defId) ++
                        LABEL(nextLabelId)
            (varInst, nextLabelId1)
        }
        
        def compVariants(switchTest: Value, variants: Vector[Variant], defId: Int, inst: Vector[Instruction], nextLabelId: Int): (Vector[Instruction], Int) = {
            variants match {
                case variant +: rest =>
                    val (varInst, nextLabelId1) = compVariant(switchTest, variant, defId, nextLabelId)
                    compVariants(switchTest, rest, defId, inst ++ varInst, nextLabelId1)
                case _ => (inst, nextLabelId)
            }
        }
        
        def comp(sts: Vector[Statement], inst: Vector[Instruction], nextLabelId: Int): (Vector[Instruction], Int) = {
            sts match {
                case st +: rest =>
                    st match {
                        case Execute(events) =>
                            comp(rest, inst ++ EXECUTE(events), nextLabelId)
                        
                        case Block(statements) =>
                            val (blockInstructions, afterBlockLabelId) = comp(statements, Vector.empty, nextLabelId)
                            comp(rest, inst ++ blockInstructions, afterBlockLabelId)
                        
                        case When(condition, thenStatement, elseStatement) =>
                            val elseLabelId = nextLabelId
                            val exitLabelId = nextLabelId + 1
                            val (thenInstructions, afterThenLabelId) = comp(Vector(thenStatement), Vector.empty, exitLabelId + 1)
                            val (elseInstructions, afterElseLabelId) = comp(Vector(elseStatement), Vector.empty, afterThenLabelId + 1)
                            val whenInst =
                                TEST(condition) ++
                                        GOTO(elseLabelId) ++
                                        thenInstructions ++
                                        GOTO(exitLabelId) ++
                                        LABEL(elseLabelId) ++
                                        elseInstructions ++
                                        LABEL(exitLabelId)
                            comp(rest, inst ++ whenInst, afterElseLabelId)
                        
                        case Loop(condition, loopedStatement) =>
                            val loopLabelId = nextLabelId
                            val exitLabelId = nextLabelId + 1
                            val (loopedInstructions, afterLoopLabelId) = comp(Vector(loopedStatement), Vector.empty, exitLabelId + 1)
                            val loopInst =
                                LABEL(loopLabelId) ++
                                        TEST(condition) ++
                                        GOTO(exitLabelId) ++
                                        loopedInstructions ++
                                        GOTO(loopLabelId) ++
                                        LABEL(exitLabelId)
                            comp(rest, inst ++ loopInst, afterLoopLabelId)
                        
                        case Choose(switchTest, variants, defaultStatement) =>
                            val defId = nextLabelId
                            val (varInnerInst, nextLabelId1) = compVariants(switchTest, variants, defId, Vector.empty, nextLabelId + 1)
                            val (defInst, nextLabelId2) = comp(Vector(defaultStatement), Vector.empty, nextLabelId1)
                            val varInst =
                                varInnerInst ++
                                        defInst ++
                                        LABEL(defId)
                            comp(rest, inst ++ varInst, nextLabelId2)
                    }
                case _ => (inst, nextLabelId)
            }
        }
        
        val (instructions, _) = comp(Vector(statement), Vector.empty, 0)
        instructions ++ EXIT
    }
    
    
}
