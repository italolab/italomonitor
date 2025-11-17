package com.italomonitor.main.components;

import org.springframework.stereotype.Component;

@Component
public class CRC16CCITTFalse {

    public static int computeCRC(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        int crc = 0xFFFF; // Initial value
        int polynomial = 0x1021;

        for (byte b : data) {
            // Convert signed byte to unsigned int
            int currentByte = b & 0xFF;
            crc ^= (currentByte << 8);

            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ polynomial;
                } else {
                    crc <<= 1;
                }
                crc &= 0xFFFF; // Keep CRC 16-bit
            }
        }
        return crc & 0xFFFF;
    }

    public static void main(String[] args) {
        try {
            String input = "00020126580014br.gov.bcb.pix0136123e4567-e12b-12d1-a456-4266554400005204000053039865802BR5913Fulano de Tal6008BRASILIA62070503***6304";
            byte[] bytes = input.getBytes("UTF-8");

            int crc = computeCRC(bytes);
            System.out.println( Integer.toHexString( crc ) ); 

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
