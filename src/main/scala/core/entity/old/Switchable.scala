//package core.entity
//
//import core.entity.properties.StateHolder
//import core.parts.state.State._
//
//abstract class Switchable extends MapEntity with StateHolder {
//    override protected type T = Switchable
//    val switchingOffLength = 1000
//    val switchingOnLength = 1000
//
//    def beginSwitchingOff(time: Long): T = {
//        setState(SwitchingOff).setAnimationStartTime(time)
//    }
//
//    def finishSwitchingOff(time: Long): T = {
//        setState(Off).setAnimationStartTime(time)
//    }
//
//    def beginSwitchingOn(time: Long): T = {
//        setState(SwitchingOn).setAnimationStartTime(time)
//    }
//
//    def finishSwitchingOn(time: Long): T = {
//        setState(On).setAnimationStartTime(time)
//    }
//
////    @Deprecated
////    def setToOff(timeStamp: Long): T = {
////        setState(Off, timeStamp)
////    }
////
////    @Deprecated
////    def setToSwitchingOff(timeStamp: Long): T = {
////        setState(SwitchingOff, timeStamp)
////    }
////
////    @Deprecated
////    def setToSwitchingOn(timeStamp: Long): T = {
////        setState(SwitchingOn, timeStamp)
////    }
////
////    @Deprecated
////    def setToOn(timeStamp: Long): T = {
////        setState(On, timeStamp)
////    }
////
////    @Deprecated
////    def setSwitchableState(switchableState: SwitchableState, timeStamp: Long): T = {
////        (state, switchableState) match {
////            case (Off, SwitchingOn) => setState(switchableState, timeStamp)
////            case (SwitchingOn, On) => setState(switchableState, timeStamp)
////            case (On, SwitchingOff) => setState(switchableState, timeStamp)
////            case (SwitchingOff, Off) => setState(switchableState, timeStamp)
////
////            case _ => setState(state, 0)
////            //            case _ => setState(state, this.timeStamp)
////        }
////    }
//}