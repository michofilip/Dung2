package core.entity

import core.entity.properties.graphics.{Animation, Frame}
import core.entity.properties.physics.Physics
import core.entity.properties.position.{Coordinates, Position}
import core.entity.properties.state.State
import core.entity.properties.state.State._
import core.entity.selectors.{AnimationSelector, PhysicsSelector}
import core.program.Script
import core.timer.Timer
import json.{JSONParsable, JValue}

sealed abstract class Entity extends JSONParsable {
    protected type T <: Entity
    val id: String
    val timeStamp: Long
}

object Entity {
    
    // traits
    sealed trait MultiState extends Entity {
        val state: State
        
        protected def setState(state: State, timeStamp: Long): T
    }
    
    sealed trait Positioned extends Entity {
        def position: Position
        
        protected def setPosition(position: Position): T
        
        def moveTo(x: Int = position.coordinates.x, y: Int = position.coordinates.y): T = {
            setPosition(Position(Coordinates(x, y), position.direction))
        }
        
        def moveBy(dx: Int = 0, dy: Int = 0): T = {
            moveTo(position.coordinates.x + dx, position.coordinates.y + dy)
        }
        
        def turnClockwise90: T = {
            setPosition(Position(position.coordinates, position.direction.turnClockwise90))
        }
        
        def turnCounterClockwise90: T = {
            setPosition(Position(position.coordinates, position.direction.turnCounterClockwise90))
            
        }
        
        def turn180: T = {
            setPosition(Position(position.coordinates, position.direction.turn180))
        }
    }
    
    sealed trait Physical extends Entity {
        protected val physicsSelector: PhysicsSelector
        
        def physicsSelectorId: String = {
            physicsSelector.id
        }
        
        def physics: Physics = {
            val stateOpt = this match {
                case en: MultiState => Some(en.state)
                case _ => None
            }
            physicsSelector.getPhysics(stateOpt)
        }
    }
    
    sealed trait Animated extends Entity {
        protected val animationSelector: AnimationSelector
        
        def animationSelectorId: String = {
            animationSelector.id
        }
        
        private def animation: Animation = {
            val stateOpt = this match {
                case en: MultiState => Some(en.state)
                case _ => None
            }
            val directionOpt = this match {
                case en: Positioned => Some(en.position.direction)
                case _ => None
            }
            animationSelector.getAnimation(stateOpt, directionOpt)
        }
        
        def getFrame(implicit timer: Timer): Frame = {
            animation.getFrame(timer.getTime - timeStamp)
        }
    }
    
    sealed trait Scripted extends Entity {
        protected val scripts: Map[String, Script]
        
        def getScript(name: String): Script = scripts.getOrElse(name, Script.emptyScript)
    }
    
    //todo experimental
    //    sealed trait InventoryHolder extends Entity {
    //        //        val inventory
    //    }
    
    // abstract classes
    sealed abstract class Switchable extends Entity with MultiState {
        //        def starClosing():T
        
        
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
    
    sealed abstract class Openable extends Entity with MultiState {
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
    
    sealed abstract class Character extends Entity with MultiState {
        // TODO basic template
        def setCharacterState(characterState: CharacterState, timeStamp: Long): T =
            if (characterState != state)
                setState(characterState, timeStamp)
            else
                setState(state, timeStamp)
    }
    
    // final classes
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
    
    final class TimeCounter(val timer: Timer) extends Entity {
        override protected type T = TimeCounter
        override val id: String = "TimeCounter"
        override val timeStamp: Long = timer.getTime
        
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
    
    final class TurnCounter(val turn: Long) extends Entity {
        override protected type T = TurnCounter
        override val id: String = "TurnCounter"
        override val timeStamp: Long = 0
        
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
    
    final class Static(override val id: String,
                       override val timeStamp: Long,
                       override val position: Position,
                       override protected val physicsSelector: PhysicsSelector,
                       override protected val animationSelector: AnimationSelector)
            extends Entity with Positioned with Physical with Animated {
        
        override protected type T = Static
        
        override def setPosition(position: Position): Static =
            update(position = position)
        
        private def update(position: Position = position): T =
            new Static(id, timeStamp, position, physicsSelector, animationSelector)
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "entity" -> "Static",
                "id" -> id,
                "timeStamp" -> timeStamp,
                "position" -> position.toJSON,
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
            extends Switchable with Positioned with Physical with Animated {
        
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
            extends Openable with Positioned with Physical with Animated {
        
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
            extends Character with Positioned with Physical with Animated {
        
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
