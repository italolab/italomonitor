package com.redemonitor.main.apidoc.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import com.redemonitor.main.apidoc.APIDocConstants;
import com.redemonitor.main.dto.response.ErrorResponse;
import com.redemonitor.main.dto.response.LoginResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(
        summary = "Responsável por autenticar um usuário e retornar o access token, o refresh token e outras informações do usuário logado."
)
@ApiResponses(value= {
        @ApiResponse(
                responseCode = "200",
                description = "Token e outros dados retornados.",
                content = {@Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation =  LoginResponse.class))}),
        @ApiResponse(
                responseCode = "400",
                description = APIDocConstants.MSG_400,
                content=@Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class)))
})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginDoc {

}
