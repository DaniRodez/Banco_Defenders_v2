
package Amortizacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Almacena los resultados de una tabla de amortizacion.
 * Diego Uriel Esquivel Vazquez
 * @author BancoDefenders
 */
public class TablaAmortizacion {

    private final BigDecimal montoPrestamo;
    private final BigDecimal tasaAnual;
    private final BigDecimal tasaMensual;
    private final int plazoMeses;
    private final BigDecimal cuotaMensual;
    private final BigDecimal totalIntereses;
    private final BigDecimal totalPagado;
    private final List<CuotaAmortizacion> cuotas;

    public TablaAmortizacion(
            BigDecimal montoPrestamo,
            BigDecimal tasaAnual,
            BigDecimal tasaMensual,
            int plazoMeses,
            BigDecimal cuotaMensual,
            BigDecimal totalIntereses,
            BigDecimal totalPagado,
            List<CuotaAmortizacion> cuotas) {

        this.montoPrestamo = montoPrestamo;
        this.tasaAnual = tasaAnual;
        this.tasaMensual = tasaMensual;
        this.plazoMeses = plazoMeses;
        this.cuotaMensual = cuotaMensual;
        this.totalIntereses = totalIntereses;
        this.totalPagado = totalPagado;

        this.cuotas = Collections.unmodifiableList(
                new ArrayList<>(cuotas));
    }

    public BigDecimal getMontoPrestamo() {
        return montoPrestamo;
    }

    public BigDecimal getTasaAnual() {
        return tasaAnual;
    }

    public BigDecimal getTasaMensual() {
        return tasaMensual;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public BigDecimal getCuotaMensual() {
        return cuotaMensual;
    }

    public BigDecimal getTotalIntereses() {
        return totalIntereses;
    }

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public List<CuotaAmortizacion> getCuotas() {
        return cuotas;
    }
}
