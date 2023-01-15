package model;

public enum PictureSizes {
    Small,
    Medium,
    Large;

    public static PictureSizes create(String x) {
        if (x.equalsIgnoreCase("Small")) 
            return Small; 
        
        if (x.equalsIgnoreCase("Medium")) 
            return Medium; 
        
        if (x.equalsIgnoreCase("Large")) 
            return Large;

        throw new IllegalArgumentException(x);
    }
}
