import com.datastax.oss.driver.api.core.CqlSession
import jakarta.servlet.ServletContext
import org.scalatra.LifeCycle
import ru.mikhaildruzhinin.cassandra.web.ui._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.jdk.FutureConverters._

//noinspection ScalaUnusedSymbol
class ScalatraBootstrap extends LifeCycle {

  val eventualCqlSession: Future[CqlSession] = CqlSession.builder().buildAsync().asScala

  override def init(context: ServletContext): Unit = {
    context.mount(new CassandraWebUIServlet(eventualCqlSession), "/*")
  }

  override def destroy(context: ServletContext): Unit = {
    super.destroy(context)
    eventualCqlSession.map(_.closeAsync())
  }
}
