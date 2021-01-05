package swarm.mqtt;
import java.util.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;


class Message implements Comparable<Message>{
    int id;
    String topic, message;
    public Message(int id, String topic, String message) {
        this.id = id;
        this.topic = topic;
        this.message = message;
    }
    public int compareTo(Message b) {
        if(id>b.id){
            return 1;
        }else if(id<b.id){
            return -1;
        }else{
            return 0;
        }
    }
}

public class MQTT implements MqttCallback {
    Queue<Message> queue=new PriorityQueue<Message>();
    private MqttClient client;
    private MqttConnectOptions connOpts;
    private boolean isConnected = false;

    private String server;
    private int port;
    private String userName, password;

    // Documentation: https://www.eclipse.org/paho/files/javadoc/index.html

    public MQTT(String server, int port, String userName, String password) {

        this.server = server;
        this.port = port;
        this.userName = userName;
        this.password = password;

        connect();
    }

    public void connect() {

        String broker = "tcp://" + server + ":" + port;
        String clientId = "client_" + Math.random() * 1000;
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            client = new MqttClient(broker, clientId, persistence);
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(userName);
            connOpts.setPassword(password.toCharArray());
            connOpts.setKeepAliveInterval(60);
            connOpts.setAutomaticReconnect(true);
            client.connect(connOpts);
            isConnected = true;

            client.setCallback(this);

            System.out.println("MQTT: Connected");

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void publish(String topic, String body) {
        publish(topic, body, 0);
    }

    public void publish(String topic, String body, int qos) {
        if (isConnected) {

            MqttMessage message = new MqttMessage(body.getBytes());
            message.setQos(qos);

            try {
                this.client.publish(topic, message);
                System.out.println("Message published");

            } catch (MqttException me) {
                printMQTTError(me);
            }
        } else {
            // Not connected
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);

        } catch (MqttException me) {
            printMQTTError(me);
        }
    }

    private void printMQTTError(MqttException me) {
        System.out.println("reason " + me.getReasonCode());
        System.out.println("msg " + me.getMessage());
        System.out.println("loc " + me.getLocalizedMessage());
        System.out.println("cause " + me.getCause());
        System.out.println("excep " + me);
        me.printStackTrace();
    }


    @Override
    public void connectionLost(Throwable t) {
        System.out.println("Connection lost!");
        // code to reconnect to the broker would go here if desired
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Delivery completed!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("MQTT " + topic + ">> " + new String(message.getPayload()));

        Message m1=new Message(1,"topic1","message1");
        Message m2=new Message(2,"topic2","message2");
        Message m3=new Message(3,"topic3","message3");

        queue.add(m1);
        queue.add(m2);
        queue.add(m3);

        execute();
    }

    public void execute(){
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            Message m = queue.poll();
            if (m == null) {
                break;
            }
            mqttExecute(m.topic,m.message);
            System.out.println(m.id+" "+m.topic+" "+m.message);
        }
    }

    public void mqttExecute(String topic,String message){
        if(topic=="topic1"){
            System.out.println(message);
        }
    }
}