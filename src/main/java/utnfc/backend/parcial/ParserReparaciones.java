package utnfc.backend.parcial;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParserReparaciones {
    // en el archivo original no estaba, pero es bueno hacerlo
    // declaro encabezado viejo y nuevo
    String ENCABEZADO = "id,cliente,dispositivo,marca,prioridad,horas,estado,costoBase";
    String NUEVO_ENCABEZADO = "id,cliente,dispositivo,marca,prioridad,horas,estado,costoBase,garantia,repuestos,modalidad";

    public ResultadoParseo leer(Path ruta) throws IOException {
        List<Reparacion> reparaciones = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        int leidas = 0;
        int descartadas = 0;

        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            // guardo primera linea
            String primeraLinea = lector.readLine();
            // declaro columas q voy a esperar
            int columnasEsperadas;
            // si el encabezado es el viejo son 8 col.
            // si el encabezado es el nuevo son 11 col.
            // si no es ninguna, entonces esta mal lo q me dan de datos
            if (ENCABEZADO.equals(primeraLinea)) {
                columnasEsperadas = 8;
            } else if (NUEVO_ENCABEZADO.equals(primeraLinea)) {
                columnasEsperadas = 11;
            } else {
                throw new IllegalArgumentException("encabezado incorrecto");
            }

            String linea;
            while ((linea = lector.readLine()) != null) {
                leidas++;
                try {
                    String[] campos = Arrays.stream(linea.split(",", -1))
                            .map(String::strip)
                            .collect(Collectors.toList())
                            .toArray(new String[0]);

                    // si la longitud no es la esperada esta mal
                    if (campos.length != columnasEsperadas) {
                        throw new IllegalArgumentException("columnas incorrectas");
                    }

                    if (campos[6].equals("CANCELADA")) {
                        descartadas++;
                        continue;
                    }

                    // validar estado segun cantidad de columnas
                    if (columnasEsperadas == 8) {
                        // si son 8 columnas y no es abierta o lista:
                        if (!campos[6].equals("ABIERTA") && !campos[6].equals("LISTA")) {
                            throw new IllegalArgumentException("estado desconocido");
                        }
                    } else {
                        // si tiene 11 columnas y no es, abierta, lista o entregada:
                        if (!campos[6].equals("ABIERTA") && !campos[6].equals("LISTA") && !campos[6].equals("ENTREGADA")) {
                            throw new IllegalArgumentException("estado desconocido");
                        }
                    }

                    // aca se delega la creacion polimorfica y las validaciones del dominio (no cambie nada)
                    reparaciones.add(Reparacion.desdeCampos(campos));
                } catch (IllegalArgumentException error) {
                    errores.add("Linea " + (leidas + 1) + ": " + error.getMessage());
                }
            }
        }
        return new ResultadoParseo(reparaciones, leidas, descartadas, errores);
    }
}
