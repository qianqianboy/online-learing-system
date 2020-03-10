package com.qianqian.edu.common.handler;

import com.qianqian.edu.common.constants.ResultCodeEnum;
import com.qianqian.edu.common.exception.QianQianException;
import com.qianqian.edu.common.util.ExceptionUtil;
import com.qianqian.edu.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类(包括自定义异常)
 * @author minsiqian
 * @date 2020/2/24 14:43
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error();
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(JsonParseException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(QianQianException.class)
    @ResponseBody
    public R error(QianQianException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.setResult(ResultCodeEnum.QUERY_TOTAL_ERROR);
    }

}