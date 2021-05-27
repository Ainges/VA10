package studiplayer.audio;

import java.io.File;

public abstract class AudioFile {

    protected String author;
    protected String title;
    //protected String album;
    protected long duration;
    private String fileName;
    private String pathname;
    private boolean isWindowsPath;
    private boolean isAbsolutPath;
    private boolean isWindowsSystem;

//c-tors

    public AudioFile(String path) throws NotPlayableException { //Default Ctor
        parsePathname(path);

        File test = new File(getPathname());

        if (!test.canRead()) {
            throw new NotPlayableException(path, "Error at studiplayer.audio.AudioFile Ctor");
        }


        if (this.fileName == null) {
            this.fileName = path;
        }
        parseFilename(this.fileName);


    }

    public AudioFile() {  //No-args-Ctor
        this.fileName = " ";
        this.pathname = "";
        this.author = "";
        this.title = "";
    }

    //abstract methods
    public abstract void play() throws NotPlayableException;

    public abstract void togglePause();

    public abstract void stop();

    public abstract String getFormattedDuration();

    public abstract String getFormattedPosition();

    public abstract String[] fields();

    //methods
    public void parsePathname(String path) {

        if (path.isBlank()) {
            this.pathname = path;
            this.fileName = path;
            return;
        }

        isWindowsSystem();
        isWindowsPath(path);
        path = clearPath(path);


        if (isWindowsSystem) {
            while (path.contains("/")) {
                path = path.replace("/", "\\");
            }
        }
        if (!isWindowsSystem) {

            path = path.replace(":", "");
            while (path.contains("\\")) {
                path = path.replace("\\", "/");
            }

        }


        if (path.equals("-")) { //
            this.fileName = "-";
            this.pathname = "-";
            return;
        }

        if (this.isAbsolutPath) {

            switch (isWindowsSystem + "-" + isWindowsPath) {// Fancy way of switch case
                case "true-true":
                    //System.out.println("Win & Win passt!");
                    this.pathname = path;
                    break;
                case "false-false":
                    //System.out.println("Unix & Unix passt!");
                    path = "/" + path;
                    this.pathname = path;
                    break;
                case "false-true":
                    //System.out.println("UnixSys & WinPath!!! ");
                    winToUnix(path);
                    break;
                case "true-false":
                    //System.out.println("WinSys & UnixPath!!!");
                    UnixToWin(path);
                    break;
            }
        } else this.pathname = path;
        this.fileName = separateFileName(path);


    } //System detection & Normalisation of letter if necessary + clearPath(trim +

    private String clearPath(String inputPath) {
        String clearedPath;

        char separatorChar = System.getProperty("file.separator").charAt(0); //Get file.separator
        String separatorString = separatorChar + ""; //Convert to String

        while (inputPath.contains(separatorString + separatorString)) {
            inputPath = inputPath.replace(separatorString + separatorString, separatorString + "");
        }
        if (!isWindowsPath && isWindowsSystem) {
            while (inputPath.contains("/")) {
                inputPath = inputPath.replace("/", "\\");
            }
            while (inputPath.contains("\\\\")) { // to be safe
                inputPath = inputPath.replace("\\\\", "\\");
            }
        } else if (isWindowsPath && !isWindowsSystem) {
            while (inputPath.contains("\\")) {
                inputPath = inputPath.replace("\\", "/"); // Warum geht das gleiche nicht mit .replaceAll?
            }
            while (inputPath.contains("//")) { // to be safe
                inputPath = inputPath.replace("//", "/");
            }
        }
        clearedPath = inputPath;
        return clearedPath;

    }

    public void parseFilename(String fileName) { //Sets filename & author
        if (fileName.isBlank()) {
            this.title = "";
            this.author = "";
            return;
        }

        if (fileName.equals("-")) {
            this.title = "-";
            this.author = "";
            return;
        }

        int startOfExtension = fileName.lastIndexOf('.');
        if (startOfExtension == -1) {
            startOfExtension = fileName.length();
        }

        //this.extension = fileName.substring(startOfExtension); //new in VA08

        if (!fileName.contains(" - ")) { //To get rid of strange inputs
            if (!fileName.contains("- ")) {
                this.title = fileName.substring(0, startOfExtension);
                this.author = "";
                return;
            }
            this.title = fileName.substring(fileName.lastIndexOf(System.getProperty("file.separator")));
            this.author = "";
            return;

        }
        if (fileName.charAt(0) == '.') {
            this.author = "";
            this.title = "";
            return;
        }

        int posOfSeparator = fileName.indexOf(" - ");
        String author = fileName.substring(fileName.lastIndexOf(System.getProperty("file.separator")) + 1, posOfSeparator);


        String title = fileName.substring(posOfSeparator + 3, startOfExtension);
        if (title.isEmpty()) {
            title = fileName.substring(fileName.lastIndexOf(System.getProperty("file.separator")) + 1, startOfExtension);
        }
//        if(title.contains(".")) {
//            startOfExtension = title.lastIndexOf('.');
//            title = title.substring(0, startOfExtension);
//        }
        title = title.trim();

        this.author = author.trim();
        this.title = title.trim();

    }


    private String separateFileName(String path) {
        char separator = 'f';
        switch (isWindowsSystem + "") {
            case "true":
                separator = '\\';
                break;
            case "false":
                separator = '/';
                break;
            default:
                System.err.println("FEHLER: Fehler beim Dateinnamen parsen!!!");
        }
        int lastPosOfSeparator = path.lastIndexOf(separator); //defines Start of the filename
        String filename = path.substring(lastPosOfSeparator + 1); //separate the filename

        return filename;
    }

    private void UnixToWin(String path) { //transforms only absolut UnixFiles to WindowsFile formats

        path = path.replaceFirst("/", "");
        path = path.replaceFirst("/", ":");
        this.pathname = path;
    }

    private void winToUnix(String path) { //transforms only absolut WindowsFiles to UnixFile formats
        path = path.replaceFirst(":", "/");
        path = "/" + path;
        while (path.contains("//")) {
            path = path.replace("//", "/");
        }
        this.pathname = path;
    }

    private void isWindowsPath(String path) { //
        if ((path.charAt(0) >= (byte) 'A' && path.charAt(0) <= (byte) 'Z' || path.charAt(0) >= (byte) 'a'
                && path.charAt(0) <= (byte) 'z')) {
            if (path.charAt(1) == ':' || path.charAt(1) == '/') {
                this.isAbsolutPath = true;
            }
        }
        if (path.contains("\\")) {
            this.isWindowsPath = true;
        }
        if (path.contains("/")) {
            this.isWindowsPath = false;
        }
    }

    private void isWindowsSystem() {
        this.isWindowsSystem = System.getProperty("file.separator").charAt(0) == '\\';
    }


    //getter & setter
    public String getPathname() {
        return this.pathname;
    }

    public String getFilename() {
        return this.fileName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {

        if (!getAuthor().isEmpty()) {
            return getAuthor() + " - " + getTitle();
        } else {
            return getTitle();
        }
    }


}
