package nl.first8.devclub.earthrunner.server;

import java.io.IOException;

import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedService(path = "/sync")
public class Sync {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sync.class);

    @Ready
    public void onReady(final AtmosphereResource r) {
    	LOGGER.info("Browser {} connected.", r.uuid());
    	State.addClient(r);
    }

    @Disconnect
    public void onDisconnect(AtmosphereResourceEvent event) {
    	State.removeClient(event.getResource());
        if (event.isCancelled()) {
        	LOGGER.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        } else if (event.isClosedByClient()) {
        	LOGGER.info("Browser {} closed the connection", event.getResource().uuid());
        }
    }

    @org.atmosphere.config.service.Message(encoders = {JacksonEncoder.class}, decoders = {JacksonDecoder.class})
    public Message onMessage(Message message) throws IOException {
    	LOGGER.info("Browser pushed {} and is at latlng ", message.getMessage(), message.getCoord());
        return message;
    }

}