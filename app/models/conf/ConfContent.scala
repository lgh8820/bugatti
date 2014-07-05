package models.conf

import play.api.Play.current
import models.PlayCache

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.JdbcBackend

/**
 * 子项目配置文件内容
 *
 * @author of546
 */
case class ConfContent(id: Option[Int], content: String)
class ConfContentTable(tag: Tag) extends Table[ConfContent](tag, "conf_content") {
  def id = column[Int]("id", O.PrimaryKey) // 子项目配置文件编号
  def content = column[String]("content", O.DBType("text"))

  override def * = (id.?, content) <> (ConfContent.tupled, ConfContent.unapply _)
}
object ConfContentHelper extends PlayCache {

  import models.AppDB._

  val qConfContent = TableQuery[ConfContentTable]

  def findById(id: Int): Option[ConfContent] = db withSession { implicit session =>
    qConfContent.where(_.id is id).firstOption
  }

  def create_(content: ConfContent)(implicit session: JdbcBackend#Session) = {
    qConfContent.insert(content)(session)
  }

  def delete_(id: Int)(implicit session: JdbcBackend#Session) = {
    qConfContent.where(_.id is id).delete(session)
  }

  def update_(id: Int, content: ConfContent)(implicit session: JdbcBackend#Session) = {
    val content2update = content.copy(Some(id))
    qConfContent.where(_.id is id).update(content2update)(session)
  }

}