package swarm.configs;

import org.junit.Assert;
import org.junit.Test;

public class MQTTSettingsTest {

    @Test
    public void serverIPTest() {
        Assert.assertNotSame("Server IP is not configured", "127.0.0.1",MQTTSettings.server);
    }

    @Test
    public void serverCredentialTest() {
        Assert.assertNotSame("MQTT Server username is not configured", "",MQTTSettings.userName);
        Assert.assertNotSame("MQTT Server password is not configured", "",MQTTSettings.password);
    }

    @Test
    public void mqttChannelTest() {
        Assert.assertNotSame("MQTT Channel is not configured", "",MQTTSettings.channel);
    }
}