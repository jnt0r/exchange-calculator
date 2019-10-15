public class NotEnoughChangeException extends Exception {
    public NotEnoughChangeException() {
        super("Not enough cash left to change money.");
    }
}
