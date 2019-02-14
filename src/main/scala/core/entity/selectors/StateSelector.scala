package core.entity.selectors

sealed abstract class StateSelector {
    
}

object StateSelector {
    
    case class TestSelector() extends StateSelector {
    }
    
}
