package committee.nova.mods.cmdkey.utils;


import java.io.File;
import java.io.IOException;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/15 22:17
 * @Description:
 */
public class FileUtils {
    public static File checkFolder(File folder) {
        if (!folder.isDirectory()) {
            folder.mkdir();
            Log.info("创建文件夹: " + folder.getName());
        }
        return folder;
    }

    public static File checkFile(File file) {
        if (!file.isFile()) {
            try {
                file.createNewFile();
                Log.info("创建文件: " + file.getName());
                return file;
            } catch (IOException ignored) {
                return file;
            }
        } else {
            return file;
        }
    }


}
