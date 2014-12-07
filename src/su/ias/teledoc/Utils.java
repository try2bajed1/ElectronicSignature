package su.ias.teledoc;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 05.11.2014
 * Time: 12:43
 */
public class Utils {


    public static String getCorrectPhoneStr(String oldStr) {

        String[] splittedArr = oldStr.split("-");
        StringBuilder builder = new StringBuilder();

        builder.append("+7 (");
        builder.append(splittedArr[0]);
        builder.append(") ");
        builder.append(splittedArr[1]);
        builder.append("-");
        builder.append(splittedArr[2]);
        builder.append("-");
        builder.append(splittedArr[3]);

        return builder.toString();
    }


    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
