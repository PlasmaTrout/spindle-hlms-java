package com.programmervsworld;

import com.programmervsworld.dao.AlarmDao;
import com.programmervsworld.resources.AlarmResource;
import com.programmervsworld.resources.AlarmsResource;
import com.programmervsworld.websockets.AlarmSocketEndpoint;

import be.tomcools.dropwizard.websocket.WebsocketBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import jakarta.websocket.server.ServerEndpointConfig;

public class SpindleNms2Application extends Application<SpindleNms2Configuration> {

    private final ScanningHibernateBundle<SpindleNms2Configuration> scanningHibernateBundle =
            new ScanningHibernateBundle<SpindleNms2Configuration>("com.programmervsworld.api") {
                @Override
                public PooledDataSourceFactory getDataSourceFactory(SpindleNms2Configuration spindleNms2Configuration) {
                    return spindleNms2Configuration.getDatabase();
                }
            };

    private WebsocketBundle<SpindleNms2Configuration> websocketBundle = new WebsocketBundle<SpindleNms2Configuration>();

    public static void main(final String[] args) throws Exception {
        new SpindleNms2Application().run(args.length > 0 ? args : new String[]{"server", "config.yml"});
    }

    @Override
    public String getName() {
        return "SpindleNms2";
    }

    @Override
    public void initialize(final Bootstrap<SpindleNms2Configuration> bootstrap) {
        bootstrap.addBundle(scanningHibernateBundle);
        bootstrap.addBundle(websocketBundle);
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
    }

    @Override
    public void run(final SpindleNms2Configuration configuration,
                    final Environment environment) {
        var alarmDao = new AlarmDao(scanningHibernateBundle.getSessionFactory());
        environment.jersey().register(new AlarmsResource(alarmDao));
        environment.jersey().register(new AlarmResource(alarmDao));

        ServerEndpointConfig eServerEndpointConfig = ServerEndpointConfig.Builder.create(
            AlarmSocketEndpoint.class,
            "/alarmsocket").build();
        websocketBundle.addEndpoint(eServerEndpointConfig);
    }

}
