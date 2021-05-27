package studiplayer.audio;

import java.util.Map;

public class TaggedFile extends SampledFile {

    private String album; //-> ok has to be here got it


    //Ctors
    public TaggedFile() {
        super();
    }

    public TaggedFile(String s) throws NotPlayableException {
        super(s);
        readAndStoreTags(super.getPathname());
    }
    //methods
    public String getAlbum() {
        if (this.album == null) {
            return "";
        } else {
            return this.album;
        }
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void readAndStoreTags(String str) throws NotPlayableException {
        try {
            Map<String, Object> tag_map = studiplayer.basic.TagReader.readTags(str);
            if (tag_map.get("duration") != null) {
                super.duration = (long) tag_map.get("duration");
            }

            String tempAlbum = (String) tag_map.get("album");
            if (tempAlbum != null) {
                album = tempAlbum.trim();
            }

            String tempAuthor = (String) tag_map.get("author");
            if (tempAuthor != null) {
                super.author = tempAuthor.trim();
            }

            String tempTitle = (String) tag_map.get("title");
            if (tempTitle != null) {
                super.title = tempTitle.trim();
            }
        } catch (Exception e) {
            throw new NotPlayableException(getPathname(), "Error at 'readAndStoreTags'", e);
        }

    }

    @Override
    public String[] fields() {
        String[] strArray = new String[]{super.getAuthor(), super.getTitle(), getAlbum(), super.getFormattedDuration()};
        for (int i = 0; i < 4; i++) {
            if (strArray[i] == null) {
                strArray[i] = "";
            }
        }
        return strArray;
    }

    @Override
    public String toString() {

        String tmp;
        if (getAlbum().isEmpty()) {
            tmp = super.getAuthor() + " - " + super.getTitle() + " - " + getFormattedDuration();
            if (super.getAuthor().isEmpty()) {
                tmp = super.getTitle() + " - " + getFormattedDuration();
            }
        } else {
            tmp = super.getAuthor() + " - " + super.getTitle() + " - " + getAlbum() + " - " + getFormattedDuration();
            if (super.getAuthor().isBlank()) {
                tmp = super.getTitle() + " - " + getAlbum() + " - " + getFormattedDuration();
            }
        }
        return tmp;
    }
}
