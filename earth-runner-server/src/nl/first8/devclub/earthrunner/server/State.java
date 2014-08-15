package nl.first8.devclub.earthrunner.server;

import java.util.HashSet;
import java.util.Set;

import org.atmosphere.cpr.AtmosphereResource;

public class State {

	private static final Object SPEED_LOCK = new Object();
	private static final Object SESSIONS_LOCK = new Object();
	private static float speed = 0f;
	private static final Set<AtmosphereResource> sessions = new HashSet<AtmosphereResource>();
	
	private static final JacksonEncoder encoder = new JacksonEncoder();
	
	public static float getSpeed(String uuid) {
		// TODO keep speed specific per browser uuid
		synchronized(SPEED_LOCK) {
			return State.speed;
		}
	}

	public static void setSpeed(String uuid, float speed) {
		// TODO keep speed specific per browser uuid
		synchronized(SPEED_LOCK) {
			State.speed = speed;
		}
		
		Message m = new Message(speed);
		// for now, broadcast to all UUID's
		for (AtmosphereResource r: sessions) {
			r.getBroadcaster().broadcast(encoder.encode(m));
		}
	}

	public static void addClient(AtmosphereResource r) {
		synchronized(SESSIONS_LOCK) {
			sessions.add(r);
		}
	}

	public static void removeClient(AtmosphereResource r) {
		synchronized(SESSIONS_LOCK) {
			sessions.remove(r);
		}
	}

}
