public enum FormOfEducation {
    DISTANCE_EDUCATION,
    FULL_TIME_EDUCATION,
    EVENING_CLASSES;

    public static FormOfEducation fromOrdinal(int ordinal) {
        return values()[ordinal - 1];
    }
}