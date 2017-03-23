import java.math.BigInteger;
import java.security.MessageDigest;

public class Common {
    public static String getHash(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = string.getBytes();
            messageDigest.update(bytes);
            return new BigInteger(1, messageDigest.digest()).toString(36);
        } catch (Exception e) {
            throw new RuntimeException("Could not hash input string.", e);
        }
    }

    public static String getFileName(String filePath) {
        if (filePath == null || filePath.equals("")) {
            return null;
        }

        int p = filePath.lastIndexOf("/");
        if (p < 0) {
            return filePath;
        } else {
            return filePath.substring(p + 1);
        }
    }

    public static String removeExtension(String name)
    {
        int p = name.lastIndexOf(".");
        if (p < 0) {
            return name;
        }
        return name.substring(0, p);
    }
}