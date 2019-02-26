package core.world

import core.entity.Entity
import core.entity.repositoy.EntityRepository
import core.event.Event
import json.MyJ.ToJSON
import json.{JSONParsable, JValue}

import scala.collection.immutable.ListMap

class WorldFrame(private val entityHolder: EntityRepository,
                 private val events: Vector[Event]
                ) extends JSONParsable {
    private def update(entityHolder: EntityRepository = entityHolder, events: Vector[Event] = events): WorldFrame = {
        new WorldFrame(entityHolder, events)
    }
    
    def nextFrame(externalEvents: Vector[Event] = Vector.empty): WorldFrame = {
        val (newEntityHolder, newEvents) =
            events.foldLeft(entityHolder, externalEvents) {
                case ((tempEntityHolder, tempEvents), event) =>
                    implicit val eh: EntityRepository = tempEntityHolder
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
    
    override def toJSON: JValue = {
        ListMap(
            "entities" -> entityHolder.getAll,
            "events" -> events
        ).toJSON
    }
}

object WorldFrame {
    def apply(entities: Vector[Entity], events: Vector[Event]): WorldFrame = {
        new WorldFrame(EntityRepository(entities), events)
    }
    
}