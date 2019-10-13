import entity2.Entity
import entity2.EntityProcessor._
import entity2.parts2._
import xml.XmlParser

object Main extends App {
    val p = new scala.xml.PrettyPrinter(80, 4)
    val entity: Entity = Entity(0)
            .setPosition(Position(Coordinates(10, 25), 90, canMove = false, canRotate = true))
            .setPhysics(Physics(solid = true, opaque = false))
            .setAnimation(Animation(Vector(Frame(0), Frame(1), Frame(2), Frame(3)), 1000, isLooped = false))
            .moveTo(15, 15)
            .rotateBy(45)
            .setOpaque(true)
    
    println(p.format(XmlParser.toXml(entity)))
}
