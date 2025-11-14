package com.italomonitor.disp_monitor.apidoc.dispositivo.monitor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.MediaType;

import com.italomonitor.disp_monitor.apidoc.APIDocConstants;
import com.italomonitor.disp_monitor.dto.response.ErrorResponse;
import com.italomonitor.disp_monitor.dto.response.ExisteNoMonitorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Operation(
        summary = "Responsável por retornar se um dispositivo está sendo monitorado no monitor."
)
@ApiResponses(value= {
        @ApiResponse(
                responseCode = "200",
                description = "Status do dispositivo no monitor (monitorado ou não).",
                content = {@Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation =  ExisteNoMonitorResponse.class))}),
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
public @interface GetExisteNoMonitorDoc {
}
