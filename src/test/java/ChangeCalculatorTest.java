import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

enum Note {
    ONECENT(0.01),
    TWOCENT(0.02),
    FIVECENT(0.05),
    TENCENT(0.1),
    TWENTYCENT(0.2),
    FIFTYCENT(0.5),
    ONEEURO(1.),
    TWOEURO(2.0),
    FIVEEURO(5.0),
    TENEURO(10.0);

    private long value;

    private Note(double value) {
        this.value = (long) (value*100);
    }

    public long getValue() {
        return value;
    }
}
public class ChangeCalculatorTest {

    private Map<Note, Long> available = new HashMap<>();

    @BeforeEach
    public void setup() {
        Arrays.stream(Note.values()).forEach(note -> available.put(note, Long.MAX_VALUE));
    }

    @Test
    @DisplayName("0 = []")
    void returnEmptyArrayWhenChanging0() throws NotEnoughChangeException {
        assertEquals(new ArrayList<>(), exchange(0., available));
    }

    @Test
    @DisplayName("0.01 = ['1']")
    void return2EuroNoteWhenChanging2() throws NotEnoughChangeException {
        assertEquals(Arrays.asList(Note.ONECENT), exchange(0.01, available));
    }

    @Test
    @DisplayName("0.03 = ['1', '2']")
    void return2EuroNoteAnd1EuroNoteWhenChanging3() throws NotEnoughChangeException {
        assertEquals(Arrays.asList(Note.TWOCENT, Note.ONECENT), exchange(0.03, available));
    }

    @Test
    @DisplayName("5 = ['5']")
    void return5EuroNoteWhenChanging5() throws NotEnoughChangeException {
        assertEquals(Arrays.asList(Note.FIVEEURO), exchange(5., available));
    }

    @Test
    @DisplayName("17.73 = ['...']")
    void returnEuroNotesWhenChanging17EuroAnd73Cent() throws NotEnoughChangeException {
        assertEquals(Arrays.asList(Note.TENEURO, Note.FIVEEURO, Note.TWOEURO, Note.FIFTYCENT, Note.TWENTYCENT, Note.TWOCENT, Note.ONECENT), exchange(17.73, available));
    }

    @Test
    void throwNotEnoughChangeExceptionWhenNotEnoughNotesAvailableToChangeMoney() {
        Arrays.stream(Note.values()).forEach(note -> available.put(note, 0L));
        assertThrows(NotEnoughChangeException.class, () -> exchange(20., available));
    }

    @Test
    void throwNotEnoughChangeExceptionWhenNotEnoughNotesAvailableToChangeMoney2() {
        Arrays.stream(Note.values()).forEach(note -> available.put(note, 1L));
        System.out.println(available);
        assertThrows(NotEnoughChangeException.class, () -> exchange(4., available));
    }

    private List<Note> exchange(double change, Map<Note, Long> available_notes) throws NotEnoughChangeException {
        List<Note> change_notes = new ArrayList<>();

        long lchange = (long) (change*100);
        int i = Note.values().length - 1;

        while (lchange > 0L && i >= 0) {
            Note current_note = Note.values()[i];

            while ((available_notes.get(current_note) > 0L) && ((lchange - current_note.getValue()) >= 0L)) {
                lchange -= current_note.getValue();
                available_notes.put(current_note, available_notes.get(current_note) - 1L);
                change_notes.add(current_note);
            }
            i--;
        }

        if (lchange > 0L) {
            throw new NotEnoughChangeException();
        }

        return change_notes;
    }
}
