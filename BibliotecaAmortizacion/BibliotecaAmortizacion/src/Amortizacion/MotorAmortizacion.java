
package Amortizacion;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Motor encargado de realizar los calculos
 * de amortizacion de prestamos.
 *
 * Utiliza el sistema de cuota fija (frances).
 * 
 * @author BancoDefenders
 */
public class MotorAmortizacion {

    private static final BigDecimal TASA_ANUAL =
            new BigDecimal("0.129");

    private static final BigDecimal DOCE =
            new BigDecimal("12");

    private static final MathContext MC =
            new MathContext(20, RoundingMode.HALF_UP);

    private static final int[] PLAZOS_PERMITIDOS =
            {12, 24, 36, 48, 60};

    /**
     * Calcula la mensualidad fija de un prestamo.
     * 
     * @param monto
     * @param plazoMeses
     * @return 
     */
    public BigDecimal calcularCuota(
            BigDecimal monto, int plazoMeses) {

        validarDatos(monto, plazoMeses);

        BigDecimal tasaMensual =
                TASA_ANUAL.divide(DOCE, MC);

        BigDecimal factor =
                BigDecimal.ONE.add(tasaMensual, MC)
                .pow(plazoMeses, MC);

        BigDecimal denominador =
                factor.subtract(BigDecimal.ONE, MC);

        return monto.multiply(tasaMensual, MC)
                .multiply(factor, MC)
                .divide(denominador, MC);
    }

    /**
     * Genera la tabla completa de amortizacion.
     *
     * @param monto cantidad solicitada
     * @param plazoMeses plazo del prestamo
     * @return tabla con todas las cuotas
     */
    public TablaAmortizacion calcularTabla(
            BigDecimal monto, int plazoMeses) {

        validarDatos(monto, plazoMeses);

        BigDecimal tasaMensual =
                TASA_ANUAL.divide(DOCE, MC);

        BigDecimal cuotaMensual =
                calcularCuota(monto, plazoMeses);

        BigDecimal saldo = monto;
        BigDecimal totalIntereses = BigDecimal.ZERO;
        BigDecimal totalPagado = BigDecimal.ZERO;

        List<CuotaAmortizacion> cuotas =
                new ArrayList<>();

        for (int numero = 1;
                numero <= plazoMeses; numero++) {

            BigDecimal saldoInicial = saldo;

            BigDecimal interes =
                    saldoInicial.multiply(tasaMensual, MC);

            BigDecimal capital;
            BigDecimal mensualidad;

            if (numero == plazoMeses) {

                // La ultima cuota liquida el saldo restante.
                capital = saldoInicial;
                mensualidad = capital.add(interes, MC);
                saldo = BigDecimal.ZERO;

            } else {

                mensualidad = cuotaMensual;
                capital = mensualidad.subtract(interes, MC);
                saldo = saldoInicial.subtract(capital, MC);
            }

            totalIntereses =
                    totalIntereses.add(interes, MC);

            totalPagado =
                    totalPagado.add(mensualidad, MC);

            CuotaAmortizacion cuota =
                    new CuotaAmortizacion(
                            numero,
                            saldoInicial,
                            mensualidad,
                            capital,
                            interes,
                            saldo);

            cuotas.add(cuota);
        }

        return new TablaAmortizacion(
                monto,
                TASA_ANUAL,
                tasaMensual,
                plazoMeses,
                cuotaMensual,
                totalIntereses,
                totalPagado,
                cuotas);
    }

    /**
     * Valida el monto y el plazo del prestamo.
     */
    private void validarDatos(
            BigDecimal monto, int plazoMeses) {

        if (monto == null ||
                monto.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "El monto debe ser mayor que cero.");
        }

        boolean plazoValido = false;

        for (int plazo : PLAZOS_PERMITIDOS) {

            if (plazo == plazoMeses) {
                plazoValido = true;
                break;
            }
        }

        if (!plazoValido) {
            throw new IllegalArgumentException(
                    "El plazo debe ser 12, 24, 36, 48 o 60 meses.");
        }
    }
}
