/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.datatype.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author faust
 */
public class DataTypeChecker {

    private Pattern pattern;
    private static final String intPattern = "([0-9]+)";
    private static final String booleanPattern = "(true|false)";
    private String str;

    public DataTypeChecker(String _str) {
        this.str = _str;
    }

    public boolean isInteger() {
        pattern = Pattern.compile(intPattern);
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    public boolean isString() {
        if ((!isInteger() && !isArray() && !isBoolean())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isBoolean() {
        pattern = Pattern.compile(booleanPattern);
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    public boolean isArray() {
        if (((str.startsWith("\"") && str.endsWith("\"")) || (str.startsWith("[") && str.endsWith("]")))) {
            return true;
        } else {
            return false;
        }
    }
}
