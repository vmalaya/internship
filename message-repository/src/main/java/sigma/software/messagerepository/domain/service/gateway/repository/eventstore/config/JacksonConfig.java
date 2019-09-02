package sigma.software.messagerepository.domain.service.gateway.repository.eventstore.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonConfig {

    public ObjectMapper createObjectMapper() {
        return JsonMapper.builder()
                         .addModules(new JavaTimeModule())
                         .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                         .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
                         .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                         .disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                         .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                         .build();
    }
}
