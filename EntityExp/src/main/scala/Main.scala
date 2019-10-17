import entity.EntityMutator._
import entity.selectors.{AnimationSelector, PhysicsSelector}
import entity.{Entity, EntityFactory}

object Main extends App {
    implicit val physicsSelector: PhysicsSelector = new PhysicsSelector()
    implicit val graphicsSelector: AnimationSelector = new AnimationSelector()
    val entityFactory: EntityFactory = new EntityFactory()
    
    val entity: Entity = entityFactory.makeSwitch()
    
    println(entity)
    println(entity.beginSwitchingOn())
    //    println(p.format(XmlParser.toXml(entity)))
}
