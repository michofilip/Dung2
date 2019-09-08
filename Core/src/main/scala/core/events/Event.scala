package core.events

import core.entities.Entity
import core.entities.traits.properties._
import core.entities.traits.templates.{ClosableTemplate, SwitchTemplate}
import core.parts.position.{Coordinates, Direction}
import core.parts.scripts.Instruction._
import core.parts.scripts.Script
import core.parts.state.State
import core.parts.timer.TimeStamp
import core.parts.value.CustomLongValue.{GetTime, GetTurn}
import core.parts.value.CustomValueImports._
import core.repository.EntityRepository
import value.Value
import value.ValueImports._

import scala.language.implicitConversions

sealed abstract class Event {
    val entityId: Long
    
    def applyTo(entity: Entity): (Vector[Entity], Vector[Event])
}

object Event {
    implicit def en2Vector(entity: Entity): Vector[Entity] = Vector[Entity](entity)
    
    implicit def ev2Vector(event: Event): Vector[Event] = Vector(event)
    
    private def getTimeStamp(implicit er: EntityRepository): TimeStamp =
        TimeStamp(GetTime(0).getOrElse(0))
    
    private def getTurn(implicit er: EntityRepository): Long =
        GetTurn(1).getOrElse(0)
    
    // entity amount
    final case class Create(override val entityId: Long) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = ???
    }
    
    final case class Delete(override val entityId: Long) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) =
            (Vector.empty, Vector.empty)
    }
    
    // position
    final case class MoveTo(override val entityId: Long, x: Int, y: Int) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: PositionProperty => (ent.moveTo(x, y), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case class MoveBy(override val entityId: Long, dx: Int, dy: Int) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: PositionProperty => (ent.moveBy(dx, dy), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    // switchable
    final case class SwitchOff(override val entityId: Long)(implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: SwitchTemplate if ent.state == State.On =>
                (ent.beginSwitchingOff(getTimeStamp), DelayTime(entityId, ent.switchingOffDuration, SwitchOff(entityId)))
            case ent: SwitchTemplate if ent.state == State.SwitchingOff =>
                (ent.finishSwitchingOff(getTimeStamp), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case class SwitchOn(override val entityId: Long)(implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: SwitchTemplate if ent.state == State.Off =>
                (ent.beginSwitchingOn(getTimeStamp), DelayTime(entityId, ent.switchingOnDuration, SwitchOn(entityId)))
            case ent: SwitchTemplate if ent.state == State.SwitchingOn =>
                (ent.finishSwitchingOn(getTimeStamp), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    // openable
    final case class Open(override val entityId: Long)(implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: ClosableTemplate if ent.state == State.Close =>
                (ent.beginOpening(getTimeStamp), DelayTime(entityId, ent.openingDuration, Open(entityId)))
            case ent: ClosableTemplate if ent.state == State.Opening =>
                (ent.finishOpening(getTimeStamp), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case class Close(override val entityId: Long)(implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: ClosableTemplate if ent.state == State.Open =>
                (ent.beginClosing(getTimeStamp), DelayTime(entityId, ent.closingDuration, Close(entityId)))
            case ent: ClosableTemplate if ent.state == State.Closing =>
                (ent.finishClosing(getTimeStamp), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case class Unlock(override val entityId: Long, keys: Set[Long])(implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: ClosableTemplate if ent.state == State.Locked =>
                (ent.beginUnlocking(getTimeStamp), DelayTime(entityId, ent.unlockingDuration, Unlock(entityId, keys)))
            case ent: ClosableTemplate if ent.state == State.Closing =>
                (ent.finishUnlocking(getTimeStamp), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case class Lock(override val entityId: Long, keys: Set[Long])
                         (implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: ClosableTemplate if ent.state == State.Close =>
                (ent.beginLocking(getTimeStamp), DelayTime(entityId, ent.lockingDuration, Lock(entityId, keys)))
            case ent: ClosableTemplate if ent.state == State.Locking =>
                (ent.finishLocking(getTimeStamp), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    // value
    final case class SetValue(override val entityId: Long, name: String, value: Value) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case en: ValueProperty => (en.setValue(name, value), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case class RemoveValue(override val entityId: Long, name: String) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case en: ValueProperty => (en.removeValue(name), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    //TODO not needed anymore
    
//    final case class SetCalculatedValue(override val entityId: Long, name: String, value: Value) extends Event {
//        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
//            case en: ValueProperty => value.get match {
//                case Some(v: Boolean) => (en.setValue(name, v), Vector.empty)
//                case Some(v: Byte) => (en.setValue(name, v), Vector.empty)
//                case Some(v: Short) => (en.setValue(name, v), Vector.empty)
//                case Some(v: Int) => (en.setValue(name, v), Vector.empty)
//                case Some(v: Long) => (en.setValue(name, v), Vector.empty)
//                case Some(v: Float) => (en.setValue(name, v), Vector.empty)
//                case Some(v: Double) => (en.setValue(name, v), Vector.empty)
//                case Some(v: Char) => (en.setValue(name, v), Vector.empty)
//                case Some(v: String) => (en.setValue(name, v), Vector.empty)
//
//                case Some(v: Coordinates) => (en.setValue(name, v), Vector.empty)
//                case Some(v: Direction) => (en.setValue(name, v), Vector.empty)
//                case Some(v: State) => (en.setValue(name, v), Vector.empty)
//
//                case _ => (en.removeValue(name), Vector.empty)
//            }
//            case _ => (entity, Vector.empty)
//        }
//    }
    
    // script
    final case class RunScript(override val entityId: Long, scriptName: String) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case ent: ScriptProperty => (entity, ExecuteScriptLine(entityId, ent.getScript(scriptName), 0))
            case _ => (entity, Vector.empty)
        }
    }
    
    // TODO redo
    final case class ExecuteScriptLine(override val entityId: Long, script: Script, lineNo: Int) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = script.getInstruction(lineNo) match {
            case EXIT(_) => (entity, Vector.empty)
            case EXECUTE(events) => (entity, events ++ ExecuteScriptLine(entityId, script, lineNo + 1))
            case LABEL(_) => (entity, ExecuteScriptLine(entityId, script, lineNo + 1))
            case GOTO(labelId) => script.getLineNo(labelId) match {
                case Some(labelLineNo) => (entity, ExecuteScriptLine(entityId, script, labelLineNo + 1))
                case None => (entity, ExecuteScriptLine(entityId, script, lineNo + 1))
            }
            case TEST(condition) => condition.get match {
                case Some(true) => (entity, ExecuteScriptLine(entityId, script, lineNo + 2))
                case Some(false) => (entity, ExecuteScriptLine(entityId, script, lineNo + 1))
                case None => (entity, Vector.empty)
            }
        }
    }
    
    // time
    final case object StartTime extends Event {
        override val entityId: Long = 0
        
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case en: TimeCounterProperty if !en.isTimerRunning => (en.startTimer(), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case object StopTime extends Event {
        override val entityId: Long = 0
        
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case en: TimeCounterProperty if en.isTimerRunning => (en.stopTimer(), Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case class DelayTime(override val entityId: Long, delay: Long, events: Vector[Event])
                              (implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) =
            (entity, ScheduleTime(entityId, getTimeStamp.milliseconds + delay, events))
    }
    
    final case class ScheduleTime(override val entityId: Long, timeStamp: Long, events: Vector[Event])
                                 (implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) =
            if (getTimeStamp.milliseconds >= timeStamp) (entity, events)
            else (entity, this)
    }
    
    // turn
    final case object NextTurn extends Event {
        override val entityId: Long = 1
        
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) = entity match {
            case en: TurnCounterProperty => (en.nextTurn, Vector.empty)
            case _ => (entity, Vector.empty)
        }
    }
    
    final case class DelayTurns(override val entityId: Long, delay: Long, events: Vector[Event])
                               (implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) =
            (entity, ScheduleTurns(entityId, getTurn + delay, events))
    }
    
    final case class ScheduleTurns(override val entityId: Long, turnStamp: Long, events: Vector[Event])
                                  (implicit er: EntityRepository) extends Event {
        override def applyTo(entity: Entity): (Vector[Entity], Vector[Event]) =
            if (getTurn >= turnStamp) (entity, events)
            else (entity, this)
    }
    
}
