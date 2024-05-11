package ru.mikhaildruzhinin.cassandra.web.ui

import com.datastax.oss.driver.api.core.CqlSession
import org.scalatra._
import org.scalatra.forms._
import org.scalatra.i18n.I18nSupport
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.CollectionConverters._
import scala.jdk.FutureConverters._

class CassandraWebUIServlet(eventualCqlSession: Future[CqlSession]) extends ScalatraServlet
  with FormSupport with I18nSupport with FutureSupport {

  override protected implicit def executor: ExecutionContext = ExecutionContext.global

  private val logger: Logger =  LoggerFactory.getLogger(getClass)

  get("/") {
    new AsyncResult() { override val is: Future[_] =
      Future(html.index(None, None, false))
    }
  }

  post("/") {
    new AsyncResult() { override val is: Future[_] =
      for {
        query <- Future.successful(params("query"))
        _ = logger.info(query)
        cqlSession <- eventualCqlSession
        resultSet <- cqlSession.executeAsync(query).asScala // "describe tables"
        columnDefinitions = resultSet.getColumnDefinitions.iterator().asScala
        columnNames = columnDefinitions.map(_.getName.asCql(true))
        rows = resultSet.currentPage().asScala.map { row =>
          columnDefinitions.map(col => row.get(col.getName, classOf[String])).mkString(", ")
        }
        result: String = columnNames.mkString(", ") + "\n" + rows.mkString("\n")
      } yield html.index(Some(query), Some(result), resultSet.hasMorePages)
    }
  }
}
