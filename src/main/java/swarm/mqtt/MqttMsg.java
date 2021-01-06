package swarm.mqtt;

public class MqttMsg implements Comparable<MqttMsg> {

    private static int nextId=0;

    private int id;
    public String topic, message;
    public String[] topicGroups;
    public int QoS;

    // For messages to be published
    public MqttMsg(String topic, String message, int QoS) {
        this.id = nextId++;
        this.topic = topic;
        this.message = message;
        // this.topicGroups = topic.split("/");
        this.QoS = QoS;
    }

    public MqttMsg(String topic, String message) {
        this.id = nextId++;
        this.topic = topic;
        this.message = message;
        this.topicGroups = topic.split("/");
        this.QoS = 0;
    }

    public int compareTo(MqttMsg b) {
        return (this.hashCode() == b.hashCode()) ? 0 : 1;
    }
}