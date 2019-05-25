import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import core.entities.EntityFactory
import core.parts.timer.Timer


object XMLTest extends App {
    val timer: Timer = new Timer().start
    val entityFactory = new EntityFactory(timer)
    var ent = entityFactory.create("lever").get
    
    val xstream = new XStream(new DomDriver())
    xstream.alias(ent.getClass.getSimpleName, ent.getClass)
    val xml = xstream.toXML(ent)
    println(xml)
    
    
}
