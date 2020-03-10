package com.qianqian.edu.common.exception;

import com.qianqian.edu.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 自定义异常类
 * @author minsiqian
 * @date 2020/2/24 15:03
 */

@Data
@ApiModel(value = "全局异常")
public class QianQianException extends RuntimeException{
    @ApiModelProperty(value = "自定义异常状态码")
    private Integer code;

    @ApiModelProperty(value = "自定义异常提示信息")
    private String message;

    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public QianQianException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum
     */
    public QianQianException(ResultCodeEnum resultCodeEnum) {
//        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "QianQianException{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}
