package core.event

import core.entity.Entity
import core.entity.Entity._
import core.entity.properties.state.State
import core.program.Instruction._
import core.program.Script
import core.timer.Timer
import core.world.WorldFrame
import json.{JSONParsable, JValue}

import scala.language.implicitConversions

sealed abstract class Event extends JSONParsable {
  val entityId: String

  def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event])
}

object Event {
  implicit def ev2Vector(event: Event): Vector[Event] = Vector(event)

  implicit def en2Vector(entity: Entity): Vector[Entity] = Vector(entity)

  final case class Delete(override val entityId: String) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      (Vector.empty, Vector.empty)
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId
      )
    }
  }

  // position
  final case class MoveTo(override val entityId: String, x: Int, y: Int) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Positioned =>
          (ent.moveTo(x, y), Vector.empty)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "x" -> x,
        "y" -> y
      )
    }
  }

  final case class MoveBy(override val entityId: String, dx: Int, dy: Int) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Positioned =>
          (ent.moveBy(dx, dy), Vector.empty)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "dx" -> dx,
        "dy" -> dy
      )
    }
  }

  // state setter
  final case class SetState(override val entityId: String, state: State) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      implicit val clock: Timer = mapFrame.clock
      (entity, state) match {
        case (ent: Switchable, st: State.SwitchableState) =>
          (ent.setSwitchableState(st), Vector.empty)
        case (ent: Openable, st: State.OpenableState) =>
          (ent.setOpenableState(st), Vector.empty)
        case (ent: Character, st: State.CharacterState) =>
          (ent.setCharacterState(st), Vector.empty)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "state" -> state
      )
    }
  }

  // switchable
  final case class SwitchOff(override val entityId: String) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Switchable if ent.state == State.On =>
          val delay = 1000
          val events = Vector(
            SetState(entityId, State.SwitchingOff),
            DelayTime(entityId, delay, SetState(entityId, State.Off))
          )
          (entity, events)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId
      )
    }
  }

  final case class SwitchOn(override val entityId: String) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Switchable if ent.state == State.Off =>
          val delay = 1000
          val events = Vector(
            SetState(entityId, State.SwitchingOn),
            DelayTime(entityId, delay, SetState(entityId, State.On))
          )
          (entity, events)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId
      )
    }
  }

  // openable
  final case class Open(override val entityId: String) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Openable if ent.state == State.Close =>
          val delay = 1000
          val events = Vector(
            SetState(entityId, State.Opening),
            DelayTime(entityId, delay, SetState(entityId, State.Open))
          )
          (entity, events)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId
      )
    }
  }

  final case class Close(override val entityId: String) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Openable if ent.state == State.Open =>
          val delay = 1000
          val events = Vector(
            SetState(entityId, State.Closing),
            DelayTime(entityId, delay, SetState(entityId, State.Close))
          )
          (entity, events)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId
      )
    }
  }

  final case class Unlock(override val entityId: String, keys: Set[Long]) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Openable if ent.state == State.Locked && keys.contains(ent.lockCode) =>
          val delay = 1000
          val events = Vector(
            SetState(entityId, State.Unlocking),
            DelayTime(entityId, delay, SetState(entityId, State.Close))
          )
          (entity, events)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "keys" -> keys.toSeq
      )
    }
  }

  final case class Lock(override val entityId: String, keys: Set[Long]) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Openable if ent.state == State.Close && keys.contains(ent.lockCode) =>
          val delay = 1000
          val events = Vector(
            SetState(entityId, State.Locking),
            DelayTime(entityId, delay, SetState(entityId, State.Locked))
          )
          (entity, events)
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "keys" -> keys.toSeq
      )
    }
  }

  // script
  final case class RunScript(override val entityId: String, scriptName: String) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      entity match {
        case ent: Scripted =>
          (entity, ExecuteScript(entityId, ent.getScript(scriptName), 0))
        case _ =>
          (entity, Vector.empty)
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "scriptName" -> scriptName
      )
    }
  }

  final case class ExecuteScript(override val entityId: String, script: Script, lineNo: Int) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      script.getInstruction(lineNo) match {
        case EX =>
          (entity, Vector.empty)
        case DO(events) =>
          (entity, ExecuteScript(entityId, script, lineNo + 1) ++ events)
        case LB(_) =>
          (entity, ExecuteScript(entityId, script, lineNo + 1))
        case GT(labelId) =>
          script.labelMap.get(labelId) match {
            case Some(labelLineNo) => (entity, ExecuteScript(entityId, script, labelLineNo + 1))
            case None => (entity, ExecuteScript(entityId, script, lineNo + 1))
          }
        case IF(condition) =>
          condition.getValue match {
            case Some(true) => (entity, ExecuteScript(entityId, script, lineNo + 2))
            case Some(false) => (entity, ExecuteScript(entityId, script, lineNo + 1))
            case None => (entity, Vector.empty)
          }
      }
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "program" -> null
      )
    }
  }

  // time
  final case class DelayTime(override val entityId: String, delay: Long, events: Vector[Event]) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      (entity, ScheduleTime(entityId, mapFrame.clock.getTime + delay, events))
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "delay" -> delay,
        "events" -> events
      )
    }
  }

  final case class ScheduleTime(override val entityId: String, timeStamp: Long, events: Vector[Event]) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      if (mapFrame.clock.getTime >= timeStamp)
        (entity, events)
      else
        (entity, this)
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "timeStamp" -> timeStamp,
        "events" -> events
      )
    }
  }

  // turn
  final case class DelayTurns(override val entityId: String, delay: Long, events: Vector[Event]) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      (entity, ScheduleTurns(entityId, mapFrame.turn + delay, events))
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "delay" -> delay,
        "events" -> events
      )
    }
  }

  final case class ScheduleTurns(override val entityId: String, turnStamp: Long, events: Vector[Event]) extends Event {
    override def applyTo(entity: Entity)(implicit mapFrame: WorldFrame): (Vector[Entity], Vector[Event]) = {
      if (mapFrame.turn >= turnStamp)
        (entity, events)
      else
        (entity, this)
    }

    override def toJSON: JValue = {
      import json.MyJ._
      jObject(
        "event" -> this.getClass.getSimpleName,
        "entityId" -> entityId,
        "turnStamp" -> turnStamp,
        "events" -> events
      )
    }
  }

}
