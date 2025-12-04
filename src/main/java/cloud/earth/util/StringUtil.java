package cloud.earth.util;

public class StringUtil {

    public static String trim(String text){
        return text.replaceAll("\\s+", " ").trim();
    }
}
