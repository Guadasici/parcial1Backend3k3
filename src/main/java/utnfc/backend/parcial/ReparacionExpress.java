package utnfc.backend.parcial;
// crear este archivo, dado q este parcial si tiene herencia y polimorfismo
// Esta clase hereda todo de Reparacion con el super del constructor

public class ReparacionExpress extends Reparacion {
    public ReparacionExpress(String id, String cliente, String dispositivo, String marca, int prioridad, int horas, String estado, double costoBase, boolean garantia, double repuestos) {
        super(id, cliente, dispositivo, marca, prioridad, horas, estado, costoBase, garantia, repuestos);
    }

    // Con Override se redefine importe, con super se aplica lo anterior tambien
    @Override
    public double importe() {
        return super.importe() * 1.25;
    }
}
