package io.ride.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-11-27
 * Time: 下午1:08
 */
public class Result {
    private Boolean success;
    private String message;
    private Map<String, Object> data;

    public Result() {
        this.data = new HashMap<String, Object>();
    }

    public Result(Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = new HashMap<String, Object>();
    }

    public Result addData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
