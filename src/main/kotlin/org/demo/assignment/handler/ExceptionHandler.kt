package org.demo.assignment.handler


import com.fasterxml.jackson.databind.ObjectMapper
import org.demo.assignment.exception.NotFoundException
import org.demo.assignment.exception.UrlServiceException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    val objectMapper: ObjectMapper = ObjectMapper()

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected fun handleNotFoundException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(ex, buildMessage(ex.message), buildHttpHeaders(), HttpStatus.NOT_FOUND, request)
    }


    @ExceptionHandler(UrlServiceException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected fun handleUrlServiceException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(
            ex,
            buildMessage(ex.message),
            buildHttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request
        )
    }

    private fun buildMessage(exceptionMessage: String?): String {

        //  print(exceptionMessge)
        println(exceptionMessage)

        val reason = exceptionMessage?.split(":")!![0]
        val message = exceptionMessage.split(":")[1]
        return objectMapper.createObjectNode().put("reason", reason).put("message", message).toString()
    }


    private fun buildHttpHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        return headers
    }

}