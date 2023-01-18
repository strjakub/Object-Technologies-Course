package model;

public enum PictureSizes {
    Small(Sizes.SMALL),
    Medium(Sizes.MEDIUM),
    Large(Sizes.LARGE);

    private int size;

    private PictureSizes(int size) {
        this.size = size;
    }

    class Sizes {
        public static final int SMALL = 50;
        public static final int MEDIUM = 100;
        public static final int LARGE = 200;
    }

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
        return size;
    }
}
