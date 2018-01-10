package models

import java.sql.Connection

import anorm.{RowParser, SQL, SqlParser}

import scala.concurrent.Future

/**
  * Created by sheep3 on 2017/10/30.
  */
abstract class BaseRepository[T] {

  def TABLE_NAME: String

  def PK_ROW: String = "id"

  def simple: RowParser[T]


  def findAll(deleted: Boolean = false)(implicit connection: Connection) = {
    SQL(s"SELECT * FROM $TABLE_NAME WHERE deleted={deleted}")
      .on('deleted -> deleted).as(simple *)
  }

  def findById(id: Long, deleted: Boolean = false)(implicit connection: Connection) = {
    SQL(s"SELECT * FROM $TABLE_NAME WHERE $PK_ROW={id} AND deleted={deleted}")
      .on('deleted -> deleted, 'id -> id).as(simple.singleOpt)
  }

  def findByIdIn(ids: Seq[Long], deleted: Boolean = false)(implicit connection: Connection) = {
    SQL(s"SELECT * FROM $TABLE_NAME WHERE $PK_ROW IN (${ids.mkString(",")}) AND deleted={deleted}")
      .on('deleted -> deleted).as(simple *)
  }

  def count(deleted: Boolean = false)(implicit connection: Connection) = {
    SQL(s"SELECT COUNT($PK_ROW) FROM $TABLE_NAME AND deleted={deleted}")
      .on('deleted -> deleted).as(SqlParser.scalar[Long].singleOpt).getOrElse(0)
  }

  def delete(id: Long)(implicit connection: Connection) = {
    SQL(s"DELETE FROM $TABLE_NAME WHERE id={id}")
      .on('id -> id).executeUpdate()
  }

}
