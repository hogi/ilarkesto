package ilarkesto.id;

public class CountingIdGenerator implements IdGenerator {

    private int count;

    public synchronized String generateId() {
        return prefix + (++count);
    }

    // --- dependencies ---

    private String prefix;

    public CountingIdGenerator(String prefix) {
        this.prefix = prefix;
    }
}
