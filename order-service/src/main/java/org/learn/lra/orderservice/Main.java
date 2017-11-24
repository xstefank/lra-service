package org.learn.lra.orderservice;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.undertow.WARArchive;

public class Main {

    public static void main(String[] args) throws Exception {
        // Instantiate the container
        Swarm swarm = new Swarm(args);

        // Create one or more deployments
        WARArchive deployment = ShrinkWrap.create(WARArchive.class);

        // Add resource to deployment
        deployment.addPackage(Main.class.getPackage());
        deployment.addAllDependencies();

        // Add Web resources
        deployment.addAsWebResource(
                new ClassLoaderAsset("src/main/webapp/index.html", Main.class.getClassLoader()), "src/main/webapp/index.html");
        deployment.addAsWebInfResource(
                new ClassLoaderAsset("src/main/webapp/WEB-INF/beans.xml", Main.class.getClassLoader()), "beans.xml");


        swarm.start().deploy(deployment);

    }
}
