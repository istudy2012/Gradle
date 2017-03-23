import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }

                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }

    }

    public static void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            cleanDirectory(directory);
        }

        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    public static void cleanDirectory(File directory) throws IOException {
        String msg;
        if (!directory.exists()) {
            msg = directory + " does not exist";
            throw new IllegalArgumentException(msg);
        } else if (!directory.isDirectory()) {
            msg = directory + " is not a directory";
            throw new IllegalArgumentException(msg);
        } else {
            File[] files = directory.listFiles();
            if (files == null) {
                throw new IOException("Failed to list contents of " + directory);
            } else {
                IOException exception = null;

                int len = files.length;
                for (int i = 0; i < len; ++i) {
                    File file = files[i];

                    try {
                        forceDelete(file);
                    } catch (IOException var8) {
                        exception = var8;
                    }
                }

                if (null != exception) {
                    throw exception;
                }
            }
        }
    }

    public static void copyFile(String inputFile, String outputFile) {
        try {
            InputStream reader = new FileInputStream(inputFile);
            BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(outputFile));

            byte[] buffer = new byte[1024];
            int numRead;
            while ((numRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, numRead);
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unzip(String zipPath, String outPath)
            throws IOException {
        File zip = new File(zipPath);
        File dest = new File(outPath);

        ZipFile zipFile = new ZipFile(zip);
        try {
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    new File(dest, entry.getName()).mkdirs();
                } else {
                    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(dest, entry.getName())));
                    try {
                        copyInputStream(zipFile.getInputStream(entry), outputStream);
                    } finally {
                    }
                }
            }
        } finally {
            zipFile.close();
        }
    }

    private static void copyInputStream(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
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

    public static String removeExtension(String name) {
        int p = name.lastIndexOf(".");
        if (p < 0) {
            return name;
        }
        return name.substring(0, p);
    }
}
