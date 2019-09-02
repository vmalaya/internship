package sigma.software.messagerepository.domain.event.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import sigma.software.messagerepository.domain.event.*;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = false
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "FriendRequestAcceptedEvent", value = FriendRequestAcceptedEvent.class),
        @JsonSubTypes.Type(name = "FriendRequestDeclinedEvent", value = FriendRequestDeclinedEvent.class),
        @JsonSubTypes.Type(name = "FriendRequestSentEvent", value = FriendRequestSentEvent.class),
        @JsonSubTypes.Type(name = "MessageReceivedEvent", value = MessageReceivedEvent.class),
        @JsonSubTypes.Type(name = "MessageSentEvent", value = MessageSentEvent.class),
        @JsonSubTypes.Type(name = "UserCreatedEvent", value = UserCreatedEvent.class),
})
public interface DomainEvent {
    UUID getAggregateId();
}
