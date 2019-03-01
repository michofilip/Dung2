import core.entities.finals.TimeCounter
import core.entities.{Entity, EntityFactory}
import core.event.Event
import core.event.Event._
import core.parts.timer.Timer
import core.world.WorldFrame

object Main extends App {
    val timeCounter: Timer = new Timer(0, true)
    val entityFactory = new EntityFactory(timeCounter)
    var ent = entityFactory.create("lever").get
    
    val entities = Vector[Entity](
        ent,
        new TimeCounter("TimeCounter", timeCounter),
        //        new entity.Entity.TurnCounter(0),
        //        new entity.Entity.ScriptRunner(0),
    )
    val events = Vector[Event](
        SwitchOn("1000"),
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
