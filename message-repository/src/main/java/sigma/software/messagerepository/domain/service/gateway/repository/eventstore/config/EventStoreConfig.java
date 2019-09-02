package sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class EventStoreConfig {

    private static Logger log = LogManager.getLogger();
    private Map<String, String> config = new ConcurrentHashMap<>();

    public EventStoreConfig() {
        readDefaultEventStoreProperties();
    }

    private void readDefaultEventStoreProperties() {
        String configPath = "/META-INF/microprofile-config.properties";
        try (InputStream resourceAsStream = EventStoreConfig.class.getResourceAsStream(configPath)) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            // new HashMap<>(properties);
            properties.stringPropertyNames()
                      .forEach(key -> config.put(key, properties.getProperty(key)));

        } catch (Throwable throwable) {
            log.error(throwable.getLocalizedMessage(), throwable);
            throw new RuntimeException(throwable);
        }
    }

    public Path dbBasePath() {
        return config.containsKey("eventStore.dbBasePath")
                ? Paths.get(config.get("eventStore.dbBasePath"))
                : Paths.get("target", "db");
    }
}
