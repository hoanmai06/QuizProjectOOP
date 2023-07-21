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
    public static final int GRADE_60 = 6;
    public static final int GRADE_50 = 7;
    public static final int GRADE_40 = 8;
    public static final int GRADE_33 = 9;
    public static final int GRADE_30 = 10;
    public static final int GRADE_25 = 11;
    public static final int GRADE_20 = 12;
    public static final int GRADE_16 = 13;
    public static final int GRADE_14 = 14;
    public static final int GRADE_12 = 15;
    public static final int GRADE_11 = 16;
    public static final int GRADE_10 = 17;
    public static final int GRADE_5 = 18;
    public static final int GRADE_MINUS_5 = 19;

    public static double getGrade(int gradeConstant) {
        return switch (gradeConstant) {
            case 1 -> 1;
            case 2 -> 0.9;
            case 3 -> 5 / 6d;
            case 4 -> 0.8;
            case 5 -> 0.75;
            case 6 -> 0.7;
            case 7 -> 0.5;
            case 8 -> 0.4;
            case 9 -> 1 / 3d;
            case 10 -> 0.3;
            case 11 -> 0.25;
            case 12 -> 0.2;
            case 13 -> 1 / 6d;
            case 14 -> 1 / 7d;
            case 15 -> 0.125;
            case 16 -> 1 / 9d;
            case 17 -> 0.1;
            case 18 -> 0.05;
            case 19 -> -0.05;

            default -> 0;
        };
    }
}
