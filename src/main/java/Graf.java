public class Graf {

    public static double[] Xx = {-69.3,-1.43,35,92.5,-69.3,94};   //задаем координаты по Х для построения прямых
    public static double[] XxII = {-31,-1.43,15.5,40.77,-31,40.77};   //задаем координаты по Х для построения прямых
    public static double[] XxIII = {-23.43,-1.43,13,30.67,-23.43,30.67};   //задаем координаты по Х для построения прямых

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

    public static void GrafDraw(){
        for (int i=0; i<Xx.length-1;i++){
            for (double x=Xx[i]; x<=Xx[i+1]; x=x+0.1) {
                double y=0;
                switch (i){      //каждой прямой характеристики соответствует своя функция y(x)
                     //каждой прямой характеристики соответствует своя функция y(x)
                    case (0):
                        y = k3 * x + b3;
                        break;
                    case (1):
                        y= k1 *  x  + b31;
                        break;
                    case (2):
                        y = k2 * x + b32;
                        break;
                    case (4):
                        y = k4 * x + b34;
                        break;
                    default:
                        break;
                }
                ChartsXY.addAnalogData(0, 3, x, y);  //вывод графика
            }
        }
        for (int i=0; i<XxII.length-1;i++){
            for (double xII=XxII[i]; xII<=XxII[i+1]; xII=xII+0.1) {
                double yII=0;
                switch (i){       //каждой прямой характеристики соответствует своя функция y(x)
                    case (0):
                        yII = k3 * xII + b3;
                        break;
                    case (1):
                        yII = k1 * xII + b21;
                        break;
                    case (2):
                        yII = k2 * xII + b22;
                        break;
                    case (4):
                        yII = k4 * xII +b24;
                        break;
                    default:
                        break;
                }
                ChartsXY.addAnalogData(0, 4, xII, yII);  //вывод графика
            }
        }
        for (int i=0; i<XxIII.length-1;i++){
            for (double xII=XxIII[i]; xII<=XxIII[i+1]; xII=xII+0.1) {
                double yII=0;
                switch (i){       //каждой прямой характеристики соответствует своя функция y(x)
                    case (0):
                        yII = k3 * xII + b3;
                        break;
                    case (1):
                        yII = k1 * xII + b11;
                        break;
                    case (2):
                        yII = k2 * xII + b12;
                        break;
                    case (4):
                        yII = k4 * xII + b14;
                        break;
                    default:
                        break;
                }
                ChartsXY.addAnalogData(0, 5, xII, yII);  //вывод графика
            }
        }
    }
}
