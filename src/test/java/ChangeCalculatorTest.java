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
    THIRTYCENT(0.3),
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

    private Map<Long, String> notes = new HashMap<>();

    @Test
    @DisplayName("0 = []")
    void returnEmptyArrayWhenChanging0() {
        assertEquals(new ArrayList<>(), exchange(0., new HashMap<>()));
    }

    @Test
    @DisplayName("0.01 = ['1']")
    void return2EuroNoteWhenChanging2() {
        assertEquals(Arrays.asList(Note.ONECENT), exchange(0.01, new HashMap<>()));
    }

    @Test
    @DisplayName("0.03 = ['1', '2']")
    void return2EuroNoteAnd1EuroNoteWhenChanging3() {
        assertEquals(Arrays.asList(Note.TWOCENT, Note.ONECENT), exchange(0.03, new HashMap<>()));
    }

    @Test
    @DisplayName("5 = ['5']")
    void return5EuroNoteWhenChanging5() {
        assertEquals(Arrays.asList(Note.FIVEEURO), exchange(5., new HashMap<>()));
    }

    @Test
    @DisplayName("17.73 = ['...']")
    void returnEuroNotesWhenChanging17EuroAnd73Cent() {
        assertEquals(Arrays.asList(Note.TENEURO, Note.FIVEEURO, Note.TWOEURO, Note.FIFTYCENT, Note.TWENTYCENT, Note.TWOCENT, Note.ONECENT), exchange(17.73, new HashMap<>()));
    }

    @Test
    void throwNotEnoughChangeExceptionWhenNotEnoughNotesAvailableToChangeMoney() {
        assertThrows(NotEnoughChangeException.class, () -> exchange(20., new HashMap<>()));
    }

    private List<Note> exchange(double change, Map<Note, Long> available_notes) {
        List<Note> change_notes = new ArrayList<>();

        long lchange = (long) (change*100);

        int i = Note.values().length - 1;

        while (lchange > 0L) {
            Note current_note = Note.values()[i];

            while ((lchange - current_note.getValue()) >= 0L) {
                lchange -= current_note.getValue();
                change_notes.add(current_note);
            }
            i--;
        }

        return change_notes;
    }
}
