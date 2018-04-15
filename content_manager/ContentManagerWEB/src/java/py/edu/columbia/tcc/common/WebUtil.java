/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tid
 */
public class WebUtil {

    private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

    public static int strToInt(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return 0;
            }

            return Integer.parseInt(str);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static double strToDouble(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return 0;
            }

            return Double.parseDouble(str);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static Date strToDate(String str) throws ParseException {

        if (str == null || str.trim().isEmpty()) {
            log.debug("El texto es nulo o vacío, no se puede convertir a Date.");

            return null;
        }

        if (!matcherDate(str)) {
            log.debug("El texto no es una fecha válida del tipo yyyy-MM-dd, valor: {}", str);

            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.parse(str.trim());
    }

    public static Date strToDateTime(String str) throws ParseException {
        String dt1 = "2014-01-01 %s:00";
        String dt2 = "2014-01-01 %s";

        //Fecha hora completa
        String strDT = "";

        if (str == null || str.trim().isEmpty()) {
            log.debug("La hora es nulo o vacío, no se puede convertir a yyyy-MM-dd HH:mm:ss.");

            return null;
        }

        //Si solo viene la hora y el minuto se arma la fecha completa
        if (str.trim().length() == 5 && str.trim().indexOf(":") > 0) {
            strDT = String.format(dt1, str.trim());
        }

        //Si solo viene la hora, el minuto y los segundos se arma la fecha completa
        if (str.trim().length() == 8 && str.trim().indexOf(":") > 0) {
            strDT = String.format(dt2, str.trim());
        }

        String time = strDT.substring(10).trim();
        if (!matcherTime(time)) {
            log.debug("La parte de la fecha que corresponde a la hora no es válido del tipo HH:mm:ss, valor: {}", time);

            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.parse(strDT);
    }

    private static boolean matcherDate(String str) {
        Pattern pattern = Pattern.compile("^(2\\d{3})-([0][1-9]|[1][0-2])-([0-1][1-9]|[2][0-9]|[3][0-1])$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    private static boolean matcherTime(String str) {
        Pattern pattern = Pattern.compile("^([0][0-9]|[1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$");
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    public static String localeCurrencyToMask(BigDecimal value) {
        Locale l = new Locale("es", "PY");
        NumberFormat nf = NumberFormat.getCurrencyInstance(l);
        return nf.format(value);
    }

    public static String currencyToMask(BigDecimal value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value);
    }

    public static String stackTraceToStr(Throwable t) {
        StringBuilder sb = new StringBuilder();

        for (StackTraceElement element : t.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(WebUtil.matcherTime("00:59:59"));
    }

    public static boolean compareHours(int hour, int minute) {

        Calendar calendar = Calendar.getInstance();
        int hourSystem, minuteSystem;

        hourSystem = calendar.get(Calendar.HOUR_OF_DAY);
        minuteSystem = calendar.get(Calendar.MINUTE);
        if (hourSystem == hour && minuteSystem == minute) {
            return true;
        } else {

            return false;
        }

    }
}
