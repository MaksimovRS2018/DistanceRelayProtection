import java.util.ArrayList;

public class Fourie {
    private double[] A0 = new double[3]; //3 - 3 phases
    public double[] Ak1 = new double[3];
    public double[] Bk1 = new double[3];
    private ArrayList<double[]> buff = new ArrayList<double[]>();
    private ArrayList<Double> emergencyComponent = new ArrayList<Double>();
    private int count = 0;
    private SampleValues sv;
    private MeanValues means;
    private Vectors vector;
    private int number;
    private int period = 80; //количество точек за период
    private int start = 0;

    public void set() {
        for (int i = 0; i < 3; i++) {
            buff.add(new double[80]); // bufferPh current or voltage (F1 - voltage, F2 - current)
        }
    }

    Fourie(int number) {
        this.number = number;
    }

    public void calculate() {
        for (int i = 3 * number; i < (3 * number + 3); i++) { //3 - 3 phases
            int numberBuff = i % 3;
            double[] actual_buf = buff.get(numberBuff); //i%3 - number buff 0,1,2 for each Fourie
            double sumPh = sv.get(i) - actual_buf[count];
            //Алгоритм фурье: постоянная составляющая + 1 гармоника
            //расчет постоянной составляющей, возникает при КЗ. В норм. режиме равна нулю -> интеграл синусоиды = 0
            A0[numberBuff] = A0[numberBuff] + sumPh / period;
            //расчет cos и sin составляющей для первой гармоники
            Ak1[numberBuff] = Ak1[numberBuff] + 2 * (Math.cos(count * 2 * Math.PI / period) * sumPh) / period;
            Bk1[numberBuff] = Bk1[numberBuff] + 2 * (Math.sin(count * 2 * Math.PI / period) * sumPh) / period;
            //расчет действующего значения для 1 гармоники по cos и sin составляющей
            double Ck1 = Math.sqrt((Math.pow(Ak1[numberBuff], 2) + Math.pow(Bk1[numberBuff], 2)) / 2);
            double x = Math.sqrt(Math.pow(Ck1, 2) + Math.pow(A0[numberBuff], 2));
            //суммарная составляющая
            actual_buf[count] = sv.get(i);
            buff.set(numberBuff, actual_buf);
            //устанавливаем действующие значения для построения графика
            means.set(i, x);
        }
        count++;
        if (count == period) {
            start = 1;
            count = 0;
        }

//        vector.getVectorsFirstAndSecondHarmonic(Ak1, Bk1, Ak2, Bk2, number);

    }


    public void setSv(SampleValues sv) {
        this.sv = sv;
    }

    public void setMeans(MeanValues means) {
        this.means = means;
    }

    public Vectors getVector() {
        return vector;
    }

    public void setVector(Vectors vector) {
        this.vector = vector;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public ArrayList<double[]> getBuff() {
        return buff;
    }

}


