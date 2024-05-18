package Other.models;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Arena {
    Properties props;
//        File configFile = new File("src/resources/arena/default_arena.properties");
    File configFile = new File("arena/default_arena.properties");
    FileReader reader;

    public double gridSpace;
    public int gridLength;

    public Arena() throws IOException {
        props = new Properties();
        reader = new FileReader(configFile);
        props.load(reader);

        gridSpace = Double.parseDouble(props.getProperty("arena.default.gridSpace"));
        gridLength = Integer.parseInt(props.getProperty("arena.default.gridLength"));

    }
}
