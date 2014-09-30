package me.fornever.loglistimporter

import java.io.{FileInputStream, InputStreamReader}
import java.sql.Timestamp
import java.util.Properties

import org.apache.commons.lang3.StringEscapeUtils

import scala.slick.driver.PostgresDriver.{simple => postgreSQL}
import scala.slick.driver.MySQLDriver.{simple => mySQL}

object Application extends App {

  override def main(args: Array[String]): Unit = {
    val config = getConfig(args)
    mySQL.Database.forURL(
      config.getProperty("legacy.url"),
      config.getProperty("legacy.user"),
      config.getProperty("legacy.password")) withSession { mySQLSession =>
      import mySQL._
      val legacyQuotes = mySQL.TableQuery[legacy.QuoteTable]

      postgreSQL.Database.forURL(
        config.getProperty("actual.url"),
        config.getProperty("actual.user"),
        config.getProperty("actual.password")) withSession { implicit h2Session =>
        val actualQuotes = postgreSQL.TableQuery[actual.QuoteTable]

        var counter = 0
        legacyQuotes.foreach({case (id, text, time) =>
          if (!text.contains("<a")) {
            val properText = StringEscapeUtils.unescapeHtml4(text.replace("<br>", "\n"))
            actualQuotes += (0, new Timestamp(time), properText)
            counter += 1
          }
        })(mySQLSession)
        println(s"$counter rows were imported")
      }
    }
  }

  def getConfig(args: Array[String]): Properties = {
    val configPath = args match {
      case Array(path) => path
      case Array() => "./app.properties"
    }

    val properties = new Properties()
    val stream = new InputStreamReader(new FileInputStream(configPath), "UTF8")
    try {
      properties.load(stream)
      properties
    } finally {
      stream.close()
    }
  }

}