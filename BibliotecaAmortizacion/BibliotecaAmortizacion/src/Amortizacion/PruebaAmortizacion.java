
package Amortizacion;

import java.math.BigDecimal;

public class PruebaAmortizacion {

    public static void main(String[] args) {

        MotorAmortizacion motor = new MotorAmortizacion();

        BigDecimal monto = new BigDecimal("10000");

        int[] plazos = {12, 24, 36, 48, 60};

        for (int plazo : plazos) {

            TablaAmortizacion tabla =
                    motor.calcularTabla(monto, plazo);

            System.out.println("\nPLAZO: " + plazo + " MESES");

            System.out.println("Mensualidad: $"
                    + tabla.getCuotaMensual()
                    .setScale(2, BigDecimal.ROUND_HALF_UP));

            System.out.println("Total intereses: $"
                    + tabla.getTotalIntereses()
                    .setScale(2, BigDecimal.ROUND_HALF_UP));

            System.out.println("Total pagado: $"
                    + tabla.getTotalPagado()
                    .setScale(2, BigDecimal.ROUND_HALF_UP));

            System.out.println("Numero de cuotas: "
                    + tabla.getCuotas().size());
        }
    }
}
