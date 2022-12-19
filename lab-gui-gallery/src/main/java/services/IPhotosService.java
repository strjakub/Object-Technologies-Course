package services;

public interface IPhotosService {
    boolean isPhoto(String extension);
    boolean isPhotoOrZip(String extension);
    String[] getExtensionFilter();
}
