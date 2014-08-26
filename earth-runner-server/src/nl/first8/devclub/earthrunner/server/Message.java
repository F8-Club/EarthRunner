package nl.first8.devclub.earthrunner.server;

import java.util.Date;

public class Message {

    public enum Type {
        SPEED, COORDS, IP
    };

    private Type type;
    private String message;
    private String coord;
    private Float speed;
    private long time;

    public Message() {
        this("", "");
    }

    public Message(float speed) {
        this.type = Type.SPEED;
        this.setSpeed(speed);
        this.message = "change speed";
        this.coord = null;
        this.time = new Date().getTime();
    }

    public Message(String coord, String message) {
        this.type = Type.COORDS;
        this.setCoord(coord);
        this.message = message;
        this.setSpeed(null);
        this.time = new Date().getTime();
    }

    public Message(String ip) {
        this.type = Type.IP;
        this.message = ip;
        this.setSpeed(null);
        this.time = new Date().getTime();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}