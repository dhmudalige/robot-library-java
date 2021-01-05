package swarm.robot.helpers;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import swarm.mqtt.MqttHandler;

public class Coordinate {

    private double x, y, heading;
    private MqttHandler mqtt;
    private int robotId;

    public Coordinate(int robotId, double x, double y, double heading, MqttHandler mqtt) {
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.robotId = robotId;
        this.mqtt = mqtt;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getHeading() {
        return this.heading;
    }

    public double getHeadingRad() {
        return (double) ((this.heading / 360) * 2 * Math.PI);
    }

    public void setHeading(double heading) {
        this.heading = getNormalizedHeading(heading); // heading between [180,-180)
    }

    public void setHeadingRad(double heading) {
        setHeading((heading * 180) / Math.PI);
    }

    public void setCoordinate(double x, double y) {
        setX(x);
        setY(y);
    }

    public void setCoordinate(double x, double y, double heading) {
        setX(x);
        setY(y);
        setHeading(heading);
    }

    public void print() {
        System.out.println(this.toString());
    }

    public String toString() {
        return "x:" + round2(x) + " y:" + round2(y) + " heading:" + round2(heading);
    }

    public void publishCoordinate() {
        JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("id", robotId);
        obj.put("x", round2(x));
        obj.put("y", round2(y));
        obj.put("heading", round2(heading));

        jsonArray.add(obj);
        mqtt.publish("localization/info", jsonArray.toJSONString());
    }

    private double round2(double x) {
        return Math.round(x * 100) / 100.0;
    }

    private double getNormalizedHeading(double heading) {
        double h = (heading + 180) % 360;
        if (h <= 0) h += 360;
        h = h - 180;
        return h;
    }

}