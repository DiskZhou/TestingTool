

/**
 * 获取某进程的CPU和内存使用情况
 */
package com.appcenter.testingtool.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appcenter.testingtool.util.ShellExcuter;
import com.appcenter.testingtool.util.TaoLog;

public class CpuInfo {
    private final static int INDEX_CPU = 2;
    private final static int INDEX_PID = 0;
    private final static int INDEX_VSS = 5;
    private final static int INDEX_RSS = 6;
    private final static int INDEX_UID = 7;
    private final static int INDEX_NAME = 9;
    private final static int INDEX_LAST = 10;
    private final static String TAG ="CpuInfo";

    private List<ProcessInfoDo> ProcessList = new ArrayList<ProcessInfoDo>();

    public CpuInfo() {
        RefreshProcessList();
    }

    private String getProcessRunningInfo() {

        String result = null;
        ShellExcuter cmdexe = new ShellExcuter();
        try {
            String[] args = {"/system/bin/top", "-n", "1"};
            result = cmdexe.run(args, "/system/bin/");
        } catch (IOException ex) {
            TaoLog.Logi("fetch_process_info", "ex=" + ex.toString());
        }
        return result;
    }

    private int parseProcessRunningInfo(String infoString) {
        String tempString;
        boolean bIsProcInfo = false;

        String[] rows;
        String[] columns;
        rows = infoString.split("[\n]+");        // 使用正则表达式分割字符串,分行

        for (int i = 1; i < rows.length; i++) {
            tempString = rows[i];
            if (tempString.contains("PID")) {
                bIsProcInfo = true;
                continue;
            }
            if (bIsProcInfo) {
                tempString = tempString.trim();
                columns = tempString.split("\\s+");
                if (columns.length == INDEX_LAST) if (columns[INDEX_UID] != "root") {
                    ProcessInfoDo processInfo = new ProcessInfoDo();
                    processInfo.setPid(columns[INDEX_PID]);
                    processInfo.setCpu(columns[INDEX_CPU]);
                    processInfo.setVss(columns[INDEX_VSS]);
                    processInfo.setRss(columns[INDEX_RSS]);
                    processInfo.setName(columns[INDEX_NAME]);
                    ProcessList.add(processInfo);
                }
            }


        }
        return ProcessList.size();
    }


    // 初始化所有进程的CPU和内存列表，用于检索每个进程的信息
    public void RefreshProcessList() {
        if (ProcessList != null && ProcessList.size() > 0) {
            //每次先清空List然后重新添加列表
            ProcessList.clear();
        }
        String resultString = getProcessRunningInfo();
        parseProcessRunningInfo(resultString);
    }

    // 根据进程名获取CPU和内存信息
    public String getCpuUsageByName(String procName) {
        String result = "";
        for (ProcessInfoDo processInfoDo : ProcessList) {
            if (procName != null && procName.equals(processInfoDo.getName())) {
                result = processInfoDo.getCpu();
                break;
            }
        }
        return result;
    }


    public Map<String,String> getCpuUsageByPackageName(String packageName){
        Map<String,String> result = new HashMap<String, String>();
        for (ProcessInfoDo processInfoDo : ProcessList) {
            Pattern p = Pattern.compile(packageName+":([A-Za-z]+$)");
            Matcher m = p.matcher(processInfoDo.getName());
            while(m.find()){
               TaoLog.Logi(TAG,m.group(1));
            }
        }
        return result;
    }
    // 根据进程ID获取CPU和内存信息
    public String getCpuUsageByPid(int pid) {
        String result = "";
        for (ProcessInfoDo processInfoDo : ProcessList) {
            if (String.valueOf(pid).equals(processInfoDo.getPid())) {
                result = processInfoDo.getCpu();
                break;
            }
        }
        return result;
    }
}

