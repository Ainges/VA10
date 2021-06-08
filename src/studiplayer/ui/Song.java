package studiplayer.ui;

import studiplayer.audio.AudioFile;

public class Song {
	private final int index;
	private final AudioFile af;
	private final String interpret;
    private final String titel;
    private final String album;
    private final String laenge;

	public Song(int index, AudioFile af, String interpret, String titel, String album, String laenge) {
		this.index = index;
		this.af = af;
		this.interpret = interpret;
		this.titel = titel;
		this.album = album;
		this.laenge = laenge;
	}

	public int getIndex() {
		return index;
	}
	
	public AudioFile getAudioFile() {
		return af;
	}

	public String getInterpret() {
		return interpret;
	}

	public String getTitel() {
		return titel;
	}

	public String getAlbum() {
		return album;
	}

	public String getLaenge() {
		return laenge;
	}
	
}	
