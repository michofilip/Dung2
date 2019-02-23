package core.entity

import core.entity.properties.PositionHolder
import core.entity.properties.position.Coordinates

class EntityHolder private(private val entitiesById: Map[String, Entity],
                           private val entitiesByCoordinates: Map[Coordinates, Map[String, Entity]]) {
    
    def add(entity: Entity): EntityHolder = {
        val newEntitiesById = entitiesById + (entity.id -> entity)
        val newEntitiesByCoordinates = entity match {
            case en: PositionHolder =>
                val coordinates = en.position.coordinates
                val newEntitiesAtCoordinates = entitiesByCoordinates.getOrElse(coordinates, Map.empty) + (en.id -> en)
                entitiesByCoordinates + (coordinates -> newEntitiesAtCoordinates)
            case _ =>
                entitiesByCoordinates
        }
        new EntityHolder(newEntitiesById, newEntitiesByCoordinates)
    }
    
    def +(entity: Entity): EntityHolder = {
        add(entity)
    }
    
    def addAll(entities: Seq[Entity]): EntityHolder = {
        entities.foldLeft(this)((entityHolder, entity) => entityHolder + entity)
    }
    
    def ++(entities: Seq[Entity]): EntityHolder = {
        addAll(entities)
    }
    
    def remove(entity: Entity): EntityHolder = {
        val newEntitiesById = entitiesById - entity.id
        val newEntitiesByCoordinates = entity match {
            case en: PositionHolder =>
                val coordinates = en.position.coordinates
                val newEntitiesAtCoordinates = entitiesByCoordinates.getOrElse(coordinates, Map.empty) - en.id
                if (newEntitiesAtCoordinates.isEmpty)
                    entitiesByCoordinates - coordinates
                else
                    entitiesByCoordinates + (coordinates -> newEntitiesAtCoordinates)
            case _ =>
                entitiesByCoordinates
        }
        new EntityHolder(newEntitiesById, newEntitiesByCoordinates)
    }
    
    def -(entity: Entity): EntityHolder = {
        remove(entity)
    }
    
    def removeAll(entities: Seq[Entity]): EntityHolder = {
        entities.foldLeft(this)((entityHolder, entity) => entityHolder - entity)
    }
    
    def --(entities: Seq[Entity]): EntityHolder = {
        removeAll(entities)
    }
    
    def contains(id: String): Boolean = {
        entitiesById.contains(id)
    }
    
    def getAll: Vector[Entity] = {
        entitiesById.values.toVector
    }
    
    def getById(id: String): Option[Entity] = {
        entitiesById.get(id)
    }
    
    def getByCoordinates(coordinates: Coordinates): Vector[Entity] = {
        entitiesByCoordinates.getOrElse(coordinates, Map.empty).values.toVector
    }
    
    def existsAtCoordinates(coordinates: Coordinates, condition: Entity => Boolean): Boolean = {
        getByCoordinates(coordinates).exists(condition)
    }
    
    def forallAtCoordinates(coordinates: Coordinates, condition: Entity => Boolean): Boolean = {
        getByCoordinates(coordinates).forall(condition)
    }
    
}

object EntityHolder {
    def apply(): EntityHolder = {
        new EntityHolder(Map.empty, Map.empty)
    }
    
    def apply(entities: Seq[Entity]): EntityHolder = {
        EntityHolder() ++ entities
    }
}
