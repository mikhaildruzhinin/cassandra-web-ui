import jakarta.servlet.ServletContext
import org.scalatra.LifeCycle
import ru.mikhaildruzhinin.cassandra.web.ui._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext): Unit = {
    context.mount(new CassandraWebUIServlet, "/*")
  }
}
