package studiplayer.audio;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {

    public WavFile() {
        super();
    }


    @Override
    public String[] fields() {
        String[] strArray = new String[]{super.getAuthor(), super.getTitle(),"", super.getFormattedDuration()};
        for (int i = 0; i < 3; i++) {
            if (strArray[i] == null) {
                strArray[i] = "";
            }
        }
        return strArray;
    }

    public WavFile(String s) throws NotPlayableException {
        super(s);
        readAndSetDurationFromFile(super.getPathname());
    }

    public static long computeDuration(long numberOfFrames, float frameRate) {

        long DurationInMicroSeconds;
        DurationInMicroSeconds = (long) (numberOfFrames / frameRate * 1000000);
        return DurationInMicroSeconds;
    }

    public void readAndSetDurationFromFile(String pathname) throws NotPlayableException {
        try {
            WavParamReader.readParams(pathname);
            super.duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());
        } catch (Exception e) {
            throw new NotPlayableException(pathname, "Error at readAndSetDurationFromFile", e);
        }
    }

    @Override
    public String toString() {

        return super.toString() + " - " + super.getFormattedDuration();
    }
}
