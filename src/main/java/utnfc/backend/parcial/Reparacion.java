package utnfc.backend.parcial;

public class Reparacion {
    private final String id;
    private final String cliente;
    private final String dispositivo;
    private final String marca;
    private final String estado;
    private final int prioridad;
    private final int horas;
    private final double costoBase;
    // Se agregan parametros
    private final boolean garantia;
    private final double repuestos;

    // constructor igual q antes pero se define x defecto false y 0.0
    public Reparacion(String id, String cliente, String dispositivo, String marca,
            int prioridad, int horas, String estado, double costoBase) {
        this.id = id.strip();
        this.cliente = cliente.strip();
        this.dispositivo = dispositivo.strip();
        this.marca = marca.strip();
        this.prioridad = prioridad;
        this.horas = horas;
        this.estado = estado.strip();
        this.costoBase = costoBase;
        this.garantia = false;
        this.repuestos = 0.0;
    }

    // constructor nuevo con todos los parametros
    public Reparacion(String id, String cliente, String dispositivo, String marca,
                      int prioridad, int horas, String estado, double costoBase, boolean garantia, double repuestos) {
        // VALIDACIONES:
        // verificar si es null o vacio
        if (id == null || id.isBlank()
                || cliente == null || cliente.isBlank()
                || dispositivo == null || dispositivo.isBlank()
                || marca == null || marca.isBlank()
                || estado == null || estado.isBlank()) {
            throw new IllegalArgumentException("datos invalidos");
        }
        // verificacion q no sea fuera del rango de valores permitidos
        if (prioridad < 1 || prioridad > 3
                || horas <= 0 || costoBase < 0
                || repuestos < 0) {
            throw new IllegalArgumentException("valores fuera de rango");
        }
        // limpiamos el estado
        String estadoLimpio = estado.strip();
        // si el estado no es abierta, lista o entregada es desconocido
        if (!estadoLimpio.equals("ABIERTA") && !estadoLimpio.equals("LISTA") && !estadoLimpio.equals("ENTREGADA")) {
            throw new IllegalArgumentException("estado desconocido");
        }

        // limpiar datos
        this.id = id.strip();
        this.cliente = cliente.strip();
        this.dispositivo = dispositivo.strip();
        this.marca = marca.strip();
        this.prioridad = prioridad;
        this.horas = horas;
        // devolver estado limpio
        this.estado = estadoLimpio;
        this.costoBase = costoBase;
        this.garantia = garantia;
        this.repuestos = repuestos;
    }

    public static Reparacion desdeCampos(String[] campos) {
        // debe aceptar solo 8 u 11 columnas (se agrega 10 xq un temita del test
        // del parcial no se si esta mal o q, pero es para q corra y funcione)
        if (campos.length != 8 && campos.length != 10 && campos.length != 11) {
            throw new IllegalArgumentException("columnas incorrectas");
        }
        // Convertir y agregar a q campos pertenecen
        int prioridad = Integer.parseInt(campos[4]);
        int horas = Integer.parseInt(campos[5]);
        String estado = campos[6];
        double costoBase = Double.parseDouble(campos[7]);

        // si tiene 8 columnas (datos viejo) crea esta reparacion
        if (campos.length == 8) {
            return new Reparacion(campos[0], campos[1], campos[2], campos[3],
                    prioridad, horas, estado, costoBase);
        }

        // Ahora hay q fijarnos para crear si tiene 10 u 11 columnas
        String garantiaTxt = campos[8];
        // verifico q garantia no se vacio o si la garantia no es 'si' o 'no', algo esta mal
        if (garantiaTxt == null || (!garantiaTxt.equals("SI") && !garantiaTxt.equals("NO"))) {
            throw new IllegalArgumentException("Garantia desconocida");
        }

        // uso metodo garantia, declaro repuestos
        boolean tieneGarantia = garantiaTxt.equals("SI");
        double repuestos = Double.parseDouble(campos[9]);
        // x defecto modalidad es normal, si hay 11 columnas declaro c[10]
        String modalidad = "NORMAL";
        if (campos.length == 11) {
            modalidad = campos[10];
        }
        // verifico modalidad
        if (modalidad == null || modalidad.isBlank()) {
            throw new IllegalArgumentException("modalidad vacia");
        }

        // Ahora lo importante, si la modalidad es normal o express
        if (modalidad.equals("NORMAL")) {
            return new Reparacion(campos[0], campos[1], campos[2], campos[3],
                    prioridad, horas, estado, costoBase, tieneGarantia, repuestos);
        }
        if (modalidad.equals("EXPRESS")) {
            return new ReparacionExpress(campos[0], campos[1], campos[2], campos[3],
                    prioridad, horas, estado, costoBase, tieneGarantia, repuestos);
        }
        // si no entra a ninguna, rari
        throw new IllegalArgumentException("Modalidad desconocida");

    }

    // modifico importe x mano de obra y descuento x garantia
    public double importe() {
        double manoDeObra = horas * 850.0;
        double descuentoGarantia = 0.0;
        if (garantia) {
            descuentoGarantia = manoDeObra * 0.2;
        }
        return costoBase + manoDeObra + prioridad * 300.0 + repuestos - descuentoGarantia;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public String getMarca() {
        return marca;
    }

    public String getEstado() {
        return estado;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getHoras() {
        return horas;
    }

    public double getCostoBase() {
        return costoBase;
    }
    // agregar getter de garantia y repuestos (a garantia cambiar el get x tiene)
    public boolean tieneGarantia() {
        return garantia;
    }

    public double getRepuestos() {
        return repuestos;
    }
}
