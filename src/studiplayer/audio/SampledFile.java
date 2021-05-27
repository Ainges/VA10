package studiplayer.audio;

public abstract class SampledFile extends AudioFile {

    SampledFile() {
        super();
    }

    SampledFile(String s) throws NotPlayableException {
        super(s);
    }

    public void play() throws NotPlayableException {
        try {
            studiplayer.basic.BasicPlayer.play(getPathname());
        } catch (RuntimeException e) {
            throw new NotPlayableException(getPathname(), "Error at methode 'play' from studiplayer.audio.SampledFile ", e);
        }
    }


    public void togglePause() {
        studiplayer.basic.BasicPlayer.togglePause();
    }


    public void stop() {
        studiplayer.basic.BasicPlayer.stop();
    }


    public String getFormattedDuration() {
        long temp = super.getDuration();
        return timeFormatter(temp);
    }


    public String getFormattedPosition() {
        return timeFormatter(studiplayer.basic.BasicPlayer.getPosition());
    }

    public static String timeFormatter(Long microtime) {

        long seconds;
        long minutes;

        if (microtime < 0L) {
            throw new RuntimeException("Negative time value provided");
        }
        if (microtime > 5999999999L) {
            throw new RuntimeException("Time value exceeds allowed format");
        }

        //microseconds in seconds:
        seconds = (microtime / (10 * 10 * 10 * 10 * 10 * 10));
        minutes = (seconds / 60);
        seconds = seconds % 60;
        seconds = (int) seconds;
        minutes = (int) minutes;

        String secondsFormatted = String.format("%02d", seconds);
        String minutesFormatted = String.format("%02d", minutes);

        return minutesFormatted + ":" + secondsFormatted;
    }
}
