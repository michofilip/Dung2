import entity.EntityMutator._
import entity.{Entity, EntityFactory}
import repositories.GraphicsRepository
import selectors.{AnimationSelector, PhysicsSelector}

object Main extends App {
    implicit val graphicsRepository: GraphicsRepository = new GraphicsRepository()
    implicit val physicsSelector: PhysicsSelector = new PhysicsSelector()
    implicit val graphicsSelector: AnimationSelector = new AnimationSelector()
    val entityFactory: EntityFactory = new EntityFactory()
    
    val entity: Entity = entityFactory.makeSwitch(1, 10, 20)
    
    println(entity)
    println(entity.beginSwitchingOn())
    //    println(p.format(XmlParser.toXml(entity)))
}
