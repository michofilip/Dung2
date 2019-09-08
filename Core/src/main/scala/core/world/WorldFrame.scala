package core.world

import core.entities.Entity
import core.events.Event
import core.repository.EntityRepository
import json.MyJ.ToJSON
import json.{JSONParsable, JValue}

import scala.collection.immutable.ListMap

class WorldFrame(private val entityRepository: EntityRepository,
                 private val events: Vector[Event]
                ) extends JSONParsable {
    private def update(entityRepository: EntityRepository = entityRepository, events: Vector[Event] = events): WorldFrame = {
        new WorldFrame(entityRepository, events)
    }
    
    def nextFrame(externalEvents: Vector[Event] = Vector.empty): WorldFrame = {
        val (newEntityRepository, newEvents) = events.foldLeft(entityRepository, externalEvents) {
            case ((entityRepository, events), event) => entityRepository.getById(event.entityId) match {
                case Some(entity: Entity) =>
                    val (resultEntities, resultEvents) = event.applyTo(entity)
                    
                    val newTempEntityRepository = entityRepository - entity ++ resultEntities
                    val newTempEvents = events ++ resultEvents
                    
                    (newTempEntityRepository, newTempEvents)
                
                case _ =>
                    (entityRepository, events)
            }
        }
        
        update(entityRepository = newEntityRepository, events = newEvents)
    }
    
    override def toJSON: JValue = {
        ListMap(
            "entities" -> entityRepository.getAll,
            "events" -> events
        ).toJSON
    }
}