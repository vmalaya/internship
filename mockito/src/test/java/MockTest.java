import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockTest {

    @Test
    void should_define_return_value() {
        List mockList = Mockito.mock(List.class);

        when(mockList.get(0)).thenReturn("first");

        assertThat(mockList.get(0)).isEqualTo("first");
    }

    @Test
    void should_define_more_than_one_return(){
        Iterator<String> mockIterator = mock(Iterator.class);

        when(mockIterator.next()).thenReturn("First").thenReturn(" Second");

        assertThat(mockIterator.next().concat(mockIterator.next())).isEqualTo("First Second");
    }

    @Test
    void should_return_value_independent_on_method_parameter() {
        Comparable<Integer> mockComparable = mock(Comparable.class);

        when(mockComparable.compareTo(anyInt())).thenReturn(-1);

        assertThat(mockComparable.compareTo(10)).isEqualTo(-1);
    }

    @Test
    void should_throw_exception() {
        List mockList = mock(List.class);

        when(mockList.get(-1)).thenThrow(new IllegalArgumentException("Index may not be negative."));

        assertThatThrownBy(() -> mockList.get(-1)).isInstanceOf(IllegalArgumentException.class);
    }
}

