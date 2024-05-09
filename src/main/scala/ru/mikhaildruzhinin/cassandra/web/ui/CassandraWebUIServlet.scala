package ru.mikhaildruzhinin.cassandra.web.ui

import com.datastax.oss.driver.api.core.`type`.DataType
import com.datastax.oss.driver.api.core.{CqlIdentifier, CqlSession}
import com.datastax.oss.driver.api.core.cql.{ColumnDefinition, ResultSet}
import org.scalatra._

import scala.jdk.CollectionConverters._

class CassandraWebUIServlet extends ScalatraServlet {
  get("/") {

    val session: CqlSession = CqlSession.builder().build()
    val resultSet: ResultSet = session.execute("describe tables")

    val columnDefinitions: List[ColumnDefinition] = resultSet
      .getColumnDefinitions
      .iterator()
      .asScala
      .toList

    val columnNames: List[String] = columnDefinitions.map(_.getName.asCql(true))

    val rows: List[String] = resultSet
      .iterator()
      .asScala
      .toList
      .map(row => columnDefinitions.map(col => row.get(col.getName, classOf[String])).mkString(", "))

      columnNames.mkString(", ") + "\n" + rows.mkString("\n")
  }
}
