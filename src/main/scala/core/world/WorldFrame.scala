package core.world

import core.entity.Entity.Physical
import core.entity.properties.position.Coordinates
import core.entity.{Entity, EntityHolder}
import core.event.Event
import core.timer.Timer
import json.{JSONParsable, JValue, MyJ}

class WorldFrame(private val entityHolder: EntityHolder,
                 private val events: Vector[Event],
                 val clock: Timer, val turn: Long
                ) extends JSONParsable {
    
    def getEntityById(id: String): Option[Entity] = {
        entityHolder.getById(id)
    }
    
    def getEntitiesByCoordinates(coordinates: Coordinates): Vector[Entity] = {
        entityHolder.getByCoordinates(coordinates)
    }
    
    def isSolidAtCoordinates(coordinates: Coordinates): Boolean = {
        val condition: Entity => Boolean = {
            case en: Physical if en.physics.solid => true
            case _ => false
        }
        entityHolder.existsAtCoordinates(coordinates, condition)
    }
    
    def isOpaqueAtCoordinates(coordinates: Coordinates): Boolean = {
        val condition: Entity => Boolean = {
            case en: Physical if en.physics.opaque => true
            case _ => false
        }
        entityHolder.existsAtCoordinates(coordinates, condition)
    }
    
    def nextFrame(externalEvents: Vector[Event] = Vector.empty): WorldFrame = {
        val (newEntityHolder, newEvents) =
            events.foldLeft(entityHolder, externalEvents) {
                case ((tempEntityHolder, tempEvents), event) =>
                    implicit val eh: EntityHolder = tempEntityHolder
                    tempEntityHolder.getById(event.entityId) match {
                        case Some(entity) =>
                            val (resultEntities, resultEvents) = event.applyTo(entity)
                            
                            val newTempEntityHolder = tempEntityHolder - entity ++ resultEntities
                            val newTempEvents = tempEvents ++ resultEvents
                            
                            (newTempEntityHolder, newTempEvents)
                        
                        case None =>
                            (tempEntityHolder, tempEvents)
                    }
            }
        
        update(entityHolder = newEntityHolder, events = newEvents)
    }
    
    def nextTurn(): WorldFrame = update(turn = turn + 1)
    
    private def update(entityHolder: EntityHolder = entityHolder, events: Vector[Event] = events, turn: Long = turn): WorldFrame = {
        new WorldFrame(entityHolder, events, clock, turn)
    }
    
    override def toJSON: JValue = {
        MyJ.jObject(
            "time" -> clock.getTime,
            "turn" -> turn,
            "entities" -> entityHolder.getAll,
            "events" -> events
        )
    }
}