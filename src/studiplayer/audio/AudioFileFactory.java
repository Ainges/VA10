package studiplayer.audio;

public class AudioFileFactory {
    public static AudioFile getInstance(String pathname) throws NotPlayableException {

        String extension;
        extension = pathname.substring(pathname.lastIndexOf('.'));
        extension = extension.toLowerCase();

        switch (extension) {
            case ".mp3": //fallthrough
            case ".ogg":
                TaggedFile tf1 = new TaggedFile(pathname);
                return tf1;
            case ".wav":
                WavFile wv1 = new WavFile(pathname);
                return wv1;
            default:
                throw new NotPlayableException(pathname, "Error at 'getInstance' methode from 'studiplayer.audio.AudioFileFactory'!");
        }

    }
}
