package com.qianqian.edu.common.constants;


import lombok.Getter;

/**
 * 响应结果状态码枚举类
 * @author minsiqian
 * @date 2020/2/23 16:18
 */
@Getter
public enum  ResultCodeEnum {

    SUCCESS(true, 2000,"操作成功"),
    UNKNOWN_REASON(false, 4004, "未知错误"),
    BAD_SQL_GRAMMAR(false, 4001, "SQL语法错误"),
    JSON_PARSE_ERROR(false, 4002, "Json解析错误"),
    FILE_UPLOAD_ERROR(false, 4003, "文件上传错误"),
    EXCEL_DATA_IMPORT_ERROR(false, 4005, "Excel数据导入错误"),
    PARAM_ERROR(false, 5001, "参数错误"),
    QUERY_TOTAL_ERROR(false, 5002, "分页查询参数错误");

    private final Boolean success;

    private final Integer code;

    private final String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
