package demo

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers._
import akka.util.duration._
import bootstrap._
import assertions._

class SimulationtoTaobao extends Simulation {
  // your code starts here
  val scn = scenario("My First Scenario")
    .exec(http("My Page")
    .get("http://www.taobao.com/index_global.php"))

  setUp(scn.users(5))
  // your code ends here
}
