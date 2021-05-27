package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator <AudioFile> { //TODO: Implement it less stupid
    public int compare(AudioFile af1, AudioFile af2) {
        try {
            boolean af1isNotTaggedFile = !(af1 instanceof TaggedFile);
            boolean af2isNotTaggedFile = !(af2 instanceof TaggedFile);

            if (af1isNotTaggedFile && af2isNotTaggedFile) {
                return 0;
            }
            else if (!af1isNotTaggedFile && af2isNotTaggedFile){
                return 1;
            }
            else if(af1isNotTaggedFile && !af2isNotTaggedFile){
                return -1;
            }
            else {
                TaggedFile tf1 = (TaggedFile) af1;
                TaggedFile tf2 = (TaggedFile) af2;

                return tf1.getAlbum().compareTo(tf2.getAlbum());
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
            return 0;


        }
    }
}
