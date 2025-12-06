package unificado.core.service.util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Tool {

    public static ResponseEntity<Resource> generateFile(String filename, String headerFilename) throws FileNotFoundException {
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + headerFilename);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    public static Boolean validaCuit(String cuit) {
        cuit = cuit.trim();

        if (cuit.length() != 11) {
            return false;
        }

        if (cuit.equals("00000000000") || cuit.equals("11111111111")) {
            return false;
        }

        int[] weights = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        for (int position = 0; position < 10; position++) {
            int digit = Character.getNumericValue(cuit.charAt(position));
            sum += weights[position] * digit;
        }

        int mod = sum % 11;
        int verifier = 11 - mod;

        if (verifier == 11) {
            verifier = 0;
        } else if (verifier == 10) {
            verifier = 9;
        }

        int lastDigit = Character.getNumericValue(cuit.charAt(10));
        return verifier == lastDigit;
    }

    public static String replaceSymbols(String chain) {
        return chain
            .replace("Á", "A")
            .replace("É", "E")
            .replace("Í", "I")
            .replace("Ó", "O")
            .replace("Ú", "U")
            .replace("á", "a")
            .replace("é", "e")
            .replace("í", "i")
            .replace("ó", "o")
            .replace("ú", "u")
            .replace("Ñ", "N")
            .replace("ñ", "n")
            .replace("º", "o")
            .replace("&", "y");
    }

    public static String generaCuit(String dni, String sexo) {
        dni = dni.trim();
        dni = String.format("%08d", Integer.parseInt(dni));
        sexo = sexo.toUpperCase();
        String xy;
        switch (sexo) {
            case "M":
                xy = "20";
                break;
            case "F":
                xy = "27";
                break;
            default:
                xy = "30";
                break;
        }
        String full = xy + dni;
        int[] weights = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int position = 0; position < 10; position++) {
            sum += weights[position] * Character.getNumericValue(full.charAt(position));
        }
        int mod = sum % 11;
        int verificador = 11 - mod;
        if (mod == 0) {
            verificador = 0;
        } else if (mod == 1) {
            if ("M".equals(sexo)) {
                verificador = 9;
                xy = "23";
            } else if ("F".equals(sexo)) {
                verificador = 4;
                xy = "23";
            }
        }
        return xy + "-" + dni + "-" + verificador;
    }

    public static void addFileToZip(ZipOutputStream zipOutputStream, String filename) throws IOException {
        byte[] buffer = new byte[1024];
        zipOutputStream.putNextEntry(new ZipEntry(new File(filename).getName()));
        FileInputStream fileInputStream = new FileInputStream(filename);
        int len;
        while ((len = fileInputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
        }
        fileInputStream.close();
        zipOutputStream.closeEntry();
    }

    public static BigDecimal copySign(BigDecimal signReference, BigDecimal value) {
        if (signReference == null || value == null) {
            throw new IllegalArgumentException("Los parámetros no pueden ser null");
        }

        // Aplicar el signo de signReference a la magnitud de value
        if (signReference.signum() < 0) {
            return value.abs().negate(); // valor negativo con magnitud de |value|
        }
        return value.abs();          // valor no negativo (positivo o cero)
    }

}
