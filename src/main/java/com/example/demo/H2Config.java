package com.example.demo;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

import static org.h2.tools.Server.createWebServer;

@Component
public class H2Config {
    Logger log = LoggerFactory.getLogger(H2Config.class);
    private Server webServer;

    @Value("${h2-server.port}")
    private Integer h2ConsolePort;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {
        log.info("starting h2 console at port {}", h2ConsolePort);
        webServer = createWebServer("-webPort",
                h2ConsolePort.toString(),
                "-tcpAllowOthers").start();
        log.info("access h2 console at url {}", webServer.getURL());
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port {}", h2ConsolePort);
        webServer.stop();
    }
}