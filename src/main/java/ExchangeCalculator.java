import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExchangeCalculator {
    public List<Note> exchange(double change, Map<Note, Long> available_notes) throws NotEnoughChangeException {
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
