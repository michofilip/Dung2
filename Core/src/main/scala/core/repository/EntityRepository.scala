package core.repository

import core.entities.Entity
import core.entities.traits.properties.PositionHolder
import core.parts.position.Coordinates

class EntityRepository private(private val entitiesById: Map[Long, Entity],
                               private val entitiesByCoordinates: Map[Coordinates, Map[Long, Entity]]) {
    
    def add(entity: Entity): EntityRepository = {
        val newEntitiesById = entitiesById + (entity.id -> entity)
        
        val newEntitiesByCoordinates = entity match {
            case en: PositionHolder =>
                val coordinates = en.position.coordinates
                val newEntitiesAtCoordinates = entitiesByCoordinates.getOrElse(coordinates, Map.empty) + (en.id -> en)
                entitiesByCoordinates + (coordinates -> newEntitiesAtCoordinates)
            case _ => entitiesByCoordinates
        }
        new EntityRepository(newEntitiesById, newEntitiesByCoordinates)
    }
    
    def +(entity: Entity): EntityRepository = add(entity)
    
    def addAll(entities: Seq[Entity]): EntityRepository =
        entities.foldLeft(this)((entityHolder, entity) => entityHolder + entity)
    
    def ++(entities: Seq[Entity]): EntityRepository = addAll(entities)
    
    def remove(entity: Entity): EntityRepository = {
        val newEntitiesById = entitiesById - entity.id
        
        val newEntitiesByCoordinates = entity match {
            case en: PositionHolder =>
                val coordinates = en.position.coordinates
                val newEntitiesAtCoordinates = entitiesByCoordinates.getOrElse(coordinates, Map.empty) - en.id
                if (newEntitiesAtCoordinates.isEmpty)
                    entitiesByCoordinates - coordinates
                else
                    entitiesByCoordinates + (coordinates -> newEntitiesAtCoordinates)
            case _ => entitiesByCoordinates
        }
        new EntityRepository(newEntitiesById, newEntitiesByCoordinates)
    }
    
    def -(entity: Entity): EntityRepository = remove(entity)
    
    def removeAll(entities: Seq[Entity]): EntityRepository =
        entities.foldLeft(this)((entityHolder, entity) => entityHolder - entity)
    
    def --(entities: Seq[Entity]): EntityRepository = removeAll(entities)
    
    def contains(id: Long): Boolean = entitiesById.contains(id)
    
    def contains(coordinates: Coordinates): Boolean = entitiesByCoordinates.contains(coordinates)
    
    def getAll: Vector[Entity] = entitiesById.values.toVector
    
    def getById(id: Long): Option[Entity] = entitiesById.get(id)
    
    def getByCoordinates(coordinates: Coordinates): Vector[Entity] =
        entitiesByCoordinates.getOrElse(coordinates, Map.empty).values.toVector
    
    def existsAtCoordinates(coordinates: Coordinates, condition: Entity => Boolean): Boolean =
        getByCoordinates(coordinates).exists(condition)
    
    def forallAtCoordinates(coordinates: Coordinates, condition: Entity => Boolean): Boolean =
        getByCoordinates(coordinates).forall(condition)
    
}

object EntityRepository {
    def apply(): EntityRepository = new EntityRepository(Map.empty, Map.empty)
    
    def apply(entities: Seq[Entity]): EntityRepository = EntityRepository() ++ entities
}


