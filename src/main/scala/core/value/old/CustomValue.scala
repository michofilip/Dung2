//package core.value
//
//import core.entity.properties.state.State
//import core.level.MapFrame
//import core.value.Value.CustomValue
//
//import scala.language.implicitConversions
//
//object CustomValue {
//
//    object StateValue {
//
//        sealed abstract class StateValue extends CustomValue {
//            override final type T = State
//        }
//
//        final case class StateConstant(value: State) extends StateValue {
//            override def get(implicit mapFrame: MapFrame): Option[State] = Some(value)
//        }
//
//    }
//
//    //    implicit def st2v(value: State): StateValue = StateConstant(value)
//    //
//    //    implicit def dir2v(value: Direction): DirectionValue = DirectionConstant(value)
//    //
//    //    implicit class St2v(value: State) {
//    //        def toValue: StateValue = value
//    //
//    //        def toStateValue: StateValue = value
//    //    }
//    //
//    //    implicit class Dir2v(value: Direction) {
//    //        def toValue: DirectionValue = value
//    //
//    //        def toStateValue: DirectionValue = value
//    //    }
//
//    def apply(value: State): StateConstant = StateConstant(value)
//
//    def apply(value: Direction): DirectionConstant = DirectionConstant(value)
//
//    // state classes
//
//
//    // direction classes
//    sealed abstract class DirectionValue extends CustomValue[Direction]
//
//    final case class DirectionConstant(value: Direction) extends DirectionValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Direction] = Some(value)
//    }
//
//    // value extractors
//    final case class GetByte(id: Int) extends CustomByteValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Byte] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: ByteValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetShort(id: Int) extends CustomShortValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Short] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: ShortValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//
//    }
//
//    final case class GetInt(id: Int) extends CustomIntValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Int] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: IntValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetLong(id: Int) extends CustomLongValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Long] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: LongValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetFloat(id: Int) extends CustomFloatValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Float] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: FloatValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetDouble(id: Int) extends CustomDoubleValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Double] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: DoubleValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetBoolean(id: Int) extends CustomBooleanValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Boolean] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: BooleanValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetChar(id: Int) extends CustomCharValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Char] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: CharValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetString(id: Int) extends CustomStringValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[String] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: StringValue => v.get
//                    case _ => None
//                }
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetX(id: Int) extends CustomIntValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Int] = {
//            entityMap.get(id) match {
//                case Some(en: MapEntity[_]) => Some(en.position.x)
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetY(id: Int) extends CustomIntValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Int] = {
//            entityMap.get(id) match {
//                case Some(en: MapEntity[_]) => Some(en.position.y)
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetState(id: Int) extends CustomValue[State] {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[State] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: StateValue => v.get
//                    case _ => None
//                }
//                case Some(en: MultiState[_]) => Some(en.state)
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetDirection(id: Int) extends CustomValue[Direction] {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Direction] = {
//            entityMap.get(id) match {
//                case Some(en: ValueHolder[_]) => en.value match {
//                    case v: DirectionValue => v.get
//                    case _ => None
//                }
//                case Some(en: Directional[_]) => Some(en.direction)
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetMapTime(id: Int) extends CustomLongValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Long] = {
//            entityMap.get(id) match {
//                case Some(en: TimeCounter[_]) => Some(en.time)
//                case _ => None
//            }
//        }
//    }
//
//    final case class GetStateChangeTime(id: Int) extends CustomLongValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Long] = {
//            entityMap.get(id) match {
//                case Some(en: MultiState[_]) => Some(en.stateChangeTime)
//                case _ => None
//            }
//        }
//    }
//
//    final case class ExistEntity(id: Int) extends CustomBooleanValue {
//        override def get(implicit entityMap: Map[Int, Entity]): Option[Boolean] = {
//            Some(entityMap.contains(id))
//        }
//    }
//
//}
