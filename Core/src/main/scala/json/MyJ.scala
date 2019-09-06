package json

import json.JValue._

import scala.collection.immutable.ListMap
import scala.language.implicitConversions

object MyJ {
    
    implicit def any2JValue(value: Any): JValue = jValue(value)
    
    implicit class ToJSON(value: Any) {
        def toJSON: JValue = jValue(value)
    }
    
    def jValue(value: Any): JValue = {
        value match {
            case v: JValue => v
            case v: JSONParsable => v.toJSON
            
            case vs: Map[_, _] =>
                val jvs = vs.toSeq.map {
                    case (k: String, v) => k -> jValue(v)
                    case (k, v) => k.toString -> jValue(v)
                }
                JObject(ListMap(jvs: _*))
            
            case vs: Seq[_] =>
                val jvs = vs map jValue
                JArray(jvs.toVector)
            case vs: Set[_] =>
                val jvs = vs map jValue
                JArray(jvs.toVector)
            
            case v: Option[Any] => v match {
                case Some(v1) => jValue(v1)
                case None => JNull
            }
            
            case v: Boolean => JBoolean(v)
            case v: Byte => JInteger(v)
            case v: Short => JInteger(v)
            case v: Int => JInteger(v)
            case v: Long => JInteger(v)
            case v: Float => JDecimal(v)
            case v: Double => JDecimal(v)
            case v: Char => JString(v.toString)
            case v: String => JString(v)
            
            case v => JString(v.toString)
        }
    }
    
    def jObject(values: (String, Any)*): JObject = {
        val vs = values.map {
            case (k, v) => k -> jValue(v)
        }
        JObject(ListMap(vs: _*))
    }
    
    def jArray(values: Any*): JArray = {
        JArray(values.map(jValue).toVector)
    }
    
    def parse(string: String): JValue = {
        def tokenize(string: String): Vector[String] = {
            val tokens = Set(':', ',', '{', '}', '[', ']')
            
            def t(chars: List[Char], word: Vector[Char], parts: Vector[String], ignoreTokens: Boolean): Vector[String] = {
                chars match {
                    case c :: rest if !ignoreTokens && tokens.contains(c) =>
                        t(rest, Vector.empty, parts :+ word.mkString :+ c.toString, ignoreTokens = false)
                    case c :: rest =>
                        if (c == '\"')
                            t(rest, word :+ c, parts, !ignoreTokens)
                        else
                            t(rest, word :+ c, parts, ignoreTokens)
                    case _ =>
                        parts :+ word.mkString
                }
            }
            
            val tokenized = t(string.toList, Vector.empty, Vector.empty, ignoreTokens = false)
            val trimmed = tokenized.map(str => str.trim)
            val filtered = trimmed.filter(str => str.nonEmpty)
            
            filtered
        }
        
        def isInt(str: String): Boolean = {
            try {
                str.toInt
                true
            } catch {
                case _: NumberFormatException => false
            }
        }
        
        def isDouble(str: String): Boolean = {
            try {
                str.toDouble
                true
            } catch {
                case _: NumberFormatException => false
            }
        }
        
        def isString(str: String): Boolean = {
            str.length >= 2 && str.startsWith("\"") && str.endsWith("\"")
        }
        
        def getString(str: String): String = {
            str.substring(1, str.length - 1)
        }
        
        def obj(in: Vector[String], pairs: ListMap[String, JValue]): (JObject, Vector[String]) = {
            in match {
                case "," +: rest => obj(rest, pairs)
                case "}" +: rest => (JObject(pairs), rest)
                
                case key +: ":" +: "[" +: rest if isString(key) =>
                    val (jArr, vec) = arr(rest, Vector.empty)
                    obj(vec, pairs + (getString(key) -> jArr))
                case key +: ":" +: "{" +: rest if isString(key) =>
                    val (jObj, vec) = obj(rest, ListMap.empty)
                    obj(vec, pairs + (getString(key) -> jObj))
                
                case key +: ":" +: "null" +: rest if isString(key) =>
                    obj(rest, pairs + (getString(key) -> JNull))
                case key +: ":" +: "false" +: rest if isString(key) =>
                    obj(rest, pairs + (getString(key) -> JBoolean(false)))
                case key +: ":" +: "true" +: rest if isString(key) =>
                    obj(rest, pairs + (getString(key) -> JBoolean(true)))
                
                case key +: ":" +: value +: rest if isString(key) =>
                    if (isString(value))
                        obj(rest, pairs + (getString(key) -> JString(getString(value))))
                    else if (isInt(value))
                        obj(rest, pairs + (getString(key) -> JInteger(value.toInt)))
                    else if (isDouble(value))
                        obj(rest, pairs + (getString(key) -> JDecimal(value.toDouble)))
                    else
                        throw new JSONFormatException()
                
                case _ => throw new JSONFormatException()
            }
        }
        
        def arr(in: Vector[String], values: Vector[JValue]): (JArray, Vector[String]) = {
            in match {
                case "," +: rest => arr(rest, values)
                case "]" +: rest => (JArray(values), rest)
                
                case "[" +: rest =>
                    val (jArr, vec) = arr(rest, Vector.empty)
                    arr(vec, values :+ jArr)
                case "{" +: rest =>
                    val (jObj, vec) = obj(rest, ListMap.empty)
                    arr(vec, values :+ jObj)
                
                case "null" +: rest => arr(rest, values :+ JNull)
                case "false" +: rest => arr(rest, values :+ JBoolean(false))
                case "true" +: rest => arr(rest, values :+ JBoolean(true))
                
                case value +: rest =>
                    if (isString(value))
                        arr(rest, values :+ JString(getString(value)))
                    else if (isInt(value))
                        arr(rest, values :+ JInteger(value.toInt))
                    else if (isDouble(value))
                        arr(rest, values :+ JDecimal(value.toDouble))
                    else
                        throw new JSONFormatException()
                
                case _ => throw new JSONFormatException()
            }
        }
        
        val tokenized = tokenize(string)
        tokenized match {
            case "[" +: rest =>
                val (jVal, _) = arr(rest, Vector.empty)
                jVal
            case "{" +: rest =>
                val (jVal, _) = obj(rest, ListMap.empty)
                jVal
            case _ => throw new JSONFormatException()
        }
    }
    
}
