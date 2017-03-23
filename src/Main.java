import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("err args..");
            return;
        }

        String gradleZipPath = args[0];
        GradleInfo info = new GradleInfo(gradleZipPath);

        try {
            File dir = new File(info.getParentPath());
            if (dir.exists()) {
                FileUtil.forceDelete(dir);
            }
            dir.mkdir();

            String copyTip = String.format("-------- Copy from %s to %s ---------", gradleZipPath, info.getZipPath());
            System.out.println(copyTip);

            FileUtil.copyFile(gradleZipPath, info.getZipPath());

            System.out.println("-------- End copy ---------");
            System.out.println();

            // create .ok file
            File okFile = new File(info.getParentPath(), info.getFileName() + ".ok");
            okFile.createNewFile();

            String unzipTip = String.format("-------- Unzip from %s to %s ---------", info.getZipPath(), info.getParentPath());
            System.out.println(unzipTip);

            FileUtil.unzip(info.getZipPath(), info.getParentPath());

            System.out.println("-------- End unzip ---------");
            System.out.println();

            System.out.println("Success");
        } catch (IOException e) {
            System.out.println("Fail");
            System.out.println(e.getMessage());
        }
    }
}
