package DataObjects;

public class GradeConstants {
    public static final int GRADE_NONE = 0;
    public static final int GRADE_100 = 1;
    public static final int GRADE_90 = 2;
    public static final int GRADE_83 = 3;
    public static final int GRADE_80 = 4;
    public static final int GRADE_75 = 5;
    public static final int GRADE_70 = 6;
    public static final int GRADE_66 = 7;
    public static final int GRADE_60 = 8;
    public static final int GRADE_50 = 9;
    public static final int GRADE_40 = 10;
    public static final int GRADE_33 = 11;
    public static final int GRADE_30 = 12;
    public static final int GRADE_25 = 13;
    public static final int GRADE_20 = 14;
    public static final int GRADE_16 = 15;
    public static final int GRADE_14 = 16;
    public static final int GRADE_12 = 17;
    public static final int GRADE_11 = 18;
    public static final int GRADE_10 = 19;
    public static final int GRADE_5 = 20;
    public static final int GRADE_MINUS_5 = 21;
    public static final int GRADE_MINUS_10 = 22;
    public static final int GRADE_MINUS_11 = 23;
    public static final int GRADE_MINUS_12 = 24;
    public static final int GRADE_MINUS_14 = 25;
    public static final int GRADE_MINUS_16 = 26;
    public static final int GRADE_MINUS_20 = 27;
    public static final int GRADE_MINUS_25 = 28;
    public static final int GRADE_MINUS_30 = 29;
    public static final int GRADE_MINUS_33 = 30;
    public static final int GRADE_MINUS_40 = 31;
    public static final int GRADE_MINUS_50 = 32;
    public static final int GRADE_MINUS_60 = 33;
    public static final int GRADE_MINUS_66 = 34;
    public static final int GRADE_MINUS_70 = 35;
    public static final int GRADE_MINUS_75 = 36;
    public static final int GRADE_MINUS_80 = 37;
    public static final int GRADE_MINUS_83 = 38;

    public static double getGrade(int gradeConstant) {
        return switch (gradeConstant) {
            case GRADE_100 -> 1;
            case GRADE_90 -> 0.9;
            case GRADE_83 -> 5 / 6d;
            case GRADE_80 -> 0.8;
            case GRADE_75 -> 0.75;
            case GRADE_70 -> 0.7;
            case GRADE_66 -> 2 / 3d;
            case GRADE_60 -> 0.6;
            case GRADE_50 -> 0.5;
            case GRADE_40 -> 0.4;
            case GRADE_33 -> 1 / 3d;
            case GRADE_30 -> 0.3;
            case GRADE_25 -> 0.25;
            case GRADE_20 -> 0.2;
            case GRADE_16 -> 1 / 6d;
            case GRADE_14 -> 1 / 7d;
            case GRADE_12 -> 0.125;
            case GRADE_11 -> 1 / 9d;
            case GRADE_10 -> 0.1;
            case GRADE_5 -> 0.05;
            case GRADE_MINUS_5 -> -0.05;
            case GRADE_MINUS_10 -> -0.1;
            case GRADE_MINUS_11 -> -1 / 9d;
            case GRADE_MINUS_12 -> -0.125;
            case GRADE_MINUS_14 -> -1 / 7d;
            case GRADE_MINUS_16 -> -1 / 6d;
            case GRADE_MINUS_20 -> -0.2;
            case GRADE_MINUS_25 -> -0.25;
            case GRADE_MINUS_30 -> -0.3;
            case GRADE_MINUS_33 -> -1 / 3d;
            case GRADE_MINUS_40 -> -0.4;
            case GRADE_MINUS_50 -> -0.5;
            case GRADE_MINUS_60 -> -0.6;
            case GRADE_MINUS_66 -> -2 / 3d;
            case GRADE_MINUS_70 -> -0.7;
            case GRADE_MINUS_75 -> -0.75;
            case GRADE_MINUS_80 -> -0.8;
            case GRADE_MINUS_83 -> -5 / 6d;

            default -> 0;
        };
    }
}
