public class Resistance {
    private double[] cosFirstCurrent = new double[3]; // 3 - фазы
    private double[] sinFirstCurrent = new double[3];
    private double[] cosFirstVoltage = new double[3]; // 3 - фазы
    private double[] sinFirstVoltage = new double[3];
    private Complex[] resistanceComplex = new Complex[3]; //a,b,c
    private double[] resistanceAbs = new double[3]; //a,b,c



    public void getVectorsCurrent(double[] x_harmonic1, double[] y_harmonic1) {
        cosFirstCurrent = x_harmonic1; //[A,B,C] - кос. составляющие по 1 гармонике
        sinFirstCurrent = y_harmonic1;
    }

    public void getVectorsVoltage(double[] x_harmonic1, double[] y_harmonic1) {
        cosFirstVoltage = x_harmonic1; //[A,B,C] - кос. составляющие по 1 гармонике
        sinFirstVoltage = y_harmonic1;
    }

    public void startCalculateResistance(){

        for (Phases one: Phases.values()){
            int i = one.ordinal();
            Complex I = new Complex(cosFirstCurrent[i],sinFirstCurrent[i]);
//            System.out.println("current "+I.re()+' '+I.im());
            Complex U = new Complex(cosFirstVoltage[i],sinFirstVoltage[i]);
//            System.out.println("voltage "+U.re()+' '+U.im());
            resistanceComplex[i] = U.divides(I);
//            System.out.println("resistance "+resistanceComplex[i].re()+' '+resistanceComplex[i].im());
            resistanceAbs[i] = resistanceComplex[i].abs();
        }
        ChartsXY.addAnalogData(0,0, resistanceComplex[0].re(),resistanceComplex[0].im());
        ChartsXY.addAnalogData(0,1, resistanceComplex[1].re(),resistanceComplex[1].im());
        ChartsXY.addAnalogData(0,2, resistanceComplex[2].re(),resistanceComplex[2].im());
    }
    public Complex[] getResistanceComplex() {
        return resistanceComplex;
    }

    public double[] getResistanceAbs() {
        return resistanceAbs;
    }

}
