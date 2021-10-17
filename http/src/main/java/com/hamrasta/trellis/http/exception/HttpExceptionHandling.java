package com.hamrasta.trellis.http.exception;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamrasta.trellis.core.log.Logger;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(value = 100)
public class HttpExceptionHandling {

    @ExceptionHandler({BadGatewayException.class})
    public ResponseEntity handleBadGatewayException(BadGatewayException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.BAD_GATEWAY, req);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity handleBadRequestException(BadRequestException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler({BandwidthLimitExceededException.class})
    public ResponseEntity handleBandwidthLimitExceededException(BandwidthLimitExceededException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, req);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity handleConflictException(ConflictException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.CONFLICT, req);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity handleForbiddenException(ForbiddenException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.FORBIDDEN, req);
    }

    @ExceptionHandler({GatewayTimeoutException.class})
    public ResponseEntity handleGatewayTimeoutException(GatewayTimeoutException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.GATEWAY_TIMEOUT, req);
    }

    @ExceptionHandler({InsufficientStorageException.class})
    public ResponseEntity handleInsufficientStorageException(InsufficientStorageException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.INSUFFICIENT_STORAGE, req);
    }

    @ExceptionHandler({InternalServerException.class})
    public ResponseEntity handleInternalServerException(InternalServerException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR, req);
    }

    @ExceptionHandler({NetworkAuthenticationRequiredException.class})
    public ResponseEntity handleNetworkAuthenticationRequiredException(NetworkAuthenticationRequiredException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, req);
    }

    @ExceptionHandler({NotAcceptableException.class})
    public ResponseEntity handleAcceptableException(NotAcceptableException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.NOT_ACCEPTABLE, req);
    }

    @ExceptionHandler({NotExtendedException.class})
    public ResponseEntity handleNotExtendedException(NotExtendedException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.NOT_EXTENDED, req);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity handleNotFoundException(NotFoundException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler({NotImplementedException.class})
    public ResponseEntity handleNotImplementedException(NotImplementedException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.NOT_IMPLEMENTED, req);
    }

    @ExceptionHandler({PayloadTooLargeException.class})
    public ResponseEntity handlePayloadTooLargeException(PayloadTooLargeException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.PAYLOAD_TOO_LARGE, req);
    }

    @ExceptionHandler({PaymentRequiredException.class})
    public ResponseEntity handlePaymentRequiredException(PaymentRequiredException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.PAYMENT_REQUIRED, req);
    }

    @ExceptionHandler({PreConditionRequiredException.class})
    public ResponseEntity handlePreConditionRequiredException(PreConditionRequiredException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.PRECONDITION_REQUIRED, req);
    }

    @ExceptionHandler({ProcessingException.class})
    public ResponseEntity handleProcessingException(ProcessingException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.PROCESSING, req);
    }

    @ExceptionHandler({ProxyAuthenticationRequired.class})
    public ResponseEntity handleProxyAuthenticationRequired(ProxyAuthenticationRequired ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.PROXY_AUTHENTICATION_REQUIRED, req);
    }

    @ExceptionHandler({RequestTimeoutException.class})
    public ResponseEntity handleRequestTimeoutException(RequestTimeoutException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.REQUEST_TIMEOUT, req);
    }

    @ExceptionHandler({RollBackException.class})
    public ResponseEntity handleRollBackException(RollBackException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.CONFLICT, req);
    }

    @ExceptionHandler({ServiceUnavailableException.class})
    public ResponseEntity handleServiceUnavailableException(ServiceUnavailableException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.SERVICE_UNAVAILABLE, req);
    }

    @ExceptionHandler({TokenException.class})
    public ResponseEntity handleTokenException(TokenException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, req);
    }

    @ExceptionHandler({ToManyRequestsException.class})
    public ResponseEntity handleToManyRequestsException(ToManyRequestsException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.TOO_MANY_REQUESTS, req);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity handleAuthException(UnauthorizedException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.UNAUTHORIZED, req);
    }

    @ExceptionHandler({UnSupportMediaTypeException.class})
    public ResponseEntity handleUnSupportMediaTypeException(UnSupportMediaTypeException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.UNSUPPORTED_MEDIA_TYPE, req);
    }

    @ExceptionHandler({UpgradeRequiredException.class})
    public ResponseEntity handleUpgradeException(UpgradeRequiredException ex, HttpServletRequest req) {
        return handleException(ex, HttpStatus.UPGRADE_REQUIRED, req);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        BindingResult bindingResult = ex.getBindingResult();
        ObjectError error = bindingResult.getAllErrors().parallelStream().findFirst().orElse(null);
        if (error == null || StringUtils.isBlank(error.getDefaultMessage()))
            return new ResponseEntity(HttpStatus.OK);
        return extractMessage(error.getDefaultMessage(), req);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest req) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (constraintViolations == null || ex.getConstraintViolations().isEmpty())
            return new ResponseEntity(HttpStatus.OK);
        String firstErrorMessage = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()).stream().findFirst().orElse(StringUtils.EMPTY);
        return extractMessage(firstErrorMessage, req);
    }

    @ExceptionHandler({HttpException.class})
    public ResponseEntity handleHttpException(HttpException ex, HttpServletRequest req) {
        return handleException(ex, ex.getErrorMessage().getHttpStatus(), req);
    }

    protected ResponseEntity handleException(IHttpException ex, HttpStatus httpStatus, HttpServletRequest req) {
        if (StringUtils.isBlank(ex.getErrorMessage().getPath()))
            ex.setPath(req.getServletPath());
        Logger.error("Exception", ex.toString());
        return new ResponseEntity<>(ex.body(), httpStatus);
    }

    private ResponseEntity extractMessage(String message, HttpServletRequest req) {
        HttpErrorMessage errorMessage = toObject(message, HttpErrorMessage.class);
        HttpException httpException = ObjectUtils.isNotEmpty(errorMessage) ? new HttpException(errorMessage) : new HttpException(message, HttpStatus.INTERNAL_SERVER_ERROR) ;
        return handleException(httpException, httpException.getHttpStatus(), req);
    }

    private static <T> T toObject(String value, Class<T> valueType) {
        try {
            ObjectMapper Obj = new ObjectMapper();
            Obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Obj.readValue(value, valueType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
