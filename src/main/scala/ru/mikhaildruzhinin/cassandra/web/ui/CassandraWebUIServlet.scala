package ru.mikhaildruzhinin.cassandra.web.ui

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.{ColumnDefinition, ResultSet}
import org.scalatra._
import org.scalatra.forms._
import org.scalatra.i18n.I18nSupport
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._

class CassandraWebUIServlet extends ScalatraServlet with FormSupport with I18nSupport {

  private val logger: Logger =  LoggerFactory.getLogger(getClass)

  get("/") {
    html.index(None)
  }

  post("/") {
    val query = params("query")
    logger.info(query)

    val session: CqlSession = CqlSession.builder().build()
    val resultSet: ResultSet = session.execute(query)  // "describe tables"
    session.close()

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

    logger.info(columnNames.mkString(", ") + "\n" + rows.mkString("\n"))

    html.index(Some(query))
  }
}
