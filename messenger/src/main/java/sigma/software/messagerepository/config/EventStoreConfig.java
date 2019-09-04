package sigma.software.messagerepository.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class EventStoreConfig {

    public static final Path dbBasePath = new EventStoreConfig().dbBasePath();

    private static final Logger log = LogManager.getLogger();

    private Map<String, String> config = new ConcurrentHashMap<>();

    private EventStoreConfig() {
        readDefaultEventStoreProperties();
    }

    private void readDefaultEventStoreProperties() {
        String configPath = "/META-INF/microprofile-config.properties";
        try (InputStream resourceAsStream = EventStoreConfig.class.getResourceAsStream(configPath)) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            // TODO: new HashMap<>(properties);
            properties.stringPropertyNames()
                      .forEach(key -> config.put(key, properties.getProperty(key)));

        } catch (Throwable throwable) {
            log.error(throwable.getLocalizedMessage(), throwable);
            throw new RuntimeException(throwable);
        }
    }

    private Path dbBasePath() {
        return config.containsKey("eventStore.dbBasePath")
                ? Paths.get(config.get("eventStore.dbBasePath"))
                : Paths.get("target", "db");
    }
}
