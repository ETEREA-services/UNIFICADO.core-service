package unificado.core.service.util;

import lombok.extern.slf4j.Slf4j;
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
import java.math.RoundingMode;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class Tool {

    public static ResponseEntity<Resource> generateFile(String filename, String headerFilename)
            throws FileNotFoundException {
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

        int[] weights = { 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 };
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
        if (chain == null) {
            return null;
        }
        return chain
                .replace("\u00C1", "A")
                .replace("\u00C9", "E")
                .replace("\u00CD", "I")
                .replace("\u00D3", "O")
                .replace("\u00DA", "U")
                .replace("\u00E1", "a")
                .replace("\u00E9", "e")
                .replace("\u00ED", "i")
                .replace("\u00F3", "o")
                .replace("\u00FA", "u")
                .replace("\u00C4", "A")
                .replace("\u00CB", "E")
                .replace("\u00CF", "I")
                .replace("\u00D6", "O")
                .replace("\u00DC", "U")
                .replace("\u00E4", "a")
                .replace("\u00EB", "e")
                .replace("\u00EF", "i")
                .replace("\u00F6", "o")
                .replace("\u00FC", "u")
                .replace("\u00C7", "C")
                .replace("\u00E7", "c")
                .replace("\u00C0", "A")
                .replace("\u00C8", "E")
                .replace("\u00CC", "I")
                .replace("\u00D2", "O")
                .replace("\u00D9", "U")
                .replace("\u00E0", "a")
                .replace("\u00E8", "e")
                .replace("\u00EC", "i")
                .replace("\u00F2", "o")
                .replace("\u00F9", "u")
                .replace("\u00C2", "A")
                .replace("\u00CA", "E")
                .replace("\u00CE", "I")
                .replace("\u00D4", "O")
                .replace("\u00DB", "U")
                .replace("\u00E2", "a")
                .replace("\u00EA", "e")
                .replace("\u00EE", "i")
                .replace("\u00F4", "o")
                .replace("\u00FB", "u")
                .replace("\u00C3", "A")
                .replace("\u00E3", "a")
                .replace("\u00D5", "O")
                .replace("\u00F5", "o")
                .replace("\u00D1", "N")
                .replace("\u00F1", "n")
                .replace("\u00BA", "o")
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
        int[] weights = { 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 };
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
        return value.abs(); // valor no negativo (positivo o cero)
    }

    public static AjusteNeto ajustarNeto(BigDecimal importeTotal,
            BigDecimal montoIva21,
            BigDecimal montoIva105,
            BigDecimal montoIva27,
            BigDecimal percepcionIva,
            BigDecimal percepcionIngresosBrutos,
            BigDecimal gastosNoGravados) {

        // 1. Definir Tasas
        BigDecimal TASA_21 = new BigDecimal("0.21");
        BigDecimal TASA_105 = new BigDecimal("0.105");
        BigDecimal TASA_27 = new BigDecimal("0.27");

        // 2. Determinar la "Masa Gravable Real"
        // Restamos del Total Sagrado todo lo que no es Neto ni IVA
        BigDecimal fijos = percepcionIva.add(percepcionIngresosBrutos).add(gastosNoGravados);
        BigDecimal disponibleParaGravado = importeTotal.subtract(fijos);

        // Si no hay plata para repartir, devolvemos ceros
        if (disponibleParaGravado.compareTo(BigDecimal.ZERO) <= 0) {
            return new AjusteNeto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        // 3. Ingeniería Inversa para sacar las PROPORCIONES (Weights)
        // Calculamos cuánto "Neto Teórico" habría en cada cubo según los IVAs de
        // entrada.
        // Usamos 10 decimales para no perder precisión en la proporción.
        BigDecimal n21Raw = (montoIva21.compareTo(BigDecimal.ZERO) != 0)
                ? montoIva21.divide(TASA_21, 10, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        BigDecimal n105Raw = (montoIva105.compareTo(BigDecimal.ZERO) != 0)
                ? montoIva105.divide(TASA_105, 10, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        BigDecimal n27Raw = (montoIva27.compareTo(BigDecimal.ZERO) != 0)
                ? montoIva27.divide(TASA_27, 10, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        // Calculamos el "Bruto Teórico" (Neto + IVA) de cada categoría
        BigDecimal brutoTeorico21 = n21Raw.multiply(BigDecimal.ONE.add(TASA_21));
        BigDecimal brutoTeorico105 = n105Raw.multiply(BigDecimal.ONE.add(TASA_105));
        BigDecimal brutoTeorico27 = n27Raw.multiply(BigDecimal.ONE.add(TASA_27));

        BigDecimal totalBrutoTeorico = brutoTeorico21.add(brutoTeorico105).add(brutoTeorico27);

        // Validación: Si los IVAs vinieron en 0, no sabemos cómo repartir.
        if (totalBrutoTeorico.compareTo(BigDecimal.ZERO) == 0) {
            // Opción A: Asumir todo al 21%?
            // Opción B: Devolver lo que hay (asumiendo que es todo Neto sin IVA, error de
            // datos)
            // Por seguridad, devolvemos el disponible como neto y 0 IVA.
            return new AjusteNeto(disponibleParaGravado, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        // 4. EL FACTOR DE AJUSTE (La clave mágica)
        // Factor = Plata Real Disponible / Plata Teórica de los Inputs
        BigDecimal factor = disponibleParaGravado.divide(totalBrutoTeorico, 15, RoundingMode.HALF_UP);

        // 5. Aplicar Factor y Calcular Nuevos Valores Definitivos
        // Al multiplicar el neto teórico por el factor, escalamos todo
        // proporcionalmente
        BigDecimal nuevoNeto21 = n21Raw.multiply(factor).setScale(2, RoundingMode.HALF_UP);
        BigDecimal nuevoNeto105 = n105Raw.multiply(factor).setScale(2, RoundingMode.HALF_UP);
        BigDecimal nuevoNeto27 = n27Raw.multiply(factor).setScale(2, RoundingMode.HALF_UP);

        // Recalculamos IVA sobre el nuevo Neto (Garantiza consistencia Neto x Tasa)
        BigDecimal nuevoIva21 = nuevoNeto21.multiply(TASA_21).setScale(2, RoundingMode.HALF_UP);
        BigDecimal nuevoIva105 = nuevoNeto105.multiply(TASA_105).setScale(2, RoundingMode.HALF_UP);
        BigDecimal nuevoIva27 = nuevoNeto27.multiply(TASA_27).setScale(2, RoundingMode.HALF_UP);

        // 6. EL AJUSTE FINAL (Plug)
        // Sumamos todo lo nuevo y vemos si falta o sobra algún centavo para llegar al
        // TOTAL SAGRADO
        BigDecimal sumaCalculada = nuevoNeto21.add(nuevoNeto105).add(nuevoNeto27)
                .add(nuevoIva21).add(nuevoIva105).add(nuevoIva27)
                .add(fijos);

        BigDecimal diferencia = importeTotal.subtract(sumaCalculada);

        // Si hay diferencia (centavos), se la asignamos al NETO de la categoría con
        // mayor monto.
        // ¿Por qué al Neto? Porque altera menos la ecuación que tocar el IVA.
        if (diferencia.compareTo(BigDecimal.ZERO) != 0) {
            // Buscamos quién tiene más peso (usamos el bruto teórico calculado antes para
            // decidir)
            if (brutoTeorico21.compareTo(brutoTeorico105) >= 0 && brutoTeorico21.compareTo(brutoTeorico27) >= 0) {
                nuevoNeto21 = nuevoNeto21.add(diferencia);
            } else if (brutoTeorico105.compareTo(brutoTeorico21) >= 0
                    && brutoTeorico105.compareTo(brutoTeorico27) >= 0) {
                nuevoNeto105 = nuevoNeto105.add(diferencia);
            } else {
                nuevoNeto27 = nuevoNeto27.add(diferencia);
            }
        }

        // 7. Resultado Final
        BigDecimal nuevoNetoRegistradoTotal = nuevoNeto21.add(nuevoNeto105).add(nuevoNeto27);

        return new AjusteNeto(nuevoNetoRegistradoTotal, nuevoIva21, nuevoIva105, nuevoIva27);
    }

}
