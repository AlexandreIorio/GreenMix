package ch.heig.Garden;

public enum PlantType {
    BITBUD,
    DEBUGDREAM,
    HASHHACK,
    BYTEBLOOM,
    KERNELHAZE;
    public String getImageFileName() {
        switch (this) {
            case BITBUD:
                return "bitbud.png";
            case DEBUGDREAM:
                return "debugdream.png";
            case HASHHACK:
                return "hashack.png";
            case BYTEBLOOM:
                return "bytebloom.png";
            case KERNELHAZE:
                return "kernelhaze.png";
            default:
                return "";
        }
    }

    public double getDuration() {
        switch (this) {
            case BITBUD:
                return 5;
            case DEBUGDREAM:
                return 15;
            case HASHHACK:
                return 10;
            case BYTEBLOOM:
                return 20;
            case KERNELHAZE:
                return 30;
            default:
                return 0;
        }
    }
    public int getHavrest(){
        switch (this) {
            case BITBUD:
                return 3;
            case DEBUGDREAM:
                return 1;
            case HASHHACK:
                return 2;
            case BYTEBLOOM:
                return 1;
            case KERNELHAZE:
                return 1;
            default:
                return 0;
        }
    }
    public double getPurchasePrice(){
        switch (this) {
            case BITBUD:
                return 3;
            case DEBUGDREAM:
                return 8;
            case HASHHACK:
                return 5;
            case BYTEBLOOM:
                return 10;
            case KERNELHAZE:
                return 15;
            default:
                return 0;
        }
    }
    public double getSellingPrice(){
        switch (this) {
            case BITBUD:
                return 5;
            case DEBUGDREAM:
                return 11;
            case HASHHACK:
                return 9;
            case BYTEBLOOM:
                return 14;
            case KERNELHAZE:
                return 21;
            default:
                return 0;
        }
    }

}