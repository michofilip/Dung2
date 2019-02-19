package core.entity

import core.entity.properties.graphics.{Animation, Frame}
import core.entity.properties.physics.Physics
import core.entity.properties.position.{Coordinates, Position}
import core.entity.properties.state.State
import core.entity.properties.state.State._
import core.entity.selectors.{AnimationSelector, PhysicsSelector}
import core.program.Script
import core.timer.Timer
import core.value.Value
import json.{JSONParsable, JValue, MyJ}

abstract class Entity extends JSONParsable {
    protected type T <: Entity
    val id: String
    val timeStamp: Long
}

object Entity {
    
    // traits
//    sealed trait StateHolder extends Entity {
//        val state: State
//
//        protected def setState(state: State, timeStamp: Long): T
//    }
    
//     trait PositionHolder extends Entity {
//        val position: Position
//
//        protected def setPosition(position: Position): T
//
//        def moveTo(x: Int = position.coordinates.x, y: Int = position.coordinates.y): T = {
//            setPosition(Position(Coordinates(x, y), position.direction))
//        }
//
//        def moveBy(dx: Int = 0, dy: Int = 0): T = {
//            moveTo(position.coordinates.x + dx, position.coordinates.y + dy)
//        }
//
//        def turnClockwise90: T = {
//            setPosition(Position(position.coordinates, position.direction.turnClockwise90))
//        }
//
//        def turnCounterClockwise90: T = {
//            setPosition(Position(position.coordinates, position.direction.turnCounterClockwise90))
//
//        }
//
//        def turn180: T = {
//            setPosition(Position(position.coordinates, position.direction.turn180))
//        }
//    }
    
//     trait PhysicsHolder extends Entity {
//        protected val physicsSelector: PhysicsSelector
//
//        def physicsSelectorId: String = {
//            physicsSelector.id
//        }
//
//        def physics: Physics = {
//            val stateOpt = this match {
//                case en: StateHolder => Some(en.state)
//                case _ => None
//            }
//            physicsSelector.getPhysics(stateOpt)
//        }
//    }
    
//     trait AnimationHolder extends Entity {
//        protected val animationSelector: AnimationSelector
//
//        def animationSelectorId: String = {
//            animationSelector.id
//        }
//
//        private def animation: Animation = {
//            val stateOpt = this match {
//                case en: StateHolder => Some(en.state)
//                case _ => None
//            }
//            val directionOpt = this match {
//                case en: PositionHolder => Some(en.position.direction)
//                case _ => None
//            }
//            animationSelector.getAnimation(stateOpt, directionOpt)
//        }
//
//        def getFrame(implicit timer: Timer): Frame = {
//            animation.getFrame(timer.getTime - timeStamp)
//        }
//    }
    
//     trait ScriptHolder extends Entity {
//        protected val scripts: Map[String, Script]
//
//        def getScript(name: String): Script = scripts.getOrElse(name, Script.emptyScript)
//    }
    
//     trait TimeHolder extends Entity {
//        protected val timer: Timer
//
//        def getTime: Long = {
//            timer.getTime
//        }
//
//        def isRunning: Boolean = {
//            timer.isRunning
//        }
//
//        def start(): T
//
//        def stop(): T
//    }
    
//     trait TurnHolder extends Entity {
//        val turn: Long
//
//        def nextTurn: T
//    }
    
//     trait ValueHolder extends Entity {
//        val value: Value
//
//        def setValue(value: Value): T
//    }
    
    //todo experimental
    //    sealed trait InventoryHolder extends Entity {
    //        //        val inventory
    //    }
    
    // abstract classes
    abstract class MapEntity extends Entity with PositionHolder with PhysicsHolder with AnimationHolder
    
    abstract class Switchable extends MapEntity with StateHolder {
        def setSwitchableState(switchableState: SwitchableState, timeStamp: Long): T = {
            (state, switchableState) match {
                case (Off, SwitchingOn) => setState(switchableState, timeStamp)
                case (SwitchingOn, On) => setState(switchableState, timeStamp)
                case (On, SwitchingOff) => setState(switchableState, timeStamp)
                case (SwitchingOff, Off) => setState(switchableState, timeStamp)
                
                case _ => setState(state, this.timeStamp)
            }
        }
    }
    
    abstract class Openable extends MapEntity with StateHolder {
        val lockCode: Long
        
        def setOpenableState(openableState: OpenableState, timeStamp: Long): T = {
            (state, openableState) match {
                case (Open, Closing) => setState(openableState, timeStamp)
                case (Closing, Close) => setState(openableState, timeStamp)
                case (Close, Opening) => setState(openableState, timeStamp)
                case (Opening, Open) => setState(openableState, timeStamp)
                
                case (Close, Locking) => setState(openableState, timeStamp)
                case (Locking, Locked) => setState(openableState, timeStamp)
                case (Locked, Unlocking) => setState(openableState, timeStamp)
                case (Unlocking, Close) => setState(openableState, timeStamp)
                
                case _ => setState(state, this.timeStamp)
            }
        }
    }
    
    abstract class Character extends MapEntity with StateHolder {
        // TODO basic template
        def setCharacterState(characterState: CharacterState, timeStamp: Long): T =
            if (characterState != state)
                setState(characterState, timeStamp)
            else
                setState(state, timeStamp)
    }
    
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
                       _animationSelector: AnimationSelector
                      ) extends MapEntity {
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
    
    final class Switch(override val id: String,
                       override val timeStamp: Long,
                       override val state: State,
                       override val position: Position,
                       override protected val physicsSelector: PhysicsSelector,
                       override protected val animationSelector: AnimationSelector)
            extends Switchable with PositionHolder with PhysicsHolder with AnimationHolder {
        
        override protected type T = Switch
        
        override protected def setState(state: State, timeStamp: Long): T =
            update(state = state, timeStamp = timeStamp)
        
        override def setPosition(position: Position): T =
            update(position = position)
        
        private def update(timeStamp: Long = timeStamp, state: State = state, position: Position = position): T =
            new Switch(id, timeStamp, state, position, physicsSelector, animationSelector)
        
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
            extends Openable with PositionHolder with PhysicsHolder with AnimationHolder {
        
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
            extends Character with PositionHolder with PhysicsHolder with AnimationHolder {
        
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
