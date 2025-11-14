package com.italomonitor.main.apidoc.usuarioGrupo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import com.italomonitor.main.apidoc.APIDocConstants;
import com.italomonitor.main.dto.response.ErrorResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
        summary = "Responsável pelo registro de um vínculo grupo de usuário/role." )
@ApiResponses(value= {
        @ApiResponse(
                responseCode = "200",
                description = "Vínculo grupo de usuário/role registrado no sistema.",
                content=@Content),
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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VinculaRoleGrupoDoc {
}
