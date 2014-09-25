package com.appcenter.testingtool.model;

/**
 * Created by diskzhou on 13-8-13.
 */
public class ProcessInfoDo{
    private String vss;
    private String rss;
    private String name;
    private String pid;

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    private String cpu;


    public String getVss() {
        return vss;
    }

    public void setVss(String vss) {
        this.vss = vss;
    }

    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
