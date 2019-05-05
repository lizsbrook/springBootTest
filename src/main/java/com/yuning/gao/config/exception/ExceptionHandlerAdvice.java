package com.yuning.gao.config.exception;

import com.yuning.gao.domain.ResDTO;
import com.yuning.gao.domain.RsJsonManager;
import com.yuning.gao.domain.exception.BadGatewayException;
import com.yuning.gao.domain.exception.ForbiddenException;
import com.yuning.gao.domain.exception.PreconditionException;
import com.yuning.gao.domain.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

	@ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResDTO> exception(Exception e, HttpServletRequest request){

		log.error("请求路径{}报错, 错误原因为: {}", request.getRequestURI(), e.getMessage());

		//查询不到错误  404, "Not Found"
		if (e instanceof NotFoundException) {
			return new ResponseEntity<>(RsJsonManager.getResultJson().reError(e.getMessage()), HttpStatus.NOT_FOUND);
		}
        //授权失败错误 401, "Unauthorized"
		if (e instanceof UnauthorizedException) {
			return new ResponseEntity<>(RsJsonManager.getResultJson().reError(e.getMessage()), HttpStatus.UNAUTHORIZED);
		}
        //处理参数方法参数验证 400  Bad Request
		if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
			ObjectError objectError = methodArgumentNotValidException.getBindingResult().getAllErrors().get(0);
			return new ResponseEntity<>(RsJsonManager.getResultJson().reError(objectError.getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}
		//处理普通方法验证 400  Bad Request
		if (e instanceof ConstraintViolationException) {
			return new ResponseEntity<>(RsJsonManager.getResultJson().reError(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		//服务器已经理解请求，但是拒绝执行它  403, "Forbidden"
		if (e instanceof ForbiddenException) {
			return new ResponseEntity<>(RsJsonManager.getResultJson().reError(e.getMessage()), HttpStatus.FORBIDDEN);
		}
		//请求的header 的条件不对  412, "Precondition Failed"
		if (e instanceof PreconditionException) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(RsJsonManager.getResultJson().reError(e.getMessage()));
		}
		//请求方法不对  405
		if (e instanceof HttpRequestMethodNotSupportedException) {
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(RsJsonManager.getResultJson().reError(e.getMessage()));
		}
		//502 请求上游服务器出错
		if (e instanceof BadGatewayException) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(RsJsonManager.getResultJson().reError(e.getMessage()));
		}


		//如果没有异常捕捉到则返回 500错误
		return new ResponseEntity<>(RsJsonManager.getResultJson().reError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}





}
