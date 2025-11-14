package com.italomonitor.main.apidoc.monitor_server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.MediaType;

import com.italomonitor.main.apidoc.APIDocConstants;
import com.italomonitor.main.dto.response.ErrorResponse;
import com.italomonitor.main.dto.response.MonitorServerResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Operation(
        summary = "Responsável por retornar dados de um servidor de monitoramento pelo ID."
)
@ApiResponses(value= {
        @ApiResponse(
                responseCode = "200",
                description = "Servidor de monitoramento retornado pelo ID.",
                content = {@Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation =  MonitorServerResponse.class))}),
        @ApiResponse(
                responseCode = "403",
                description = APIDocConstants.MSG_403,
                content=@Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
                responseCode = "400",
                description = APIDocConstants.MSG_400,
                content=@Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetMonitorServerDoc {

}
