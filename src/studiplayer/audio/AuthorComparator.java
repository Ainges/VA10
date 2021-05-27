package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile> {

    @Override
    public int compare(AudioFile af1, AudioFile af2) {
        try {
            return af1.getAuthor().compareTo(af2.getAuthor());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
