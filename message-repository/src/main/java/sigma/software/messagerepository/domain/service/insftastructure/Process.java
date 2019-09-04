package sigma.software.messagerepository.domain.service.insftastructure;

import sigma.software.messagerepository.domain.service.UserService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class Process {

    public Map<Predicate<String[]>, BiFunction<UserService, String[], Result>> getProcessors() {
        return processors;
    }

    private final Map<Predicate<String[]>, BiFunction<UserService, String[], Result>> processors;

    private Process(Map<Predicate<String[]>, BiFunction<UserService, String[], Result>> processors) {
        this.processors = processors;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<Predicate<String[]>, BiFunction<UserService, String[], Result>> processors = new LinkedHashMap<>();

        public Builder on(Predicate<String[]> onFilter,
                          BiFunction<UserService, String[], Result> processAction) {
            processors.put(onFilter, processAction);
            return this;
        }

        public Builder otherwise(BiFunction<UserService, String[], Result> processAction) {
            Objects.requireNonNull(processAction);
            processors.put(args -> true, (userService, args) -> Result.USAGE);
            return this;
        }

        public Process build() {
            if (processors.isEmpty()) otherwise((userService, args) -> Result.USAGE);
            return new Process(processors);
        }
    }
}
