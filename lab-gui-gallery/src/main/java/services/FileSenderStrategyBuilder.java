package services;

public class FileSenderStrategyBuilder {

    public static IFileSender Build(String extension, IRetrofitService retrofitService) {
        return extension.equals(File.ZIP) 
            ? new ZipFileSender(retrofitService)
            : new PhotoFileSender(retrofitService);
    }
}
