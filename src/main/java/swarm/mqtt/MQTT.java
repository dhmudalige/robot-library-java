package swarm.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

public class MQTT implements MqttCallback {

    private MqttClient client;
    private MqttConnectOptions connOpts;
    private boolean isConnected = false;

    // Documentation: https://www.eclipse.org/paho/files/javadoc/index.html

    public MQTT(String server, int port, String userName, String password) {

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
        System.out.println("MQTT "+ topic + ">> " +  new String(message.getPayload()));
    }

}
