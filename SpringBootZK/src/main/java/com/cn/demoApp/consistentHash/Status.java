package com.cn.demoApp.consistentHash;

public enum Status {
    run("run","运行中"),
    stop("stop","停止");

    private String code;
    private String desc;

    Status(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }

    public static void main(String[] args) {
        System.out.println(Status.run.getCode());
    }
}
