package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {
    @Override
    public int compare(AudioFile af1, AudioFile af2) {
        try {
            return af1.getTitle().compareTo(af2.getTitle());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;

        }
    }
}
