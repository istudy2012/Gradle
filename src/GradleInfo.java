public class GradleInfo {
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String GRADLE_USER_HOME = USER_HOME + "/.gradle/wrapper/dists";
    private static final String BASE_URL = "https://services.gradle.org/distributions";

    private String fileName;
    private String urlHash;

    private String parentPath;

    public GradleInfo(String path) {
        this.fileName = Common.getFileName(path);

        urlHash = Common.getHash(BASE_URL + "/" + fileName);
        parentPath = String.format(GRADLE_USER_HOME + "/%s/%s",  Common.removeExtension(fileName), urlHash);
    }

    public String getFileName() {
        return fileName;
    }

    public String getParentPath() {
        return parentPath;
    }

    public String getZipPath() {
        return parentPath + "/" + fileName;
    }
}
