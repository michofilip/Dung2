import core.entities.EntityFactory
import core.entities.Entity
import core.entities.finals.nonmap.TimeCounter
import core.events.Event
import core.events.Event._
import core.parts.timer.Timer
import core.world.WorldFrame

object Main extends App {
    val timer: Timer = new Timer().start
    val entityFactory = new EntityFactory(timer)
    var ent = entityFactory.create("lever").get
    
    val entities = Vector[Entity](
        ent,
        new TimeCounter(0, timer),
        //        new entity.Entity.TurnCounter(0),
        //        new entity.Entity.ScriptRunner(0),
    )
    val events = Vector[Event](
        SwitchOn(1000),
        //        StopTime
    )
    
    var mapFrame = WorldFrame(entities, events)
    
    (1 to 14).foldLeft({
        println(mapFrame.toJSON.prettyPrint)
        mapFrame
    }) {
        case (mf, _) =>
            val mf1 = mf.nextFrame()
            println(mf1.toJSON.prettyPrint)
            Thread.sleep(100)
            mf1
    }
    
}
