import entity.selectors.{GraphicsSelector, PhysicsSelector}
import entity.{Entity, EntityFactory}

object Main extends App {
    implicit val physicsSelector: PhysicsSelector = new PhysicsSelector()
    implicit val graphicsSelector: GraphicsSelector = new GraphicsSelector()
    val entityFactory: EntityFactory = new EntityFactory()
    
    val entity: Entity = entityFactory.makeSwitch()
    
    println(entity)
    //    println(p.format(XmlParser.toXml(entity)))
}
