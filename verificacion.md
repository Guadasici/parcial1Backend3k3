# Verificación

## Contrato

El modelo inicial usa 8 columnas y el evolucionado 11:

```text
id,cliente,dispositivo,marca,prioridad,horas,estado,costoBase
id,cliente,dispositivo,marca,prioridad,horas,estado,costoBase,garantia,repuestos,modalidad
```

No hay columnas redundantes: `dispositivo` sirve para estadísticas por equipo, `marca` para estadísticas por fabricante, `horas` para mano de obra, `prioridad` para cargo operativo, `garantia` para descuento, `repuestos` para costo de piezas y `modalidad` para seleccionar el comportamiento normal o express.

## Comportamiento esperado

La solución debe procesar válidas, descartar `CANCELADA`, informar inválidas y continuar. Debe mostrar cantidades, facturación, reparaciones entregadas y agrupamiento por dispositivo. `NORMAL` crea `Reparacion`; `EXPRESS` crea `ReparacionExpress` y aplica 25% sobre el importe común.

Ejecutar `mvn test` en ambos proyectos, fusionar `tests-nuevos` con la solución y ejecutar ambos `Main`.

El control negativo debe demostrar que el inicial no acepta el encabezado ampliado ni los estados/datos nuevos. Cubrir modalidad inválida, repuesto negativo, garantía inválida, estado desconocido, cancelación con campos inválidos y continuidad después de errores. No incluir `target/` en una entrega.
