import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeCalculatorTest {
    private ExchangeCalculator excalc = new ExchangeCalculator();

    private Map<Note, Long> available = new HashMap<>();

    @BeforeEach
    public void setup() {
        Arrays.stream(Note.values()).forEach(note -> available.put(note, Long.MAX_VALUE));
    }

    @Test
    @DisplayName("0 = []")
    void returnEmptyArrayWhenChanging0() throws NotEnoughChangeException {
        assertEquals(new ArrayList<>(), excalc.exchange(0., available));
    }

    @Test
    @DisplayName("0.01 = ['1']")
    void return2EuroNoteWhenChanging2() throws NotEnoughChangeException {
        assertEquals(Arrays.asList(Note.ONECENT), excalc.exchange(0.01, available));
    }

    @Test
    @DisplayName("0.03 = ['1', '2']")
    void return2EuroNoteAnd1EuroNoteWhenChanging3() throws NotEnoughChangeException {
        assertEquals(Arrays.asList(Note.TWOCENT, Note.ONECENT), excalc.exchange(0.03, available));
    }

    @Test
    @DisplayName("5 = ['5']")
    void return5EuroNoteWhenChanging5() throws NotEnoughChangeException {
        assertEquals(Arrays.asList(Note.FIVEEURO), excalc.exchange(5., available));
    }

    @Test
    @DisplayName("17.73 = ['...']")
    void returnEuroNotesWhenChanging17EuroAnd73Cent() throws NotEnoughChangeException {
        assertEquals(Arrays.asList(Note.TENEURO, Note.FIVEEURO, Note.TWOEURO, Note.FIFTYCENT, Note.TWENTYCENT, Note.TWOCENT, Note.ONECENT), excalc.exchange(17.73, available));
    }

    @Test
    void throwNotEnoughChangeExceptionWhenNotEnoughNotesAvailableToChangeMoney() {
        Arrays.stream(Note.values()).forEach(note -> available.put(note, 0L));
        assertThrows(NotEnoughChangeException.class, () -> excalc.exchange(20., available));
    }

    @Test
    void throwNotEnoughChangeExceptionWhenNotEnoughNotesAvailableToChangeMoney2() throws NotEnoughChangeException {
        available.put(Note.TENEURO, 0L);
        System.out.println(excalc.exchange(41.5, available));
    }

}
