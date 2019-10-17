package math

object CustomMath {
    def mod(x: Int, n: Int): Int = if (x < 0) x % n + n else if (x < n) x else x % n
    
    def bound(x: Int, min: Int, max: Int): Int = if (x < min) min else if (x < max) x else max
    
    implicit class CM(x: Int) {
        def mod(n: Int): Int = CustomMath.mod(x, n)
        
        def bound(min: Int, max: Int): Int = CustomMath.bound(x, min, max)
    }
    
}
