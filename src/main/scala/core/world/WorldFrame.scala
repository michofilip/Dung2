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
    private def update(entityHolder: EntityRepository = entityRepository, events: Vector[Event] = events): WorldFrame = {
        new WorldFrame(entityHolder, events)
    }
    
    def nextFrame(externalEvents: Vector[Event] = Vector.empty): WorldFrame = {
        val (newEntityRepository, newEvents) =
            events.foldLeft(entityRepository, externalEvents) {
                case ((tempEntityRepository, tempEvents), event) =>
                    implicit val eh: EntityRepository = tempEntityRepository
                    tempEntityRepository.getById(event.entityId) match {
                        case Some(entity: Entity) =>
                            val (resultEntities, resultEvents) = event.applyTo(entity)
                            
                            val newTempEntityRepository = tempEntityRepository - entity ++ resultEntities
                            val newTempEvents = tempEvents ++ resultEvents
                            
                            (newTempEntityRepository, newTempEvents)
                        
                        case _ =>
                            (tempEntityRepository, tempEvents)
                    }
            }
        
        update(entityHolder = newEntityRepository, events = newEvents)
    }
    
    override def toJSON: JValue = {
        ListMap(
            "entities" -> entityRepository.getAll,
            "events" -> events
        ).toJSON
    }
}

object WorldFrame {
    def apply(entities: Vector[Entity], events: Vector[Event]): WorldFrame = {
        new WorldFrame(EntityRepository(entities), events)
    }
    
}