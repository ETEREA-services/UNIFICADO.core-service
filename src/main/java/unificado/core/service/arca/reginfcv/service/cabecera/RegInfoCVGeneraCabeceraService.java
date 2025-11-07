package unificado.core.service.arca.reginfcv.service.cabecera;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.OffsetDateTime;

@Service
@Slf4j
public class RegInfoCVGeneraCabeceraService {

    public String generateFile(String cuit, OffsetDateTime desde, String path) throws IOException {
        var filename = path + "REGINFO_CV_CABECERA.txt";
        log.debug("Filename: {}", filename);
        var out = new BufferedWriter(new FileWriter(filename));

        StringBuilder strLinea = new StringBuilder();
        strLinea.append(cuit.replace("-", ""));
        strLinea.append(String.format("%04d", desde.getYear()));
        strLinea.append(String.format("%02d", desde.getMonthValue()));
        strLinea.append("00");
        strLinea.append("N");
        strLinea.append("N");
        strLinea.append("1");
        for (int i = 0; i < 6; i++) {
            strLinea.append("0".repeat(15));
        }
        out.write(strLinea + "\r\n");
        out.close();

        return filename;
    }

}
