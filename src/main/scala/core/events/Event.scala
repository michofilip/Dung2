package core.events

import core.entity2.Entity2
import core.entity2.traits._
import core.entity2.traits.properties.{PositionHolder2, ScriptHolder2, TimeCounterHolder2, TurnCounterHolder2, ValueHolder2}
import core.entity2.traits.templates.{ClosingCapable, LockingCapable, SwitchingCapable}
import core.parts.position.{Coordinates, Direction}
import core.parts.scripts.Instruction._
import core.parts.scripts.Script
import core.parts.state.State
import core.parts.value.Value
import core.parts.value.basic.Implicits._
import core.parts.value.custom.CustomLongValue.{GetTime, GetTurn}
import core.parts.value.custom.Implicits._
import core.repository.EntityRepository2
import json.{JSONParsable, JValue}

import scala.language.implicitConversions

sealed abstract class Event extends JSONParsable {
    val entityId: String
    
    def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event])
}

object Event {
    implicit def en2Vector(entity: Entity2): Vector[Entity2] = Vector[Entity2](entity)
    
    implicit def ev2Vector(event: Event): Vector[Event] = Vector(event)
    
    // entity amount
    final case class Create(override val entityId: String) extends Event {
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = ???
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    final case class Delete(override val entityId: String) extends Event {
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: PositionHolder2 =>
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: PositionHolder2 =>
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
    
    
    // state
    // TODO remove it
    
    //    @Deprecated
    //    final case class SetState(override val entityId: String, state: State) extends Event {
    //        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: SwitchingCapable if ent.state == State.On =>
                    (ent.beginSwitchingOff(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.switchingOffDuration, SwitchOff(entityId)))
                case ent: SwitchingCapable if ent.state == State.SwitchingOff =>
                    (ent.finishSwitchingOff(GetTime("TimeCounter").getOrElse(0)), Vector.empty)
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
    
    final case class SwitchOn(override val entityId: String) extends Event {
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: SwitchingCapable if ent.state == State.Off =>
                    (ent.beginSwitchingOn(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.switchingOnDuration, SwitchOn(entityId)))
                case ent: SwitchingCapable if ent.state == State.SwitchingOn =>
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: ClosingCapable if ent.state == State.Close =>
                    (ent.beginOpening(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.openingDuration, Open(entityId)))
                case ent: ClosingCapable if ent.state == State.Opening =>
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: ClosingCapable if ent.state == State.Open =>
                    (ent.beginClosing(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.closingDuration, Close(entityId)))
                case ent: ClosingCapable if ent.state == State.Closing =>
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: LockingCapable if ent.state == State.Locked =>
                    (ent.beginUnlocking(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.unlockingDuration, Unlock(entityId, keys)))
                case ent: LockingCapable if ent.state == State.Closing =>
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: LockingCapable if ent.state == State.Close =>
                    (ent.beginLocking(GetTime("TimeCounter").getOrElse(0)), DelayTime(entityId, ent.lockingDuration, Lock(entityId, keys)))
                case ent: LockingCapable if ent.state == State.Locking =>
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
    final case class SetValue(override val entityId: String, name: String, value: Value) extends Event {
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case en: ValueHolder2 =>
                    (en.setValue(name, value), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "name" -> name,
                "value" -> value
            )
        }
    }
    
    final case class RemoveValue(override val entityId: String, name: String) extends Event {
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case en: ValueHolder2 =>
                    (en.removeValue(name), Vector.empty)
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "name" -> name
            )
        }
    }
    
    final case class SetCalculatedValue(override val entityId: String, name: String, value: Value) extends Event {
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case en: ValueHolder2 =>
                    value.get match {
                        case Some(v: Boolean) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: Byte) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: Short) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: Int) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: Long) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: Float) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: Double) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: Char) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: String) =>
                            (en.setValue(name, v), Vector.empty)
                        
                        case Some(v: Coordinates) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: Direction) =>
                            (en.setValue(name, v), Vector.empty)
                        case Some(v: State) =>
                            (en.setValue(name, v), Vector.empty)
                        
                        case _ =>
                            (en.removeValue(name), Vector.empty)
                    }
                case _ =>
                    (entity, Vector.empty)
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "name" -> name,
                "value" -> value
            )
        }
    }
    
    // script
    final case class RunScript(override val entityId: String, scriptName: String) extends Event {
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case ent: ScriptHolder2 =>
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            script.getInstruction(lineNo) match {
                case EXIT(_) =>
                    (entity, Vector.empty)
                case EXECUTE(events) =>
                    (entity, events ++ ExecuteScriptLine(entityId, script, lineNo + 1))
                case LABEL(_) =>
                    (entity, ExecuteScriptLine(entityId, script, lineNo + 1))
                case GOTO(labelId) =>
                    script.getLineNo(labelId) match {
                        case Some(labelLineNo) => (entity, ExecuteScriptLine(entityId, script, labelLineNo + 1))
                        case None => (entity, ExecuteScriptLine(entityId, script, lineNo + 1))
                    }
                case TEST(condition) =>
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
        
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case en: TimeCounterHolder2 if !en.isTimerRunning =>
                    (en.startTimer(), Vector.empty)
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
        
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case en: TimeCounterHolder2 if en.isTimerRunning =>
                    (en.stopTimer(), Vector.empty)
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
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
        
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
            entity match {
                case en: TurnCounterHolder2 =>
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
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
        override def applyTo(entity: Entity2)(implicit entityRepository: EntityRepository2): (Vector[Entity2], Vector[Event]) = {
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
