package core.parts.inventory

import core.entity.Entity

sealed abstract class Inventory {
    protected type InventoryType
    val slots: IndexedSeq[Slot]
    
    def add(entity: Entity, amount: Int = 1): InventoryType
    
    def remove(entity: Entity, amount: Int = 1): InventoryType
}

object Inventory {
    
    final class FiniteInventory extends Inventory {
        override protected type InventoryType = this.type
        override val slots: IndexedSeq[Slot] = ???
        
        override def add(entity: Entity, amount: Int): InventoryType = ???
        
        override def remove(entity: Entity, amount: Int): InventoryType = ???
    }
    
    final class InfiniteInventory extends Inventory {
        override protected type InventoryType = this.type
        override val slots: IndexedSeq[Slot] = ???
        
        override def add(entity: Entity, amount: Int): InventoryType = ???
        
        override def remove(entity: Entity, amount: Int): InventoryType = ???
    }
    
}