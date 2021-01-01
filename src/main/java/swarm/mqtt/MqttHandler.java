package swarm.mqtt;

import java.util.*;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.eclipse.paho.client.mqttv3.MqttCallback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;


public class MqttHandler implements MqttCallback {
    private MqttClient client;
    private boolean isConnected = false;

    private final String server;
    private final int port;
    private final String userName, password;

    public Queue<MqttMsg> inQueue = new PriorityQueue<MqttMsg>();
    public Queue<MqttMsg> outQueue = new PriorityQueue<MqttMsg>();

    // Documentation: https://www.eclipse.org/paho/files/javadoc/index.html

    public MqttHandler(String server, int port, String userName, String password) {

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
            MqttConnectOptions connOpts = new MqttConnectOptions();
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
        inQueue.add(new MqttMsg(topic, new String(message.getPayload())));
    }

    public MqttMsg inQueue() {
        return this.inQueue.poll();
    }
    public MqttMsg outQueue() {
        return this.outQueue.poll();
    }
}