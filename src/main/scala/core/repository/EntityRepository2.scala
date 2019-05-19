package core.repository

import core.entity2.Entity2
import core.entity2.traits.PositionHolder2
import core.parts.position.Coordinates

class EntityRepository2 private(private val entitiesById: Map[Long, Entity2],
                                private val entitiesByCoordinates: Map[Coordinates, Map[Long, Entity2]]) {
    
    def add(entity: Entity2): EntityRepository2 = {
        val newEntitiesById = entitiesById + (entity.id -> entity)
        val newEntitiesByCoordinates = entity match {
            case en: PositionHolder2 =>
                val coordinates = en.position.coordinates
                val newEntitiesAtCoordinates = entitiesByCoordinates.getOrElse(coordinates, Map.empty) + (en.id -> en)
                entitiesByCoordinates + (coordinates -> newEntitiesAtCoordinates)
            case _ =>
                entitiesByCoordinates
        }
        new EntityRepository2(newEntitiesById, newEntitiesByCoordinates)
    }
    
    def +(entity: Entity2): EntityRepository2 = {
        add(entity)
    }
    
    def addAll(entities: Seq[Entity2]): EntityRepository2 = {
        entities.foldLeft(this)((entityHolder, entity) => entityHolder + entity)
    }
    
    def ++(entities: Seq[Entity2]): EntityRepository2 = {
        addAll(entities)
    }
    
    def remove(entity: Entity2): EntityRepository2 = {
        val newEntitiesById = entitiesById - entity.id
        val newEntitiesByCoordinates = entity match {
            case en: PositionHolder2 =>
                val coordinates = en.position.coordinates
                val newEntitiesAtCoordinates = entitiesByCoordinates.getOrElse(coordinates, Map.empty) - en.id
                if (newEntitiesAtCoordinates.isEmpty)
                    entitiesByCoordinates - coordinates
                else
                    entitiesByCoordinates + (coordinates -> newEntitiesAtCoordinates)
            case _ =>
                entitiesByCoordinates
        }
        new EntityRepository2(newEntitiesById, newEntitiesByCoordinates)
    }
    
    def -(entity: Entity2): EntityRepository2 = {
        remove(entity)
    }
    
    def removeAll(entities: Seq[Entity2]): EntityRepository2 = {
        entities.foldLeft(this)((entityHolder, entity) => entityHolder - entity)
    }
    
    def --(entities: Seq[Entity2]): EntityRepository2 = {
        removeAll(entities)
    }
    
    def contains(id: Long): Boolean = {
        entitiesById.contains(id)
    }
    
    def getAll: Vector[Entity2] = {
        entitiesById.values.toVector
    }
    
    def getById(id: Long): Option[Entity2] = {
        entitiesById.get(id)
    }
    
    def getByCoordinates(coordinates: Coordinates): Vector[Entity2] = {
        entitiesByCoordinates.getOrElse(coordinates, Map.empty).values.toVector
    }
    
    def existsAtCoordinates(coordinates: Coordinates, condition: Entity2 => Boolean): Boolean = {
        getByCoordinates(coordinates).exists(condition)
    }
    
    def forallAtCoordinates(coordinates: Coordinates, condition: Entity2 => Boolean): Boolean = {
        getByCoordinates(coordinates).forall(condition)
    }
    
}

object EntityRepository2 {
    def apply(): EntityRepository2 = {
        new EntityRepository2(Map.empty, Map.empty)
    }
    
    def apply(entities: Seq[Entity2]): EntityRepository2 = {
        EntityRepository2() ++ entities
    }
}


