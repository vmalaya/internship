package sigma.software.messagerepository;

import java.util.Comparator;

public class DescendingComparator implements Comparator<Message>{
    @Override
    public int compare(Message o1, Message o2) {
        int compare = o1.getAt().compareTo(o2.getAt());
        return -compare;
    }
}
