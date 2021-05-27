package studiplayer.audio;

public class NotPlayableException extends Exception {
    private final String pathname;

    //Ctor 1
    public NotPlayableException(String pathname, String msg) {

        super(msg);
        this.pathname = pathname;

    }

    //Ctor 2
    public NotPlayableException(String pathname, Throwable t) {
        super(t);
        this.pathname = pathname;
    }

    //Ctor 3
    public NotPlayableException(String pathname, String msg, Throwable t) {
        super(msg, t);
        this.pathname = pathname;
    }

    @Override
    public String toString() {
        return this.pathname + ": " + super.toString();
    }

}
