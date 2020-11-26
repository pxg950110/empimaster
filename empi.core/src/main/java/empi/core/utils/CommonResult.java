package empi.core.utils;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>
 * 2020/4/15 16:13
 * </p>
 * <p>
 * 
 * @author pxg
 * @emil pxg950110@163.com
 * @Date 2020/4/15
 * @Version 1.0.0
 * @description
 *              </p>
 *              通用返回类
 */
public class CommonResult<T> {

    private long code;

    private String message;

    private T data;

    public CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommonResult() {
    }

    /**
     * 成功返回结果
     * 
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data) {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(HttpResultStatus.STATUS200.statusCode);
        return new CommonResult<T>(HttpResultStatus.STATUS200.statusCode, HttpResultStatus.STATUS200.statusDescription,
                data);
    }

    /**
     * 成功返回
     * 
     * @param data
     * @param message 提示消息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data, String message) {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(HttpResultStatus.STATUS200.statusCode);
        return new CommonResult<T>(HttpResultStatus.STATUS200.statusCode, message, data);
    }

    /**
     * 失败返回
     * 
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> error() {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(HttpResultStatus.STATUS500.statusCode);
        return new CommonResult<T>(HttpResultStatus.STATUS500.statusCode, HttpResultStatus.STATUS500.statusDescription,
                null);
    }

    /**
     * 失败返回
     * 
     * @param message 提示消息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> error(String message) {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(HttpResultStatus.STATUS500.statusCode);
        return new CommonResult<T>(HttpResultStatus.STATUS500.statusCode, message, null);
    }

    /**
     * 参数验证错误
     * 
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> validatefailed() {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(HttpResultStatus.STATUS300.statusCode);
        return new CommonResult<T>(HttpResultStatus.STATUS300.statusCode, HttpResultStatus.STATUS300.statusDescription,
                null);
    }

    /**
     * 参数验证错误
     * 
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> validatefailed(String message) {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(HttpResultStatus.STATUS300.statusCode);
        return new CommonResult<T>(HttpResultStatus.STATUS300.statusCode, message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(HttpResultStatus.STATUS401.statusCode);
        return new CommonResult<T>(HttpResultStatus.STATUS401.statusCode, HttpResultStatus.STATUS401.statusDescription,
                data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(HttpResultStatus.STATUS403.statusCode);
        return new CommonResult<T>(HttpResultStatus.STATUS403.statusCode, HttpResultStatus.STATUS403.statusDescription,
                data);
    }

    /**
     * 通用返回方法
     * 
     * @param data
     * @param httpResultStatus
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> commomResult(T data, HttpResultStatus httpResultStatus) {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(httpResultStatus.statusCode);
        return new CommonResult<T>(httpResultStatus.statusCode, httpResultStatus.statusDescription, data);
    }

    /**
     * 通用返回方法
     * 
     * @param data
     * @param httpResultStatus
     * @param message
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> commomResult(T data, HttpResultStatus httpResultStatus, String message) {
        // 同步设置http的状态
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        resp.setStatus(httpResultStatus.statusCode);
        return new CommonResult<T>(httpResultStatus.statusCode, message, data);
    }

}
