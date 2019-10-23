//package xml
//
//import entity.Entity
//import parts.{Animation, Coordinates, Frame, Physics, Position}
//
//import scala.xml.{Elem, NodeSeq}
//
//object XmlParser {
//    def toXml(coordinates: Coordinates): Elem = {
//        //            <coordinates x={coordinates.x.toString} y={coordinates.y.toString}/>
//        <coordinates>
//            <x>
//                {coordinates.x}
//            </x>
//            <y>
//                {coordinates.y}
//            </y>
//        </coordinates>
//    }
//
//    def toXml(position: Position): Elem = {
//        <position>
//            {toXml(position.coordinates)}<direction>
//            {position.direction}
//        </direction>
//            <can_move>
//                {position.canMove}
//            </can_move>
//            <can_rotate>
//                {position.canRotate}
//            </can_rotate>
//        </position>
//    }
//
//    def toXml(physics: Physics): Elem = {
//        <physics>
//            <solid>
//                {physics.solid}
//            </solid>
//            <opaque>
//                {physics.opaque}
//            </opaque>
//        </physics>
//    }
//
//    def toXml(entity: Entity): Elem = {
//        val positionXml: NodeSeq = entity.position match {
//            case Some(position) => toXml(position)
//            case None => NodeSeq.Empty
//        }
//
//        val physicsXml: NodeSeq = entity.physics match {
//            case Some(physics) => toXml(physics)
//            case None => NodeSeq.Empty
//        }
//
//        val animationXml: NodeSeq = entity.animation match {
//            case Some(animation) => toXml(animation)
//            case None => NodeSeq.Empty
//        }
//
//        <entity id={entity.id.toString}>
//            {positionXml}{physicsXml}{animationXml}
//        </entity>
//    }
//
//    def toXml(frame: Frame): Elem = {
//        <frame id={frame.id.toString}>
//            <x_offset>
//                {frame.xOffset}
//            </x_offset>
//            <y_offset>
//                {frame.yOffset}
//            </y_offset>
//        </frame>
//    }
//
//    def toXml(animation: Animation): Elem = {
//        <animation>
//            <frames>
//                {animation.frames.map(toXml)}
//            </frames>
//            <duration>
//                {animation.duration}
//            </duration>
//            <is_looped>
//                {animation.isLooped}
//            </is_looped>
//        </animation>
//    }
//}
