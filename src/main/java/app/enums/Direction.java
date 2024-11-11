package app.enums;

public enum Direction {
    ASC,
    DESC

    ;

    public static boolean isAscending(Direction direction) {
        return ASC.equals(direction);
    }

    public static boolean isDescending(Direction direction) {
        return DESC.equals(direction);
    }
}
