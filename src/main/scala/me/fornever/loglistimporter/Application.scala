package me.fornever.loglistimporter

import java.io.{FileInputStream, InputStreamReader}
import java.sql.Timestamp
import java.util.Properties

import scala.slick.driver.H2Driver.{simple => h2}
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

      h2.Database.forURL(
        config.getProperty("actual.url"),
        config.getProperty("actual.user"),
        config.getProperty("actual.password")) withSession { implicit h2Session =>
        val actualQuotes = h2.TableQuery[actual.QuoteTable]

        var counter = 0
        legacyQuotes.foreach({case (id, text, time) =>
           actualQuotes += (0, new Timestamp(time), text)
            counter += 1
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