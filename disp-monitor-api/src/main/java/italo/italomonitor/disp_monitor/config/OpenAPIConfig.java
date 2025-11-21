package italo.italomonitor.disp_monitor.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    private final String DESCRIPTION = "Sistema de monitoramento de dispositivos de rede.";
    private final String SERVER_DESCRIPTION = "Servidor local 8080";

    @Value("${spring.application.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.server.url}")
    private String appServerUrl;

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title( appName.toUpperCase() )
                                .version( appVersion )
                                .description( DESCRIPTION )
                )
                .servers(
                        Collections.singletonList(
                                new Server().url( appServerUrl ).description( SERVER_DESCRIPTION )
                        )
                )
                .addSecurityItem( new SecurityRequirement().addList( "bearerAuth" ) )
                .schemaRequirement( "bearerAuth",
                        new SecurityScheme()
                                .name( "bearerAuth" )
                                .in( SecurityScheme.In.HEADER )
                                .type( SecurityScheme.Type.HTTP )
                                .bearerFormat( "JWT" )
                                .scheme( "bearer" )
                );
    }

}
