package com.github.plugin.sysdict.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

/**
 * @author duhongming
 * @version 1.0
 * @description 响应基本的结果以及datatable类型
 * @date 2020/3/21 08:36
 */
@Component
@Scope("prototype")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "响应基本的结果以及datatable类型" ,
        description = "注意: 并非所有的字段都会被解析，为null的属性不会被解析")
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("状态码")
    private Integer statusCode;

    @ApiModelProperty("信息")
    private String message;

    @ApiModelProperty("封装多个 错误信息")
    private Map<String, Object> errors;

    @ApiModelProperty("重定向的action")
    private String uri;

    @ApiModelProperty("数据")
    private T data;

    @ApiModelProperty(value = "dataTable查询次数",
            notes = "注意：如果是参数注入的方法注入JsonResult不用设置本属性\n" +
                    "如果是通过new，在set时不用将原有的draw次数+1，否则将会比预想的大1")
    private Integer draw;

    @ApiModelProperty("总数")
    private Integer total;

    @ApiModelProperty("dataTable本次查询出的数据量")
    private Integer recordsTotal;

    @ApiModelProperty("过滤的数据量")
    private Integer recordsFiltered;

    @ApiModelProperty("表头数据")
    private Object tatledata;

    public Object getTatledata() {
        return tatledata;
    }

    public JsonResult setTatledata(Object tatledata) {
        this.tatledata = tatledata;
        return this;
    }

    public Boolean isSuccess() {
        return success;
    }

    public JsonResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public JsonResult  setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public JsonResult setErrors(Map<String, Object> errors) {
        this.errors = errors;
        return this;
    }

    public String getUri() {
        return uri;
    }

    /**
     * 用于通知前台需要加载的页面，例如前台接受到成功消息后在3秒后加载一个page页面
     * @param rui
     * @return
     */
    public JsonResult setUri(String rui) {
        this.uri = rui;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonResult setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getDraw() {
        return draw;
    }

    /**
     * dataTable查询次数
     * 注意：如果是参数注入的方法注入JsonResult不用设置本属性
     * 如果是通过new，在set时不用将原有的draw次数+1，否则将会比预想的大1
     */
    public JsonResult setDraw(int draw) {
        this.draw = draw;
        return this;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public JsonResult setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
        this.setRecordsFiltered(recordsTotal);
        return this;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public JsonResult setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
        return this;
    }

    public JsonResult(Boolean success, Map<String, Object> errors) {
        this.success = success;
        this.errors = errors;
    }

    public JsonResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public JsonResult setTotal(Integer total) {
        this.total = total;
        return this;
    }

    /**
     * 用于重置当前json对象
     * @return
     */
    public JsonResult reset(){
        this.success = null;
        this.statusCode = null;
        this.message = null;
        this.errors = null;
        this.uri = null;
        this.data = null;
        this.draw = null;
        this.recordsTotal = null;
        this.recordsFiltered = null;
        return this;
    }

    public JsonResult() {
        this.statusCode = 10000;
        this.success = true;
        this.message = "成功";
    }

    public JsonResult(T data, Integer recordsTotal) {
        this.data = data;
        this.recordsTotal = recordsTotal;
    }
}