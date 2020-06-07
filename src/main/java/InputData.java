import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputData {

    public File comtrCfg, comtrDat;
    private BufferedReader br;
    private String line;
    private String[] lineData;
    private double[] k1;
    private double[] k2;
    private String comtradeName;
    private String nameFile;
    private SampleValues sv = new SampleValues();
    private MeanValues means = new MeanValues();
    private ArrayList<Fourie> filter = new ArrayList<Fourie>();
    private Resistance resistance = new Resistance();
    private OutputData od = new OutputData();
    private Breaker breaker = new Breaker();
    private Vectors vectors = new Vectors();
    private boolean t = false;
    private static int i = 0;
    private Logic logic = new Logic();
    private double del = 1.;
//    private FilterEmergencyComponents emerg = new FilterEmergencyComponents();


    public InputData(String nameFile) {
        this.nameFile = nameFile;
    }

    public void start() throws FileNotFoundException, NoSuchMethodException {
        int period = 80; // millisec
        double step = 0.00025;
        filter.add(new Fourie("Voltage"));
        filter.add(new Fourie("Current"));
        for (Fourie value : filter) {
//            value.set();
            value.setSv(sv);
            value.setVector(vectors);
            value.setMeans(means);
            value.setPeriod(period);
            value.setRes(resistance);
        }


        logic.setResistance(resistance);
        logic.setVector(vectors);
        logic.setRes(resistance);
        od.setLogic(logic);
        od.setBreaker(breaker);

        try {
            //путь к файлам comtrade
            comtradeName = nameFile;
            String path = "Опыты\\";
            String cfgname = path + comtradeName + ".cfg";
            String datName = path + comtradeName + ".dat";
            comtrCfg = new File(cfgname);
            comtrDat = new File(datName);
            //открываем cfg файл для получения коэф a и b для расчета y = ax+b
            br = new BufferedReader(new FileReader(comtrCfg));
            int lineNumber = 0, count = 0, numberData = 100;
            try {
                while ((line = br.readLine()) != null) {
//                System.out.println(line);
                    lineNumber++;
                    if (lineNumber == 2) {
                        //получаем количество аналоговых сигналов во 2 строке cfg файла "4,3A,1D"
                        numberData = Integer.parseInt(line.split(",")[1].replaceAll("A", ""));
                        //создаем double " массивы " с размерностью равной количеству
                        k1 = new double[numberData];
                        k2 = new double[numberData];
                    }
                    //коэф находятся на 3,4,5 строке это 5 и 6 элемент строки при парсинге
                    if (lineNumber > 2 && lineNumber < numberData + 3) {
                        k1[count] = Double.parseDouble(line.split(",")[5]);
                        k2[count] = Double.parseDouble(line.split(",")[6]);
                        count++;
                    }
                }
                count = 0;
                br = new BufferedReader(new FileReader(comtrDat));
                //получение методов из объекта sv
                List<Method> methodsSV = Arrays.asList(sv.getClass().getDeclaredMethods());

                while ((line = br.readLine()) != null) {
                    count++;
                    if ((count > 0 && count < 3500)) {
                        lineData = line.split(",");

                        if (Breaker.isState()) {
                            sv.setCurrentPhA(Double.parseDouble(lineData[5]) * k1[3] + k2[3]);
                            sv.setCurrentPhB(Double.parseDouble(lineData[6]) * k1[4] + k2[4]);
                            sv.setCurrentPhC(Double.parseDouble(lineData[7]) * k1[5] + k2[5]);
                        } else {
                            //имитация отключния выключателя
                            del = 1;
                            sv.setCurrentPhA(0);
                            sv.setCurrentPhB(0);
                            sv.setCurrentPhC(0);
                        }

                        sv.setVoltagePhA((Double.parseDouble(lineData[2]) * k1[0] + k2[0])*del);
                        sv.setVoltagePhB((Double.parseDouble(lineData[3]) * k1[1] + k2[1])*del);
                        sv.setVoltagePhC((Double.parseDouble(lineData[4]) * k1[2] + k2[2])*del);

                        for (Fourie fourie : filter) {
                            try {
                                fourie.calculate();
                            } catch (ReflectiveOperationException e) {
                                e.printStackTrace();
                            }
                        }
                        vectors.startCalculateReverseSeq();
                        logic.protect();
                        od.controlState();


                        Charts.addAnalogData(0, 0, sv.getVoltagePhA());
                        Charts.addAnalogData(0, 1, sv.getVoltagePhB());
                        Charts.addAnalogData(0, 2, sv.getVoltagePhC());

                        Charts.addAnalogData(1, 0, sv.getCurrentPhA());
                        Charts.addAnalogData(1, 1, sv.getCurrentPhB());
                        Charts.addAnalogData(1, 2, sv.getCurrentPhC());

                        Charts.addAnalogData(2, 0, means.getVoltagePhA());
                        Charts.addAnalogData(2, 1, means.getVoltagePhB());
                        Charts.addAnalogData(2, 2, means.getVoltagePhC());

                        Charts.addAnalogData(3, 0, means.getCurrentPhA());
                        Charts.addAnalogData(3, 1, means.getCurrentPhB());
                        Charts.addAnalogData(3, 2, means.getCurrentPhC());

                        Charts.addAnalogData(4, 0, vectors.getDI2());
                        Charts.addAnalogData(5, 0, resistance.getResistanceAbs()[0]);
                        Charts.addAnalogData(5, 1, resistance.getResistanceAbs()[1]);
                        Charts.addAnalogData(5, 2, resistance.getResistanceAbs()[2]);

                        //имитация снижения напряжения при КЗ
                        if (means.getCurrentPhA() > 0.4 || means.getCurrentPhB() > 0.4 || means.getCurrentPhC() > 0.4){
                            double maxValue1 = Math.max(means.getCurrentPhA(),means.getCurrentPhB());
                            double maxValue2 = Math.max(means.getCurrentPhB(),means.getCurrentPhC());
                            double maxValue = Math.max(maxValue2,maxValue1);
                            del = (0.5/maxValue);

                        } else {
                            del = 1;
                        }



                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
