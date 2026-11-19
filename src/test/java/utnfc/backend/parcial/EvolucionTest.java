package utnfc.backend.parcial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EvolucionTest {
    // en los 2 metodos agregue "Normal" xq sino no funciona xq tenia 10 columnas antes,
    // y solo acepta 8 u 11 segun las consignas
    @Test
    void agregaGarantiaYRepuestos() {
        var reparacion = Reparacion.desdeCampos(new String[] {
                "P", "A", "celular", "Nexo", "1", "5", "ENTREGADA", "0", "SI", "100", "NORMAL"
        });

        assertTrue(reparacion.tieneGarantia());
        assertEquals(3800, reparacion.importe());
    }

    @Test
    void reconoceEntregada() {
        var reparacion = Reparacion.desdeCampos(new String[] {
                "P", "A", "celular", "Nexo", "1", "1", "ENTREGADA", "0", "NO", "0", "NORMAL"
        });

        assertEquals("ENTREGADA", reparacion.getEstado());
    }
}
