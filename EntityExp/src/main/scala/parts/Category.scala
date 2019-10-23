package parts

sealed abstract class Category

object Category {
    
    case object Floor extends Category
    
    case object Wall extends Category
    
    case object Switch extends Category
    
    case object Door extends Category
    
}
