# java-swarm-node

Java Swarm Node Implementation for the project, Pera-Swarm

This repository is under construction. Please visit back later for information and documentation.

---


## Local Environment Setup

If you need to run this repository on your local environment,
please create a file named *'MQTTSettings.java'* in path, *'./src/main/java/swarm/config/'*
as follows and provide your MQTT broker's configurations.

```java
package swarm.configs;

public class MQTTSettings {

    public final static String server =  "127.0.0.1";
    public final static int port = 1883;
    public final static String userName = "user";
    public final static String password = "password";
    public final static String channel = "v1";

}

```

