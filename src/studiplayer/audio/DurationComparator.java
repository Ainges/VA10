package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {

    public int compare(AudioFile af1, AudioFile af2) {
        try {
            Long Obj1 = af1.getDuration();
            Long Obj2 = af2.getDuration();
            return Obj1.compareTo(Obj2);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
