package com.appcenter.testingtool.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by diskzhou on 13-8-13.
 */
public class ShellExcuter {
    public synchronized String run(String [] cmd, String workdirectory) throws IOException {
        String result = "";
        try {
            ProcessBuilder builder = new ProcessBuilder(cmd);
            InputStream in = null;
            //设置一个路径
            if (workdirectory != null) {
                builder.directory(new File(workdirectory));
                builder.redirectErrorStream(true);
                Process process = builder.start();
                in = process.getInputStream();
                byte[] re = new byte[1024];
                while (in.read(re) != -1)
                    result = result + new String(re);
            }
            if (in != null) {
                in.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    private String getProcessRunningInfo(String[] args) {

        String result = null;
        ShellExcuter cmdexe = new ShellExcuter();
        try {
            result = cmdexe.run(args, "/system/bin/");
        } catch (IOException ex) {
            TaoLog.Logi("fetch_process_info", "ex=" + ex.toString());
        }
        return result;
    }
}
