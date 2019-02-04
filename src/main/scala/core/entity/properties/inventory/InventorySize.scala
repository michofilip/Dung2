package core.entity.properties.inventory

sealed abstract class InventorySize

object InventorySize {
    
    case object Infinite extends InventorySize
    
    case class Finite(size: Int) extends InventorySize
    
}
