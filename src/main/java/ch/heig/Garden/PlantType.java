package ch.heig.Garden;

public enum PlantType {
    BITBUD,
    DEBUGDREAM,
    HASHHACK,
    BYTEBLOOM,
    KERNELHAZE;

    String getImageFileName() {
        switch (this) {
            case BITBUD:
                return "bitbud.png";
            case DEBUGDREAM:
                return "debugdream.png";
            case HASHHACK:
                return "hashhack.png";
            case BYTEBLOOM:
                return "bytebloom.png";
            case KERNELHAZE:
                return "kernelhaze.png";
        }
    }
}