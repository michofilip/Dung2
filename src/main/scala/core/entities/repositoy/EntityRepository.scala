package core.entities.repositoy

import core.entities.Entity
import core.entities.properties.PositionHolder
import core.parts.position.Coordinates

class EntityRepository private(private val entitiesById: Map[String, Entity[_]],
                               private val entitiesByCoordinates: Map[Coordinates, Map[String, Entity[_]]]) {
    
    def add(entity: Entity[_]): EntityRepository = {
        val newEntitiesById = entitiesById + (entity.id -> entity)
        val newEntitiesByCoordinates = entity match {
            case en: PositionHolder[_] =>
                val coordinates = en.position.coordinates
                val newEntitiesAtCoordinates = entitiesByCoordinates.getOrElse(coordinates, Map.empty) + (en.id -> en)
                entitiesByCoordinates + (coordinates -> newEntitiesAtCoordinates)
            case _ =>
                entitiesByCoordinates
        }
        new EntityRepository(newEntitiesById, newEntitiesByCoordinates)
    }
    
    def +(entity: Entity[_]): EntityRepository = {
        add(entity)
    }
    
    def addAll(entities: Seq[Entity[_]]): EntityRepository = {
        entities.foldLeft(this)((entityHolder, entity) => entityHolder + entity)
    }
    
    def ++(entities: Seq[Entity[_]]): EntityRepository = {
        addAll(entities)
    }
    
    def remove(entity: Entity[_]): EntityRepository = {
        val newEntitiesById = entitiesById - entity.id
        val newEntitiesByCoordinates = entity match {
            case en: PositionHolder[_] =>
                val coordinates = en.position.coordinates
                val newEntitiesAtCoordinates = entitiesByCoordinates.getOrElse(coordinates, Map.empty) - en.id
                if (newEntitiesAtCoordinates.isEmpty)
                    entitiesByCoordinates - coordinates
                else
                    entitiesByCoordinates + (coordinates -> newEntitiesAtCoordinates)
            case _ =>
                entitiesByCoordinates
        }
        new EntityRepository(newEntitiesById, newEntitiesByCoordinates)
    }
    
    def -(entity: Entity[_]): EntityRepository = {
        remove(entity)
    }
    
    def removeAll(entities: Seq[Entity[_]]): EntityRepository = {
        entities.foldLeft(this)((entityHolder, entity) => entityHolder - entity)
    }
    
    def --(entities: Seq[Entity[_]]): EntityRepository = {
        removeAll(entities)
    }
    
    def contains(id: String): Boolean = {
        entitiesById.contains(id)
    }
    
    def getAll: Vector[Entity[_]] = {
        entitiesById.values.toVector
    }
    
    def getById(id: String): Option[Entity[_]] = {
        entitiesById.get(id)
    }
    
    def getByCoordinates(coordinates: Coordinates): Vector[Entity[_]] = {
        entitiesByCoordinates.getOrElse(coordinates, Map.empty).values.toVector
    }
    
    def existsAtCoordinates(coordinates: Coordinates, condition: Entity[_] => Boolean): Boolean = {
        getByCoordinates(coordinates).exists(condition)
    }
    
    def forallAtCoordinates(coordinates: Coordinates, condition: Entity[_] => Boolean): Boolean = {
        getByCoordinates(coordinates).forall(condition)
    }
    
}

object EntityRepository {
    def apply(): EntityRepository = {
        new EntityRepository(Map.empty, Map.empty)
    }
    
    def apply(entities: Seq[Entity[_]]): EntityRepository = {
        EntityRepository() ++ entities
    }
}
