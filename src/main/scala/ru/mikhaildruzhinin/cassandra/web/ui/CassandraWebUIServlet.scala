package ru.mikhaildruzhinin.cassandra.web.ui

import org.scalatra._

class CassandraWebUIServlet extends ScalatraServlet {
  get("/") {
    "Hello world!"
  }
}
