public enum Semester {
    FIRST,
    FOURTH,
    SEVENTH;

    public static Semester fromOrdinal(int ordinal) {
        return values()[ordinal - 1];
    }
}