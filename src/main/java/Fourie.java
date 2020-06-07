import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fourie {
    private double[] A0 = new double[3]; //3 - 3 phases
    public double[] Ak1 = new double[3];
    public double[] Bk1 = new double[3];
    private ArrayList<double[]> buff = new ArrayList<double[]>();
    private int count = 0;
    private SampleValues sv;
    private MeanValues means;
    private Vectors vector;
    private String number;
    private static int period = 80; //количество точек за период
    private Resistance res;

    Fourie(String number) {
        this.number = number;
        for (Phases onePhasa : Phases.values()) {
            buff.add(new double[80]); // bufferPh current or voltage
        }
    }

    public void calculate() throws ReflectiveOperationException {

        for (Phases onePhasa : Phases.values()) { //проходимся по enum
            Method onePhasaMet = sv.getClass().getDeclaredMethod("get" + number + onePhasa); //получение метода из объекта sv
            int i = onePhasa.ordinal();
            double actualSV = (double) onePhasaMet.invoke(sv);
            double[] actual_buf = buff.get(i);
            double sumPh = actualSV - actual_buf[count];
            //Алгоритм фурье: постоянная составляющая + 1 гармоника
            //расчет постоянной составляющей, возникает при КЗ. В норм. режиме равна нулю -> интеграл синусоиды = 0
            A0[i] = A0[i] + sumPh / period;
            //расчет cos и sin составляющей для первой гармоники
            Ak1[i] = Ak1[i] + 2 * (Math.cos(count * 2 * Math.PI / period) * sumPh) / period;
            Bk1[i] = Bk1[i] + 2 * (Math.sin(count * 2 * Math.PI / period) * sumPh) / period;
            //расчет действующего начения для 1 гармоники по cos и sin составляющей
            double Ck1 = Math.sqrt((Math.pow(Ak1[i], 2) + Math.pow(Bk1[i], 2)) / 2);
            double x = Math.sqrt(Math.pow(Ck1, 2) + Math.pow(A0[i], 2));
            //для фазы, но она не нужна
//            double Phase = Math.atan(Bk1[i] / Ak1[i]);
//            if (Bk1[i]/Ak1[i] < 0) {
//                if (Ak1[i] <0) {
//                    //2 четверть
//                    System.out.println(number + " " + onePhasa + " 1 " + x + ' ' + (180 + Phase * 180 / 3.13));
//                } else {
//                    //4 четверть
//                    System.out.println(number + " " + onePhasa + " 2 " + x + ' ' + (360+ Phase * 180 / 3.13));
//                }
//            } else {
//                if (Ak1[i] <0 && Bk1[i] <0) {
//                    //3 четверть
//                    System.out.println(number + " " + onePhasa + " 1 " + x + ' ' + (180 + Phase * 180 / 3.13));
//                } else {
//                    //1 четверть
//                    System.out.println(number + " " + onePhasa + " 3 " + x + ' ' + (Phase * 180 / 3.13));
//                }
//
//            }
            actual_buf[count] = actualSV;
            buff.set(i, actual_buf);
            //достаем метод из mean
            Method methodsMEAN = means.getClass().getMethod("set" + number + onePhasa, double.class);
            methodsMEAN.setAccessible(true);
            methodsMEAN.invoke(means, x);

        }

        if (number.equals("Current")) {
            vector.getVectors(Ak1, Bk1, number);
            res.getVectorsCurrent(Ak1, Bk1);
        } else  {
            res.getVectorsVoltage(Ak1, Bk1);
        }

        count++;
        if (count == period) {
            count = 0;
        }
    }


    public void setSv(SampleValues sv) {
        this.sv = sv;
    }

    public void setMeans(MeanValues means) {
        this.means = means;
    }

    public void setVector(Vectors vector) {
        this.vector = vector;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setRes(Resistance res) {
        this.res = res;
    }


}


