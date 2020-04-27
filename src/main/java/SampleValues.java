public class SampleValues {

    private double voltagePhA;
    private  double voltagePhB;
    private  double voltagePhC;
    private double currentPhA;
    private  double currentPhB;
    private  double currentPhC;

    public double get(int numberFunction){
        if (numberFunction==0)
            return  getVoltagePhA();
        if (numberFunction==1) {
            return getVoltagePhB();
        }
        if (numberFunction==2) {
            return getVoltagePhC();
        }
        if (numberFunction==3) {
            return getCurrentPhA();
        }
        if (numberFunction==4) {
            return getCurrentPhB();
        }
        if (numberFunction==5) {
            return getCurrentPhC();
        }else {
            return 0;
        }

    }

    public void set(int phasa, double value){

        if (phasa==0) setVoltagePhA(value);
        if (phasa==1) setVoltagePhB(value);
        if (phasa==2) setVoltagePhC(value);
        if (phasa==3) setCurrentPhA(value);
        if (phasa==4) setCurrentPhB(value);
        if (phasa==5) setCurrentPhC(value);

    }

    public double getVoltagePhA() {
        return voltagePhA;
    }

    public void setVoltagePhA(double voltagePhA) {
        this.voltagePhA = voltagePhA;
    }

    public double getVoltagePhB() {
        return voltagePhB;
    }

    public void setVoltagePhB(double voltagePhB) {
        this.voltagePhB = voltagePhB;
    }

    public double getVoltagePhC() {
        return voltagePhC;
    }

    public void setVoltagePhC(double voltagePhC) {
        this.voltagePhC = voltagePhC;
    }

    public double getCurrentPhA() {
        return currentPhA;
    }

    public void setCurrentPhA(double currentPhA) {
        this.currentPhA = currentPhA;
    }

    public double getCurrentPhB() {
        return currentPhB;
    }

    public void setCurrentPhB(double currentPhB) {
        this.currentPhB = currentPhB;
    }

    public double getCurrentPhC() {
        return currentPhC;
    }

    public void setCurrentPhC(double currentPhC) {
        this.currentPhC = currentPhC;
    }
}
