package com.redemonitor.main.apidoc.dispositivo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import com.redemonitor.main.apidoc.APIDocConstants;
import com.redemonitor.main.dto.response.DispositivoResponse;
import com.redemonitor.main.dto.response.ErrorResponse;
import com.redemonitor.main.dto.response.RoleResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
        summary = "Responsável por retornar dados de um dispositivo pelo ID."
)
@ApiResponses(value= {
        @ApiResponse(
                responseCode = "200",
                description = "Dispositivo retornado pelo ID.",
                content = {@Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation =  DispositivoResponse.class))}),
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
public @interface GetDispositivoDoc {

}
