import java.util.ArrayList;
import java.util.List;

public class Logic {


    private Resistance resistance;
    private Vectors vector;
    // y - kx < b -lower    or   y - kx > b - higher
    //The first digit indicates the stage number
    // 1 stage
    // bottom line
    private static double k1 = -0.087; //it is the same for all stages
    private static double b11 = 0; //The first digit indicates the stage number
    // the right line is the same for all stages
    private static double k2 = 4.22;
    private static double b12 = -55.28;
    // the left line is the same for all stages
    private static double k3 = -3.73;
    private static double b3 = -6.08;
    //top line
    private static double k4 = -0.087; //it is the same for all stages
    private static double b14 = 76.9;
    // 2 stage
    private static double b21 = 0;
    private static double b22 = -67.01;
    private static double b24 = 108.59;
    // 3 stage
    private static double b31 = -2;
    private static double b32 = -152.1;
    private static double b34 = 246.46;
    private static double[] stageFirst = {k1, b11, k2, b12, k3, b3, k4, b14};
    private static double[] stageSecond = {k1, b21, k2, b22, k3, b3, k4, b24};
    private static double[] stageThird = {k1, b31, k2, b32, k3, b3, k4, b34};
    private static double[] counter = {0, 0, 0};
    private static boolean[] trip = {false, false, false};
    private static boolean[] str = {false, false, false};
    private static double timeForFirstStage = 0.;
    private static double timeForSecondStage = 0.5;
    private static double timeForThirdStage = 1.5;
    private static double[] time = {timeForFirstStage, timeForSecondStage, timeForThirdStage};
    private double timeStep = 0.00025; //counter for time
    private static List<double[]> allStages = new ArrayList<>();
    private static Complex[] Z;
    private boolean checkAllPhases;
    private static int numberStage = 0;
    private Resistance res;

    Logic() {
        allStages.add(stageFirst);
        allStages.add(stageSecond);
        allStages.add(stageThird);
    }

    public void protect() {
        //если блокировки нет, то происходит определение попадания сопротивления
        //в нормальном режиме защита заблокирована, разблокировывается по DI2
        if (!vector.isBlocking() && Breaker.isState()) { //алгоритм расчета сопротивления запускается если выполнены эти условия
            res.startCalculateResistance();
//            System.out.println("kek");
            Z = resistance.getResistanceComplex();
            //проходимся по всем ступеням
            numberStage = 0;
//            actualTrip = false;
            allStages.forEach(actualStage -> {
                //проходимся по всем ФАЗАМ - СОПРОТИВЛЕНИЯМ
                checkAllPhases = false;
                for (Phases j : Phases.values()) {
//                    условие попадания в четырехугольник
//                    System.out.println("    \n");
//                    System.out.println("Фаза = " + j);
//                    System.out.println("Z[j] = " + Z[j.ordinal()].re() +' '+ Z[j.ordinal()].im() + " ступень - "+numberStage);
                    boolean condOne = conditionHigher(Z[j.ordinal()], actualStage, 0);
                    boolean condTwo = conditionHigher(Z[j.ordinal()], actualStage, 1);
                    boolean condThree = conditionHigher(Z[j.ordinal()], actualStage, 2);
                    boolean condFour = conditionLower(Z[j.ordinal()], actualStage, 3);
                    if (condOne && condTwo && condThree && condFour) {
                        checkAllPhases = true;
                    }
                }

                //если хотя бы по одной из фазе сработал пусковой орган,то
                if (checkAllPhases) {
                    //пуск
                    str[numberStage] = checkAllPhases;
                    counter[numberStage] = counter[numberStage] + timeStep;
                    double settingTime = time[numberStage];
                    if (counter[numberStage] > settingTime) {
                        //срабатывание
                        trip[numberStage] = checkAllPhases;
                    }
                } else {
                    //сбрасывание сигнала и счетчика
                    str[numberStage] = false;
                    counter[numberStage] = 0;
                }
                ++numberStage;

            });
        }
    }

    private boolean conditionHigher(Complex z, double[] coef, int numberLine) {
        //index of the array double []
        numberLine = numberLine * 2;
        boolean cond = false; //изначально - false
        double k = coef[numberLine];
        double b = coef[numberLine + 1];
        if (z.im() - k * z.re() - b >= 0) {
            cond = true;
        }
        return cond;
    }

    private boolean conditionLower(Complex z, double[] coef, int numberLine) {
        numberLine = numberLine * 2; //index of the array double []
        boolean cond = false; //изначально - false
        double k = coef[numberLine];
        double b = coef[numberLine + 1];
        if (z.im() - k * z.re() - b <= 0) {
            cond = true;
        }
        return cond;
    }

    public void setResistance(Resistance resistance) {
        this.resistance = resistance;
    }

    public void setVector(Vectors vector) {
        this.vector = vector;
    }

    public boolean[] getTrip() {
        return trip;
    }

    public boolean[] getStr() {
        return str;
    }

    public void setRes(Resistance res) {
        this.res = res;
    }
}
