package core.entity.properties.inventory

sealed abstract class Slot {
    
}

object Slot {
    
    case object EmptySlot extends Slot
    
    case class OccupiedSlot(entityId: Long, amount: Int) extends Slot
    
}