package com.gitee.leica.exception;

import com.gitee.leica.object.ResultBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * 全局异常处理器
 *
 * @author wyh
 * @since 2021/4/6 17:38
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未知异常或不想暴露详情的异常统一返回内部错误
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultBase<String> common(Exception e) {
        log.error("exception catch ==>", e);
        return ResultBase.fail("Internal Error");
    }

    /**
     * 外部异常返回错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultBase<String> external(ExternalException e) {
        log.error("external exception catch ==>", e);
        return ResultBase.fail(e.getMessage());
    }

    /**
     * 统一处理{@link javax.validation.Valid}
     * 配合各种{@link javax.validation.constraints.NotNull}
     * {@link javax.validation.constraints.NotBlank}
     * {@link javax.validation.constraints.NotEmpty}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultBase<Void> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("arguments valid catch ==>", e);
        return ResultBase.fail(Optional.ofNullable(e.getBindingResult().getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("参数错误"));
    }
}
