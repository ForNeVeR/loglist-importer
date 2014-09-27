package me.fornever.loglistimporter.legacy

import scala.slick.driver.MySQLDriver.simple._

class QuoteTable(tag: Tag) extends Table[(Int, String, Int)](tag, "list") {

  def id = column[Int]("id", O.PrimaryKey)
  def text = column[String]("text")
  def time = column[Int]("time")

  def * = (id, text, time)

}
