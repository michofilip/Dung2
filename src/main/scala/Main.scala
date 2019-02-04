import core.entity.{Entity, EntityFactory}
import core.event.Event
import core.event.Event._
import core.timer.Timer
import core.world.{EntityHolder, WorldFrame}

object Main extends App {
    val timeCounter: Timer = new Timer(0, true)
    val entityFactory = new EntityFactory(timeCounter)
    var ent = entityFactory.create("lever").get
    
    val ents = Seq[Entity](ent)
    val events = Vector[Event](
        SwitchOn("1000")
    )
    val entityMap = EntityHolder(ents)
    
    var mapFrame = new WorldFrame(entityMap, events, timeCounter, 1)
    
    (1 to 14).foldLeft({
        println(mapFrame.toJSON.prettyPrint)
        mapFrame
    }) {
        case (mf, _) =>
            val mf1 = mf.nextFrame().nextTurn()
            println(mf1.toJSON.prettyPrint)
            Thread.sleep(100)
            mf1
    }
    
}
