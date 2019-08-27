package core.events

import core.entities.Entity
import core.entities.traits.properties._
import core.entities.traits.templates.{ClosingCapable, LockingCapable, SwitchingCapable}
import core.parts.position.{Coordinates, Direction}
import core.parts.scripts.Instruction._
import core.parts.scripts.Script
import core.parts.state.State
import core.parts.timer.TimeStamp
import core.parts.value.Value
import core.parts.value.basic.Implicits._
import core.parts.value.CustomLongValue.{GetTime, GetTurn}
import core.parts.value.CustomValueImports._
import core.repository.EntityRepository
import json.{JSONParsable, JValue}

import scala.language.implicitConversions

sealed abstract class Event extends JSONParsable {
    val entityId: Long
    
    def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event])
}

object Event {
    implicit def en2Vector(entity: Entity): Vector[Entity] = Vector[Entity](entity)
    
    implicit def ev2Vector(event: Event): Vector[Event] = Vector(event)
    
    private def getTimeStamp(implicit entityRepository: EntityRepository): TimeStamp = {
        TimeStamp(GetTime(0).getOrElse(0))
    }
    
    private def getTurn(implicit entityRepository: EntityRepository): Long = {
        GetTurn(1).getOrElse(0)
    }
    
    // entity amount
    final case class Create(override val entityId: Long) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = ???
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    final case class Delete(override val entityId: Long) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
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
    final case class MoveTo(override val entityId: Long, x: Int, y: Int) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: PositionHolder =>
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
    
    final case class MoveBy(override val entityId: Long, dx: Int, dy: Int) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: PositionHolder =>
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
    final case class SwitchOff(override val entityId: Long) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: SwitchingCapable if ent.state == State.On =>
                    (ent.beginSwitchingOff(getTimeStamp), DelayTime(entityId, ent.switchingOffDuration, SwitchOff(entityId)))
                case ent: SwitchingCapable if ent.state == State.SwitchingOff =>
                    (ent.finishSwitchingOff(getTimeStamp), Vector.empty)
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
    
    final case class SwitchOn(override val entityId: Long) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: SwitchingCapable if ent.state == State.Off =>
                    (ent.beginSwitchingOn(getTimeStamp), DelayTime(entityId, ent.switchingOnDuration, SwitchOn(entityId)))
                case ent: SwitchingCapable if ent.state == State.SwitchingOn =>
                    (ent.finishSwitchingOn(getTimeStamp), Vector.empty)
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
    final case class Open(override val entityId: Long) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: ClosingCapable if ent.state == State.Close =>
                    (ent.beginOpening(getTimeStamp), DelayTime(entityId, ent.openingDuration, Open(entityId)))
                case ent: ClosingCapable if ent.state == State.Opening =>
                    (ent.finishOpening(getTimeStamp), Vector.empty)
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
    
    final case class Close(override val entityId: Long) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: ClosingCapable if ent.state == State.Open =>
                    (ent.beginClosing(getTimeStamp), DelayTime(entityId, ent.closingDuration, Close(entityId)))
                case ent: ClosingCapable if ent.state == State.Closing =>
                    (ent.finishClosing(getTimeStamp), Vector.empty)
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
    
    final case class Unlock(override val entityId: Long, keys: Set[Long]) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: LockingCapable if ent.state == State.Locked =>
                    (ent.beginUnlocking(getTimeStamp), DelayTime(entityId, ent.unlockingDuration, Unlock(entityId, keys)))
                case ent: LockingCapable if ent.state == State.Closing =>
                    (ent.finishUnlocking(getTimeStamp), Vector.empty)
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
    
    final case class Lock(override val entityId: Long, keys: Set[Long]) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: LockingCapable if ent.state == State.Close =>
                    (ent.beginLocking(getTimeStamp), DelayTime(entityId, ent.lockingDuration, Lock(entityId, keys)))
                case ent: LockingCapable if ent.state == State.Locking =>
                    (ent.finishLocking(getTimeStamp), Vector.empty)
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
    final case class SetValue(override val entityId: Long, name: String, value: Value) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case en: ValueHolder =>
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
    
    final case class RemoveValue(override val entityId: Long, name: String) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case en: ValueHolder =>
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
    
    final case class SetCalculatedValue(override val entityId: Long, name: String, value: Value) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case en: ValueHolder =>
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
    final case class RunScript(override val entityId: Long, scriptName: String) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case ent: ScriptHolder =>
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
    
    final case class ExecuteScriptLine(override val entityId: Long, script: Script, lineNo: Int) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
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
        override val entityId: Long = 0
        
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case en: TimeCounterHolder if !en.isTimerRunning =>
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
        override val entityId: Long = 0
        
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case en: TimeCounterHolder if en.isTimerRunning =>
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
    
    final case class DelayTime(override val entityId: Long, delay: Long, events: Vector[Event]) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            val time = getTimeStamp.milliseconds
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
    
    final case class ScheduleTime(override val entityId: Long, timeStamp: Long, events: Vector[Event]) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            val time = getTimeStamp.milliseconds
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
        override val entityId: Long = 1
        
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            entity match {
                case en: TurnCounterHolder =>
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
    
    final case class DelayTurns(override val entityId: Long, delay: Long, events: Vector[Event]) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            (entity, ScheduleTurns(entityId, getTurn + delay, events))
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
    
    final case class ScheduleTurns(override val entityId: Long, turnStamp: Long, events: Vector[Event]) extends Event {
        override def applyTo(entity: Entity)(implicit entityRepository: EntityRepository): (Vector[Entity], Vector[Event]) = {
            if (getTurn >= turnStamp)
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
