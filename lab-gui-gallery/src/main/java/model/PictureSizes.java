package model;

public enum PictureSizes {
    Small,
    Medium,
    Large;

    public static final int SIZE_SMALL = 50;
    public static final int SIZE_MEDIUM = 100;
    public static final int SIZE_LARGE = 200;

    public static PictureSizes create(String x) {
        
        if (x.equals("Small")) 
            return Small; 

        if (x.equals("Medium")) 
            return Medium; 

        if (x.equals("Large")) 
            return Large;

        throw new IllegalArgumentException(x);
    }

    public int toInt() {
        return switch (this) {
            case Small -> SIZE_SMALL;
            case Medium -> SIZE_MEDIUM;
            case Large -> SIZE_LARGE;
        };
    }
}
