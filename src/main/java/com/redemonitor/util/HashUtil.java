package com.redemonitor.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashUtil {

    public String geraHash( String pw ) {
        try {
            MessageDigest md = MessageDigest.getInstance( "SHA-256" );
            byte[] cript = md.digest( pw.getBytes() );
            return new String( Hex.encode( cript ) );
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        Algorithm algorithm = Algorithm.HMAC256( "949f5f9c8048fa7ff45ffc69e8208a26cd3865e29c65ff720abe330d68e4a572" );

        JWTVerifier verifier = JWT.require( algorithm )
                .withIssuer( "com.redemonitor" )
                .build();

        DecodedJWT decodedJWT = verifier.verify(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjb20ucmVkZW1vbml0b3IiLCJzdWIiOiJpdGFsbyIsInJvbGVzIjpbInVzdWFyaW8tcmVhZCIsInVzdWFyaW8tZGVsZXRlIiwidXN1YXJpby1ncnVwby13cml0ZSIsInVzdWFyaW8tZ3J1cG8tcmVhZCIsInJvbGUtd3JpdGUiLCJyb2xlLXJlYWQiLCJyb2xlLWRlbGV0ZSIsInVzdWFyaW8tZ3J1cG8tZGVsZXRlIiwiZW1wcmVzYS1yZWFkIiwiZW1wcmVzYS13cml0ZSIsImVtcHJlc2EtZGVsZXRlIiwidXN1YXJpby13cml0ZSIsImRpc3Bvc2l0aXZvLXdyaXRlIiwiZGlzcG9zaXRpdm8tcmVhZCIsImRpc3Bvc2l0aXZvLWRlbGV0ZSIsImRpc3Bvc2l0aXZvLW1vbml0b3JhbWVudG8iLCJ1c3VhcmlvLWdldCIsImVtcHJlc2EtZ2V0Il0sImV4cCI6MTc2MTIyODQyM30.qTc5TyYtDj807z3STAl8AEiqXq5RmhyiOpCl20feHHQ"
        );

        System.out.println( decodedJWT.getSubject() );

        System.out.println( "VALIDOU." );

        System.out.println( new HashUtil().geraHash( "italo" ) );
    }

}
