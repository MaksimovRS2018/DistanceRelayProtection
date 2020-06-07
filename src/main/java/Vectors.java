import java.awt.*;
import java.util.ArrayList;

public class Vectors {
    private double[] cosFirst = new double[3]; // 3 - фазы
    private double[] sinFirst = new double[3];
    private Complex[] buffActualValueI2 = new Complex[80];
    private static Complex a = new Complex(-0.5,0.866);
    private static Complex aa = new Complex(-0.5,-0.866);
    private int count = 0;
    private boolean flag = false; // флаг для разрешния расчета аварийной составляющей
    private double DI2;
    private boolean blocking = true;
    private double timeTemporary = 0;
    private double actBlocking = 2000;
    private boolean wait = false;



    public void getVectors(double[] x_harmonic1, double[] y_harmonic1, String numberFourie) {
        cosFirst = x_harmonic1; //[A,B,C] - кос. составляющие по 1 гармонике
        sinFirst = y_harmonic1;
    }

    public void startCalculateReverseSeq(){

        Complex Ia = new Complex(cosFirst[0],sinFirst[0]);
        Complex Ib = new Complex(cosFirst[1],sinFirst[1]);
        Complex Ic = new Complex(cosFirst[2],sinFirst[2]);
        //I2 = Ia + Ib *a^2 +Ic*a
        Complex I2 = Ia.plus(Ib.product(aa)).plus(Ic.product(a)).divides(3);
        Charts.addAnalogData(9, 0, boolToInt(blocking));
        if (flag) {
            Complex oldValue = buffActualValueI2[count];
            DI2 = I2.minus(oldValue).abs() ;
            if ((DI2 > 1. || (!blocking)) & !wait){
                actBlocking = actBlocking - 0.00025;
                if (actBlocking > 0.) {
                    blocking = false;
                } else {
                    blocking = true;
                    actBlocking = 2000;
                    wait = true; //выдержка времени после срабатывания ПО
                }
            }
            if (wait) {
                timeTemporary = timeTemporary + 0.00025;
                if (timeTemporary > 1000) {
                    timeTemporary = 0.;
                    wait = false;
                }
            }

        } else {
            DI2 = 0;
        }

        buffActualValueI2[count] = I2;
        count++;
        if (count == 80) {
            count = 0;
            flag = true;
        }

    }

    public double getDI2() {
        return DI2;
    }

    public boolean isBlocking() {
        return blocking;
    }

    // функция для преобразования boolean -> int
    public int boolToInt(boolean b) {
        return Boolean.compare(b, false);
    }





}
