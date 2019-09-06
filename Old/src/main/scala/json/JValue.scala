package json

import json.JValue.{JArray, JNull, JObject}

import scala.collection.immutable.ListMap

sealed abstract class JValue {
    
    def /(index: Int): JValue = {
        this match {
            case JArray(values) if 0 <= index && index < values.length => values(index)
            case _ => JNull
        }
    }
    
    def /(key: String): JValue = {
        this match {
            case JObject(values) => values.getOrElse(key, JNull)
            case _ => JNull
        }
    }
    
    def >> : String
    
    def miniPrint: String = {
        prettify(separator = "", lineBreaker = "", indent = "")
    }
    
    def linePrint: String = {
        prettify(separator = " ", lineBreaker = " ", indent = "")
    }
    
    def prettyPrint: String = {
        prettify(separator = " ", lineBreaker = "\n", indent = "\t")
    }
    
    protected def tokenize: Vector[String]
    
    private def prettify(separator: String, lineBreaker: String, indent: String): String = {
        implicit class Tests(str: String) {
            def goesWith(that: String): Boolean = (str, that) match {
                case ("{", "}") => true
                case ("[", "]") => true
                case _ => false
            }
            
            def isOp: Boolean = Set("[", "{").contains(str)
            
            def isCl: Boolean = Set("]", "}").contains(str)
        }
        
        def shift(level: Int): String = List.fill(level)(indent).mkString
        
        def p(input: Vector[String], output: Vector[String], level: Int, ignoreShift: Boolean): Vector[String] = {
            val sh = if (ignoreShift) "" else shift(level)
            input match {
                case key +: ":" +: rest =>
                    p(rest, output :+ shift(level) :+ key :+ ":" :+ separator, level, ignoreShift = true)
                case "," +: rest =>
                    p(rest, output :+ "," :+ lineBreaker, level, ignoreShift = false)
                case c1 +: c2 +: rest if c1 goesWith c2 =>
                    p(rest, output :+ sh :+ c1 :+ c2, level, ignoreShift = false)
                case c1 +: c2 +: rest if c1.isOp && c2.isOp =>
                    p(c2 +: rest, output :+ c1 :+ lineBreaker, level + 1, ignoreShift = false)
                case c +: rest if c.isOp =>
                    p(rest, output :+ sh :+ c :+ lineBreaker, level + 1, ignoreShift = false)
                case c +: rest if c.isCl =>
                    p(rest, output :+ lineBreaker :+ shift(level - 1) :+ c, level - 1, ignoreShift = false)
                case str +: rest =>
                    p(rest, output :+ sh :+ str, level, ignoreShift = false)
                case _ => output
            }
        }
        
        p(tokenize, Vector.empty, 0, ignoreShift = false).mkString
    }
}

object JValue {
    
    final case object JNull extends JValue {
        protected override def tokenize: Vector[String] = {
            Vector("null")
        }
        
        override def >> : String = "null"
    }
    
    final case class JBoolean(value: Boolean) extends JValue {
        protected override def tokenize: Vector[String] = {
            Vector(value.toString)
        }
        
        override def >> : String = value.toString
    }
    
    final case class JInteger(value: Long) extends JValue {
        protected override def tokenize: Vector[String] = {
            Vector(value.toString)
        }
        
        override def >> : String = value.toString
    }
    
    final case class JDecimal(value: Double) extends JValue {
        protected override def tokenize: Vector[String] = {
            Vector(value.toString)
        }
        
        override def >> : String = value.toString
    }
    
    final case class JString(value: String) extends JValue {
        protected override def tokenize: Vector[String] = {
            Vector("\"" + value + "\"")
        }
        
        override def >> : String = value
    }
    
    final case class JObject(values: ListMap[String, JValue]) extends JValue {
        protected override def tokenize: Vector[String] = {
            val tokens = values.toList.map {
                case (key, value) =>
                    if (value == null)
                        Vector("\"" + key + "\"", ":", "null")
                    else
                        Vector("\"" + key + "\"", ":") ++ value.tokenize
            }
            join(tokens, "{", ",", "}")
        }
        
        override def >> : String = miniPrint
    }
    
    final case class JArray(values: Vector[JValue]) extends JValue {
        protected override def tokenize: Vector[String] = {
            val tokens = values.map(value =>
                if (value == null)
                    Vector("null")
                else
                    value.tokenize
            )
            join(tokens, "[", ",", "]")
        }
        
        override def >> : String = miniPrint
    }
    
    private def join(seq: Seq[Vector[String]], prefix: String, separator: String, suffix: String): Vector[String] = {
        val separated = seq.foldLeft(Vector.empty[String]) {
            case (out, vec) if out.isEmpty => out ++ vec
            case (out, vec) => out ++ Vector(separator) ++ vec
        }
        Vector(prefix) ++ separated ++ Vector(suffix)
    }
}
