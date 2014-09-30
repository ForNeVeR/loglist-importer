package me.fornever.loglistimporter.actual

import java.sql.Timestamp
import scala.slick.driver.PostgresDriver.simple._

class QuoteTable(tag: Tag) extends Table[(Long, Timestamp, String)](tag, "QUOTE") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def time = column[Timestamp]("TIME")
  def content = column[String]("CONTENT")

  def * = (id, time, content)

}
