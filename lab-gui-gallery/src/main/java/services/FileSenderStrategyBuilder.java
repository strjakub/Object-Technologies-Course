package services;

public class FileSenderStrategyBuilder {

    public static IFileSender Build(String extension) {
        return extension.equals(File.ZIP) 
            ? new ZipFileSender()
            : new PhotoFileSender();
    }
}
