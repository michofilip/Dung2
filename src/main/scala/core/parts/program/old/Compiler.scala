//package core.parts.program
//
//import core.parts.program.Instruction._
//import core.parts.program.Statement._
//import core.parts.value.Value
//
//import scala.language.implicitConversions
//
//object Compiler {
//    //    def mapLabels(instructions: Vector[Instruction]): Map[Int, Int] = {
//    //        def m(inst: Vector[Instruction], line: Int, labels: Map[Int, Int]): Map[Int, Int] = {
//    //            inst match {
//    //                case in +: rest =>
//    //                    in match {
//    //                        case LB(labelId) => m(rest, line + 1, labels + (labelId -> line))
//    //                        case _ => m(rest, line + 1, labels)
//    //                    }
//    //                case _ => labels
//    //            }
//    //        }
//    //
//    //        m(instructions, 0, Map.empty)
//    //    }
//
//    def mapLabels(instructions: Vector[Instruction]): Map[Int, Int] = {
//        instructions.zipWithIndex.foldLeft(Map.empty[Int, Int]) {
//            case (labels, (LB(labelId), lineNo)) => labels + (labelId -> lineNo)
//            case (labels, _) => labels
//        }
//    }
//
////    def compile(statement: Statement): Vector[Instruction] = {
////        implicit def i2seq(instruction: Instruction): Vector[Instruction] = Vector(instruction)
////
////        def compVariant(switchTest: Value, variant: Variant, defId: Int, nextLabelId: Int): (Vector[Instruction], Int) = {
////            val (varInnerInst, nextLabelId1) = comp(Vector(variant.variantStatement), Vector.empty, nextLabelId + 1)
////            val varInst =
////                IF(switchTest === variant.variantTest) ++
////                        GT(nextLabelId) ++
////                        varInnerInst ++
////                        GT(defId) ++
////                        LB(nextLabelId)
////            (varInst, nextLabelId1)
////        }
////
////        def compVariants(switchTest: Value, variants: Vector[Variant], defId: Int, inst: Vector[Instruction], nextLabelId: Int): (Vector[Instruction], Int) = {
////            variants match {
////                case variant +: rest =>
////                    val (varInst, nextLabelId1) = compVariant(switchTest, variant, defId, nextLabelId)
////                    compVariants(switchTest, rest, defId, inst ++ varInst, nextLabelId1)
////                case _ => (inst, nextLabelId)
////            }
////        }
////
////        def comp(sts: Vector[Statement], inst: Vector[Instruction], nextLabelId: Int): (Vector[Instruction], Int) = {
////            sts match {
////                case st +: rest =>
////                    st match {
////                        case Do(events) =>
////                            comp(rest, inst ++ DO(events), nextLabelId)
////                        case Block(statements) =>
////                            val (blockInst, nextLabelId1) = comp(statements, Vector.empty, nextLabelId)
////                            comp(rest, inst ++ blockInst, nextLabelId1)
////                        case When(condition, thenStatement, elseStatement) =>
////                            val labelId1 = nextLabelId
////                            val labelId2 = nextLabelId + 1
////                            val (thenInstructions, nextLabelId1) = comp(Vector(thenStatement), Vector.empty, nextLabelId + 2)
////                            val (elseInstructions, nextLabelId2) = comp(Vector(elseStatement), Vector.empty, nextLabelId1)
////                            val whenInst =
////                                IF(condition) ++
////                                        GT(labelId1) ++
////                                        thenInstructions ++
////                                        GT(labelId2) ++
////                                        LB(labelId1) ++
////                                        elseInstructions ++
////                                        LB(labelId2)
////                            comp(rest, inst ++ whenInst, nextLabelId2)
////                        case Loop(condition, loopedStatement) =>
////                            val labelId1 = nextLabelId
////                            val labelId2 = nextLabelId + 1
////                            val (loopedInst, nextLabelId1) = comp(Vector(loopedStatement), Vector.empty, nextLabelId + 2)
////                            val loopInst =
////                                LB(labelId1) ++
////                                        IF(condition) ++
////                                        GT(labelId2) ++
////                                        loopedInst ++
////                                        GT(labelId1) ++
////                                        LB(labelId2)
////                            comp(rest, inst ++ loopInst, nextLabelId1)
////                        case Choose(switchTest, variants, defaultStatement) =>
////                            val defId = nextLabelId
////                            val (varInnerInst, nextLabelId1) = compVariants(switchTest, variants, defId, Vector.empty, nextLabelId + 1)
////                            val (defInst, nextLabelId2) = comp(Vector(defaultStatement), Vector.empty, nextLabelId1)
////                            val varInst =
////                                varInnerInst ++
////                                        defInst ++
////                                        LB(defId)
////                            comp(rest, inst ++ varInst, nextLabelId2)
////                    }
////                case _ => (inst, nextLabelId)
////            }
////        }
////
////        val (instructions, _) = comp(Vector(statement), Vector.empty, 0)
////        instructions ++ EX
////    }
//
//}
