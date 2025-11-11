package com.redemonitor.main.apidoc.dispositivo.monitor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.MediaType;

import com.redemonitor.main.apidoc.APIDocConstants;
import com.redemonitor.main.dto.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Operation(
        summary = "Responsável por iniciar ou reiniciar o monitoramento de todos os dispositivo marcados como sendo monitorado."
        		+ "Útil para quando os servidores são reiniciados e os monitoramentos perdidos. "
        		+ "Então, os monitoramentos são reiniciados."
)
@ApiResponses(value= {
        @ApiResponse(
                responseCode = "200",
                description = "Todos os monitoramentos de dispositivos marcados como sendo monitorado iniciados ou reiniciados.",
                content = @Content),
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
public @interface StartOrRestartMonitoramentosDoc {
}
