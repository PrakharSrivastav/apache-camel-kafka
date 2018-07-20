package routes;

import beans.ManagementBean;
import org.apache.camel.builder.RouteBuilder;

public class MonitoringRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(Exception.class).log(exceptionMessage().toString());

        from("timer:time?period=10000")
                .choice()
                .when().simple("${bean:manageRouteBean?method=shouldRouteStop}")
                .log("Route Should Stop")
                .bean(ManagementBean.class, "stopRoute(*)")
                .otherwise()
                .log("Route Should Start")
                .bean(ManagementBean.class, "startRoute(*)")
                .end();
    }
}
