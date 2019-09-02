package sigma.software.messagerepository.domain.query;

import sigma.software.messagerepository.domain.Message;

import java.util.Comparator;

public class DescendingComparator implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        return -o1.getAt().compareTo(o2.getAt());
    }
}
