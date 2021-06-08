package studiplayer.audio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class PlayList extends LinkedList<AudioFile> {

    private int current;
    private boolean randomOrder;

    public PlayList() {
        this.current = 0;
        this.randomOrder = false;
    }

    public boolean isRandomOrder() {
        return randomOrder;
    }

    public PlayList(String pathname) {
        new PlayList();
        loadFromM3U(pathname);
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public AudioFile getCurrentAudioFile() {

        if (this.size() == 0) {
            return null;
        }
        try {
            return this.get(current);
        } catch (Exception e) {
            return null;
        }
    }

    public void changeCurrent() {

        int maxIndex = this.size() - 1;

        if (this.current < maxIndex) {
            this.current++;
        } else if (this.current == maxIndex) {
            this.current = 0;
            if (isRandomOrder()) {
                setRandomOrder(true);
            }
        } else if (this.current > maxIndex) {
            this.current = 0;
        }

    }

    public void setRandomOrder(boolean randomOrder) {
        if (randomOrder) { // = if randomOrder == true
            this.randomOrder = true;
            Collections.shuffle(this);
        } else if (!randomOrder) {
            this.randomOrder = false;
        }


    }

    public void saveAsM3U(String pathname) {
        FileWriter writer = null;
        String linesep = System.getProperty("line.separator");
        try {
            writer = new FileWriter(pathname);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); //-> from the I-Net
            writer.write("#  || name: Hubertus Seitz | Systemtime: " + dtf.format(LocalDateTime.now()) + " ||" + linesep + linesep); //dtf.forma... -> from the I-Net
            for (AudioFile af : this) {
                writer.write(af.getPathname() + linesep);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to write Playlist '" + pathname + " to file");
        } finally {
            try {
                writer.close(); //super important
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    public void loadFromM3U(String pathname) {

        if (pathname == null || pathname.isBlank()){
            return;
        }
        this.clear();
        Scanner scanner = null;
        String line;
        try {
            scanner = new Scanner(new File(pathname));
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();


                if (line.startsWith("#") || line.isBlank()) {
                    continue;
                } else {
                    try {
                        this.add(AudioFileFactory.getInstance(line));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            try {
                scanner.close();
            } catch (Exception e) {
            }
        }
    }

    public void sort(SortCriterion order) {
        switch (order){
            case AUTHOR: Collections.sort(this,new AuthorComparator()); break;
            case TITLE: Collections.sort(this, new TitleComparator());break;
            case ALBUM: Collections.sort(this, new AlbumComparator());break;
            case DURATION: Collections.sort(this, new DurationComparator()); break;
        }
    }
}