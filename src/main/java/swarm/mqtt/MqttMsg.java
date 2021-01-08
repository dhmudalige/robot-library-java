package swarm.mqtt;

public class MqttMsg implements Comparable<MqttMsg> {

    private static int nextId = 0;

    private int id;
    public String topic, message;
    public String[] topicGroups;
    private String channel;
    public int QoS;

    public MqttMsg(String topic, String message) {
        this(topic, message, 0);
    }

    public MqttMsg(String topic, String message, int QoS) {
        this.id = nextId++;
        this.topic = topic;
        this.message = message;
        this.topicGroups = topic.split("/");
        this.channel = (this.topicGroups.length > 1) ? topicGroups[0] : "";

        // QoS for messages to be published
        this.QoS = QoS;
    }

    public int compareTo(MqttMsg b) {
        return (this.hashCode() == b.hashCode()) ? 0 : 1;
    }
}