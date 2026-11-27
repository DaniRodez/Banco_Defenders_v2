
package Amortizacion;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa una cuota individual de una tabla de amortizacion.
 *
 * Contiene el desglose del pago mensual, capital, intereses
 * y saldo pendiente del prestamo.
 * Diego Uriel Esquivel Vazquez
 * @author BancoDefenders
 */
public class CuotaAmortizacion {

    private final int numero;
    private final BigDecimal saldoInicial;
    private final BigDecimal mensualidad;
    private final BigDecimal capital;
    private final BigDecimal interes;
    private final BigDecimal saldoPendiente;

    public CuotaAmortizacion(
            int numero,
            BigDecimal saldoInicial,
            BigDecimal mensualidad,
            BigDecimal capital,
            BigDecimal interes,
            BigDecimal saldoPendiente) {

        if (numero <= 0) {
            throw new IllegalArgumentException(
                    "El numero de cuota debe ser positivo.");
        }

        this.numero = numero;
        this.saldoInicial = Objects.requireNonNull(saldoInicial);
        this.mensualidad = Objects.requireNonNull(mensualidad);
        this.capital = Objects.requireNonNull(capital);
        this.interes = Objects.requireNonNull(interes);
        this.saldoPendiente = Objects.requireNonNull(saldoPendiente);
    }

    public int getNumero() {
        return numero;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public BigDecimal getMensualidad() {
        return mensualidad;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public BigDecimal getSaldoPendiente() {
        return saldoPendiente;
    }
}
