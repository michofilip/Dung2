package core.events

import core.entities._
import core.entities.properties._
import core.entities.repositoy.EntityRepository
import core.entities.templates.{Openable, Switchable}
import core.parts.position.{Coordinates, Direction}
import core.parts.program.Instruction._
import core.parts.program.Script
import core.parts.state.State
import core.parts.value.Value
import core.parts.value.basic.Implicits._
import core.parts.value.basic.UnitValue
import core.parts.value.custom.CustomLongValue.{GetTime, GetTurn}
import core.parts.value.custom.Implicits._
import json.{JSONParsable, JValue}

import scala.language.implicitConversions

sealed abstract class Event extends JSONParsable {
    val entityId: String
    
    def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event])
}

object Event {
    implicit def ev2Vector(event: Event): Vector[Event] = Vector(event)
    
    implicit def en2Vector(entity: Entity[_]): Vector[Entity[_]] = Vector.apply[Entity[_]](entity)
    
    final case class Delete(override val entityId: String) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            (Vector.empty, Vector.empty)
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    // position
    final case class MoveTo(override val entityId: String, x: Int, y: Int) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case ent: PositionHolder[_] =>
                    (ent.moveTo(x, y), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "x" -> x,
                "y" -> y
            )
        }
    }
    
    final case class MoveBy(override val entityId: String, dx: Int, dy: Int) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case ent: PositionHolder[_] =>
                    (ent.moveBy(dx, dy), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "dx" -> dx,
                "dy" -> dy
            )
        }
    }
    
    // TODO remove it
    // state
    //    @Deprecated
    //    final case class SetState(override val entityId: String, state: State) extends Event {
    //        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
    //            val time = GetTime().getOrElse(0)
    //            (entity, state) match {
    //                case (ent: Switchable, st: State.SwitchableState) =>
    //                    (ent.setSwitchableState(st, time), Vector.empty)
    //                case (ent: Openable, st: State.OpenableState) =>
    //                    (ent.setOpenableState(st, time), Vector.empty)
    //                case (ent: Character, st: State.CharacterState) =>
    //                    (ent.setCharacterState(st, time), Vector.empty)
    //                case _ =>
    //                    (entity, Vector.empty)
    //            }
    //        }
    //
    //        override def toJSON: JValue = {
    //            import json.MyJ._
    //            jObject(
    //                "class" -> this.getClass.getSimpleName,
    //                "entityId" -> entityId,
    //                "state" -> state
    //            )
    //        }
    //    }
    
    // switchable
    final case class SwitchOff(override val entityId: String) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            val tup: (Vector[Entity[_]], Vector[Event]) = entity match {
                case ent: Switchable[_] if ent.state == State.On =>
                    (Vector[Entity[_]](ent.beginSwitchingOff(GetTime("TimeCounter").getOrElse(0))), DelayTime(entityId, ent.switchingOffLength, SwitchOff(entityId)))
                case ent: Switchable[_] if ent.state == State.SwitchingOff =>
                    (Vector[Entity[_]](ent.finishSwitchingOff(GetTime("TimeCounter").getOrElse(0))), Vector.empty)
                case _ =>
                    (Vector[Entity[_]](entity), Vector.empty)
            }
            
            tup
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    final case class SwitchOn(override val entityId: String) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case ent: Switchable[_] if ent.state == State.Off =>
                    (ent.beginSwitchingOn(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.switchingOnLength, SwitchOn(entityId)))
                case ent: Switchable[_] if ent.state == State.SwitchingOn =>
                    (ent.finishSwitchingOn(GetTime("TimeCounter").getOrElse(0)), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    // openable
    final case class Open(override val entityId: String) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case ent: Openable[_] if ent.state == State.Close =>
                    (ent.beginOpening(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.openingLength, Open(entityId)))
                case ent: Openable[_] if ent.state == State.Opening =>
                    (ent.finishOpening(GetTime("TimeCounter").getOrElse(0)), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    final case class Close(override val entityId: String) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case ent: Openable[_] if ent.state == State.Open =>
                    (ent.beginClosing(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.closingLength, Close(entityId)))
                case ent: Openable[_] if ent.state == State.Closing =>
                    (ent.finishClosing(GetTime("TimeCounter").getOrElse(0)), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    final case class Unlock(override val entityId: String, keys: Set[Long]) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case ent: Openable[_] if ent.state == State.Locked =>
                    (ent.beginUnlocking(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.unlockingLength, Unlock(entityId, keys)))
                case ent: Openable[_] if ent.state == State.Closing =>
                    (ent.finishUnlocking(GetTime("TimeCounter").getOrElse(0)), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "keys" -> keys.toSeq
            )
        }
    }
    
    final case class Lock(override val entityId: String, keys: Set[Long]) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case ent: Openable[_] if ent.state == State.Close =>
                    (ent.beginLocking(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.lockingLength, Lock(entityId, keys)))
                case ent: Openable[_] if ent.state == State.Locking =>
                    (ent.finishLocking(GetTime("TimeCounter").getOrElse(0)), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "keys" -> keys.toSeq
            )
        }
    }
    
    // value
    final case class SetValue(override val entityId: String, value: Value) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case en: ValueHolder[_] =>
                    (en.setValue(value), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
    final case class SetCalculatedValue(override val entityId: String, value: Value) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case en: ValueHolder[_] =>
                    value.get match {
                        case Some(v: Boolean) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: Byte) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: Short) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: Int) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: Long) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: Float) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: Double) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: Char) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: String) =>
                            (en.setValue(v), Vector.empty)
                        
                        case Some(v: Coordinates) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: Direction) =>
                            (en.setValue(v), Vector.empty)
                        case Some(v: State) =>
                            (en.setValue(v), Vector.empty)
                        
                        case _ =>
                            (en.setValue(UnitValue), Vector.empty)
                    }
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
    // script
    final case class RunScript(override val entityId: String, scriptName: String) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case ent: ScriptHolder[_] =>
                    (entity, ExecuteScriptLine(entityId, ent.getScript(scriptName), 0))
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "scriptName" -> scriptName
            )
        }
    }
    
    final case class ExecuteScriptLine(override val entityId: String, script: Script, lineNo: Int) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            script.getInstruction(lineNo) match {
                case EX =>
                    (entity, Vector.empty)
                case DO(events) =>
                    (entity, ExecuteScriptLine(entityId, script, lineNo + 1) ++ events)
                case LB(_) =>
                    (entity, ExecuteScriptLine(entityId, script, lineNo + 1))
                case GT(labelId) =>
                    script.labelMap.get(labelId) match {
                        case Some(labelLineNo) => (entity, ExecuteScriptLine(entityId, script, labelLineNo + 1))
                        case None => (entity, ExecuteScriptLine(entityId, script, lineNo + 1))
                    }
                case IF(condition) =>
                    condition.get match {
                        case Some(true) => (entity, ExecuteScriptLine(entityId, script, lineNo + 2))
                        case Some(false) => (entity, ExecuteScriptLine(entityId, script, lineNo + 1))
                        case None => (entity, Vector.empty)
                    }
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "program" -> null
            )
        }
    }
    
    // time
    final case object StartTime extends Event {
        override val entityId: String = "TimeCounter"
        
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case en: TimeCounterHolder[_] if !en.isRunning =>
                    (en.start(), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case object StopTime extends Event {
        override val entityId: String = "TimeCounter"
        
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case en: TimeCounterHolder[_] if en.isRunning =>
                    (en.stop(), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class DelayTime(override val entityId: String, delay: Long, events: Vector[Event]) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            val time = GetTime("TimeCounter").getOrElse(0)
            (entity, ScheduleTime(entityId, time + delay, events))
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "delay" -> delay,
                "events" -> events
            )
        }
    }
    
    final case class ScheduleTime(override val entityId: String, timeStamp: Long, events: Vector[Event]) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            val time = GetTime("TimeCounter").getOrElse(0)
            if (time >= timeStamp)
                (entity, events)
            else
                (entity, this)
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "timeStamp" -> timeStamp,
                "events" -> events
            )
        }
    }
    
    // turn
    final case object NextTurn extends Event {
        override val entityId: String = "TurnCounter"
        
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            entity match {
                case en: TurnCounterHolder[_] =>
                    (en.nextTurn, Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName
            )
        }
    }
    
    final case class DelayTurns(override val entityId: String, delay: Long, events: Vector[Event]) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            val turn = GetTurn("TurnCounter").getOrElse(0)
            (entity, ScheduleTurns(entityId, turn + delay, events))
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "delay" -> delay,
                "events" -> events
            )
        }
    }
    
    final case class ScheduleTurns(override val entityId: String, turnStamp: Long, events: Vector[Event]) extends Event {
        override def applyTo(entity: Entity[_])(implicit entityHolder: EntityRepository): (Vector[Entity[_]], Vector[Event]) = {
            val turn = GetTurn("TurnCounter").getOrElse(0)
            if (turn >= turnStamp)
                (entity, events)
            else
                (entity, this)
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "turnStamp" -> turnStamp,
                "events" -> events
            )
        }
    }
    
}
