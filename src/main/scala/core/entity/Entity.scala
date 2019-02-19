package core.entity

import core.entity.properties.position.Position
import core.entity.properties.state.State
import core.entity.selectors.{AnimationSelector, PhysicsSelector}
import core.entity.traits._
import core.timer.Timer
import core.value.Value
import json.{JSONParsable, JValue, MyJ}

abstract class Entity extends JSONParsable {
    protected type T <: Entity
    val id: String
    val timeStamp: Long
}

object Entity {
    
    // Control classes
    final class EntityCreator(override val timeStamp: Long) extends Entity {
        override protected type T = EntityCreator
        override val id: String = "EntityCreator"
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> this.getClass.getSimpleName,
                "id" -> id,
                "timeStamp" -> timeStamp
            )
        }
    }
    
    final class ScriptRunner(override val timeStamp: Long) extends Entity {
        override protected type T = ScriptRunner
        override val id: String = "ScriptRunner"
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> this.getClass.getSimpleName,
                "id" -> id,
                "timeStamp" -> timeStamp
            )
        }
    }
    
    final class TimeCounter(val timer: Timer) extends Entity with TimeHolder {
        override protected type T = TimeCounter
        override val id: String = "TimeCounter"
        override val timeStamp: Long = timer.getTime
        
        private def update(timer: Timer = timer): T =
            new TimeCounter(timer)
        
        override def start(): TimeCounter = {
            update(timer.start)
        }
        
        override def stop(): TimeCounter = {
            update(timer.stop)
        }
        
        override def isRunning: Boolean = {
            timer.isRunning
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> "TimeCounter",
                "id" -> id,
                "timeStamp" -> timeStamp,
                "timer" -> timer.getTime
            )
        }
    }
    
    final class TurnCounter(val turn: Long) extends Entity with TurnHolder {
        override protected type T = TurnCounter
        override val id: String = "TurnCounter"
        override val timeStamp: Long = 0
        
        private def update(turn: Long = turn): T =
            new TurnCounter(turn)
        
        def nextTurn: TurnCounter = {
            update(turn + 1)
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> "TimeCounter",
                "id" -> id,
                "timeStamp" -> timeStamp,
                "turn" -> turn
            )
        }
    }
    
    final class ValueContainer(override val id: String,
                               override val timeStamp: Long,
                               override val value: Value
                              ) extends Entity with ValueHolder {
        override protected type T = ValueContainer
        
        private def update(value: Value = value): ValueContainer = new ValueContainer(id, timeStamp, value)
        
        override def setValue(value: Value): ValueContainer = update(value)
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> "ValueContainer",
                "id" -> id,
                "timeStamp" -> timeStamp,
                "value" -> value
            )
        }
    }
    
    // Physical classes
    //    final class Static(override val id: String,
    //                       override val timeStamp: Long,
    //                       override val position: Position,
    //                       override protected val physicsSelector: PhysicsSelector,
    //                       override protected val animationSelector: AnimationSelector)
    //            extends Entity with PositionHolder with PhysicsHolder with AnimationHolder {
    //
    //        override protected type T = Static
    //
    //        override def setPosition(position: Position): Static =
    //            update(position = position)
    //
    //        private def update(position: Position = position): T =
    //            new Static(id, timeStamp, position, physicsSelector, animationSelector)
    //
    //        override def toJSON: JValue = {
    //            import json.MyJ._
    //            jObject(
    //                "entity" -> "Static",
    //                "id" -> id,
    //                "timeStamp" -> timeStamp,
    //                "position" -> position.toJSON,
    //                "physicsSelector" -> physicsSelector.id,
    //                "animationSelector" -> animationSelector.id
    //            )
    //        }
    //    }
    
    final class Static(_id: String,
                       _timeStamp: Long,
                       _position: Position,
                       _physicsSelector: PhysicsSelector,
                       _animationSelector: AnimationSelector)
            extends MapEntity {
        override protected type T = Static
        override val id: String = _id
        override val timeStamp: Long = _timeStamp
        override val position: Position = _position
        override protected val physicsSelector: PhysicsSelector = _physicsSelector
        override protected val animationSelector: AnimationSelector = _animationSelector
        
        private def update(position: Position = position): Static = {
            new Static(id, timeStamp, position, physicsSelector, animationSelector)
        }
        
        override protected def setPosition(position: Position): Static = {
            update(position = position)
        }
        
        override def toJSON: JValue = {
            MyJ.jObject(
                "entity" -> "Static",
                "id" -> id,
                "timeStamp" -> timeStamp,
                "position" -> position,
                "physicsSelector" -> physicsSelector.id,
                "animationSelector" -> animationSelector.id
            )
        }
    }
    
    final class Switch(_id: String,
                       _timeStamp: Long,
                       _state: State,
                       _position: Position,
                       _physicsSelector: PhysicsSelector,
                       _animationSelector: AnimationSelector)
            extends Switchable {
        override protected type T = Switch
        override val id: String = _id
        override val timeStamp: Long = _timeStamp
        override val state: State = _state
        override val position: Position = _position
        override protected val physicsSelector: PhysicsSelector = _physicsSelector
        override protected val animationSelector: AnimationSelector = _animationSelector
        
        private def update(timeStamp: Long = timeStamp, state: State = state, position: Position = position): T = {
            new Switch(id, timeStamp, state, position, physicsSelector, animationSelector)
        }
        
        override protected def setState(state: State, timeStamp: Long): T = {
            update(state = state, timeStamp = timeStamp)
        }
        
        override def setPosition(position: Position): T = {
            update(position = position)
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> "Switch",
                "id" -> id,
                "timeStamp" -> timeStamp,
                "state" -> state.toString,
                "position" -> position,
                "physicsSelector" -> physicsSelector.id,
                "animationSelector" -> animationSelector.id
            )
        }
    }
    
    final class Door(override val id: String,
                     override val timeStamp: Long,
                     override val state: State,
                     override val position: Position,
                     override val lockCode: Long,
                     override protected val physicsSelector: PhysicsSelector,
                     override protected val animationSelector: AnimationSelector)
            extends Openable {
        
        override protected type T = Door
        
        override protected def setState(state: State, timeStamp: Long): T =
            update(state = state, timeStamp = timeStamp)
        
        override def setPosition(position: Position): T =
            update(position = position)
        
        private def update(timeStamp: Long = timeStamp, state: State = state, position: Position = position): T =
            new Door(id, timeStamp, state, position, lockCode, physicsSelector, animationSelector)
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> "Door",
                "id" -> id,
                "timeStamp" -> timeStamp,
                "state" -> state.toString,
                "position" -> position,
                "lockCode" -> lockCode,
                "physicsSelector" -> physicsSelector.id,
                "animationSelector" -> animationSelector.id
            )
        }
    }
    
    final class Player(override val id: String, override val timeStamp: Long,
                       override val state: State,
                       override val position: Position,
                       override protected val physicsSelector: PhysicsSelector,
                       override protected val animationSelector: AnimationSelector)
            extends Character {
        
        override protected type T = Player
        
        override protected def setState(state: State, timeStamp: Long): T =
            update(state = state, timeStamp = timeStamp)
        
        override def setPosition(position: Position): T =
            update(position = position)
        
        private def update(timeStamp: Long = timeStamp, state: State = state, position: Position = position): T =
            new Player(id, timeStamp, state, position, physicsSelector, animationSelector)
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> "Player",
                "id" -> id,
                "timeStamp" -> timeStamp,
                "state" -> state.toString,
                "position" -> position,
                "physicsSelector" -> physicsSelector.id,
                "animationSelector" -> animationSelector.id
            )
        }
    }
    
    //todo container (extends Openable)
    //todo npc, possibly merge with player
}
