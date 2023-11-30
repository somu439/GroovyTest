import static java.lang.Math.abs

public static boolean fuzzyDecimalMatch(String expValue, String actValue, double tolerance) {
//        double Val1 = Double.parseDouble(expValue);
//        double Val2 = Double.parseDouble(actValue);
    BigDecimal Val1 = new BigDecimal(expValue);
    BigDecimal Val2 = new BigDecimal(actValue);
//        double d = Val1 - Val2;
    double res = abs((Val1.subtract(Val2)).doubleValue() / tolerance);
//        System.out.println(res);
    if (res <= 1) {
//            if ((Val1 - Val2) < tolerance) {
        log.info("within tolerance ("+Double.toString(tolerance)+") level: "+(Val1.subtract(Val2)));
        return true;
    } else {
        log.warn("not within tolerance (" + Double.toString(tolerance) + ") level: " + (Val1.subtract(Val2)));
        return false;
    }

}