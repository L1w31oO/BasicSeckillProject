package com.lw.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 *
 * @Author L1W31
 * @Version 1.0
 * @Description 验证结果
 */
public class ValidationResult {
    // 默认值 防止空指针异常
    //校验结果是否有错
    private boolean hasErrors = false;

    //存放错误信息的map
    private Map<String, String> errMsgMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrMsgMap() {
        return errMsgMap;
    }

    public void setErrMsgMap(Map<String, String> errMsgMap) {
        this.errMsgMap = errMsgMap;
    }

    //使用 通用的 通过格式化字符串信息 获取 错误结果的 msg 方法
    public String getErrMsg() {
        return StringUtils.join(errMsgMap.values().toArray(), ",");
    }
}
