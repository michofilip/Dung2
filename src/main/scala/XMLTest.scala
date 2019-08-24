import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import core.entities.EntityFactory
import core.parts.timer.Timer

import scala.xml.Elem


object XMLTest extends App {
//    val timer: Timer = new Timer().start
//    val entityFactory = new EntityFactory(timer)
//    var ent = entityFactory.create("lever").get
//
//    val xstream = new XStream(new DomDriver())
//    xstream.alias(ent.getClass.getSimpleName, ent.getClass)
//    val xml = xstream.toXML(ent)
//    println(xml)
    
    case class Car(id: Int, door: Int) {
        def toXML: Elem =
            <Car id={id.toString}/>
    }
    
    println(Car(2,4).toXML)
    
}
