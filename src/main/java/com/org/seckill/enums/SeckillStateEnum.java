package com.org.seckill.enums;

/**
 * 使用枚举表述常量数据字段
 */
public enum  SeckillStateEnum {

    SUCCESS(1, "Seckill Succeeds"),
    END(0, "Seckill Ends"),
    REPEAT_KILL(-1, "Seckill Repeats"),
    INNER_ERROR(-2, "System Exception"),
    DATA_REWRITE(-3, "Data Rewrite");

    private int state;

    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static SeckillStateEnum stateOf(int index) {
        for (SeckillStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
