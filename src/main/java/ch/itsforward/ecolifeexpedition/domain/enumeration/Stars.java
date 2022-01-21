package ch.itsforward.ecolifeexpedition.domain.enumeration;

/**
 * The Stars enumeration.
 */
public enum Stars {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5");

    private final String value;


    Stars(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
