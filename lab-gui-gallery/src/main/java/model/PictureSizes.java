package model;

public enum PictureSizes {
    Small,
    Medium,
    Large;

    public static final int SIZE_SMALL = 50;
    public static final int SIZE_MDEIUM = 100;
    public static final int SIZE_LARGE = 200;

    public static PictureSizes create(String x) {
        if (x.equalsIgnoreCase("Small")) 
            return Small; 
        
        if (x.equalsIgnoreCase("Medium")) 
            return Medium; 
        
        if (x.equalsIgnoreCase("Large")) 
            return Large;

        throw new IllegalArgumentException(x);
    }

    public int toInt() {
        return switch (this) {
            case Small -> SIZE_SMALL;
            case Medium -> SIZE_MDEIUM;
            case Large -> SIZE_LARGE;
        };
    }
}
