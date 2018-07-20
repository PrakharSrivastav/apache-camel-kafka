package beans;

import org.apache.camel.ServiceStatus;

import java.util.Random;

public class ManagementBean {


    public boolean shouldRouteStop() {
        return new Random().nextBoolean();
    }

    public void startRoute(org.apache.camel.CamelContext ctx) throws Exception {
        if (ctx.getRouteStatus("GenerateInvoices") == ServiceStatus.Suspended)
            ctx.resumeRoute("GenerateInvoices");
    }

    public void stopRoute(org.apache.camel.CamelContext ctx) throws Exception {
        if (ctx.getRouteStatus("GenerateInvoices") == ServiceStatus.Started)
            ctx.suspendRoute("GenerateInvoices");
    }
}
