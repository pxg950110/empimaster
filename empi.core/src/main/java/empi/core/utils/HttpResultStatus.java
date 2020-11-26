package empi.core.utils;

/**
 * <p>
 * 2020/5/27  14:18
 * </p>
 * <p>
 * @author pxg
 * @emil pxg950110@163.com
 * @Date 2020/5/27
 * @Version 1.0.0
 * @description 服务端状态返回码
 * </p>
 */
public enum HttpResultStatus {

    //    100-199 信息性状态码
    //    100 continue 请继续
    STATUS100(100, "continue", "请继续"),
    //    101 switching protocols 切换协议，返回upgraded头
    STATUS101(101, "switching protocols", "切换协议，返回upgraded头"),
    //200-299 成功状态码
    //    200 ok
    STATUS200(200, "ok", "成功"),
    //    201 created  创建资源
    STATUS201(201, "created", "创建资源"),
    //    202 accepted 请求已经接收到，不保证完成
    STATUS202(202, "accepted", "请求已经接收到，不保证完成"),
    //    203 non-authoritative information 非权威信息，不是来自于源端服务器
    STATUS203(203, "non-authoritative information", "非权威信息，不是来自于源端服务器"),
    //    204 no content 没有内容
    STATUS204(204, "no content", "没有内容"),
    //    205 reset content 重置内容，主要是对浏览器html元素
    STATUS205(205, "reset content", "重置内容，主要是对浏览器html元素"),
    //    206 partial content 执行了部分内容
    STATUS206(206, "partial content", "执行了部分内容"),
    //300-399 重定向状态码
    //300 multiple choices 多项选择,会返回一个选项列表
    STATUS300(300, " multiple choices", "多项选择,会返回一个选项列表"),
    //    301 moved permanently 资源被移除，location中包含url
    STATUS301(301, "moved permanently", "资源被移除，location中包含url"),
    //    302 Found 与301类似，客户端应该使用location中的url临时定位
    STATUS302(302, "Found", "与301类似，客户端应该使用location中的url临时定位"),
    //    303 see other 允许post请求的响应重定向
    STATUS303(303, "see other", "允许post请求的响应重定向"),
    //    304 not modified 资源没有修改，返回的时候不能有主体内容，还是本地的内容
    STATUS304(304, "not modified", "资源没有修改，返回的时候不能有主体内容，还是本地的内容"),
    //    305 use proxy  使用代理来请求资源
    STATUS305(305, "use proxy ", "使用代理来请求资源"),
    //    307 temporary redirect 临时重定向，与301类似
    STATUS307(307, " temporary redirect", "临时重定向，与301类似"),
    //    因为http1.0和http1.1的差别因此有交叉
    //400-499 客户端错误状态码
    //    400 bad request 错误请求
    STATUS400(400, "bad request", "错误请求"),
    //    401 unauthorized 没权限
    STATUS401(401, "unauthorized", "没权限"),
    //    402 payment required 未使用
    STATUS402(402, "payment required", "未使用"),
    //    403 forbidden 禁止
    STATUS403(403, "forbidden", "禁止"),
    //    404 not found
    STATUS404(404, "not found", ""),
    //    405 methord not allowed 请求url不支持的方法，应该返回allow首部告诉允许啥
    STATUS405(405, "methord not allowed", "请求url不支持的方法，应该返回allow首部告诉允许啥"),
    //    406 not acceptable 客户端指定参数说明可以接受什么类型的文本
    STATUS406(406, "not acceptable", "客户端指定参数说明可以接受什么类型的文本"),
    //    407 proxy authentication required 要求代理服务器认证权限
    STATUS407(407, "proxy authentication required", "要求代理服务器认证权限"),
    //    408 request timeout 请求超时
    STATUS408(408, "request timeout", "请求超时"),
    //    409 conflict 请求冲突
    STATUS409(409, "conflict", "请求冲突"),
    //    410 gone 类似404
    STATUS410(410, "congonetinue", "类似404"),
    //    411 length required 需要请求中包含content-length
    STATUS411(100, "length required", "需要请求中包含content-length"),
    //    412 precondition failed  先决条件失败
    STATUS412(412, "precondition failed ", "先决条件失败"),
    //    413 request entity too large 客户端发的内容太大
    STATUS413(413, "request entity too large", "请继续客户端发的内容太大"),
    //    414 request uri too long 请求的url太长
    STATUS414(414, "request uri too long", "请求的url太长"),
    //    415 unsuport media type 不支持的媒体类型
    STATUS415(415, " unsuport media type", "不支持的媒体类型"),
    //    416 requested range not satisfiable 请求的范围不满足，无效
    STATUS416(416, " requested range not satisfiable ", "请求的范围不满足，无效"),
    //    417 expectation failed 服务器无法满足请求
    STATUS417(417, "expectation failed ", "服务器无法满足请求"),
    //500-599 服务器错误状态码
    //500 internal server error 内部错误
    STATUS500(500, " internal server error ", "内部错误"),
    //    501 not implemented 没有实现，超出了服务器的范围
    STATUS501(501, "not implemented", "没有实现，超出了服务器的范围"),
    //    502 bad gateway 代理或者网关下一链路收到未响应
    STATUS502(502, "bad gateway", "代理或者网关下一链路收到未响应"),
    //    503 service unavailable 服务不可用
    STATUS503(503, "service unavailable", "服务不可用"),
    //    504 gateway timeout 类似408,超时来自代理
    STATUS504(504, " gateway timeout", " 类似408,超时来自代理"),
    //    505 http version not supported http协议版本不支持
    STATUS505(505, " http version not supported", "http协议版本不支持");
    int statusCode;
    String statusInfo;
    String statusDescription;

    HttpResultStatus(int statusCode, String statusInfo, String statusDescription) {
        this.statusCode = statusCode;
        this.statusInfo = statusInfo;
        this.statusDescription = statusDescription;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}
