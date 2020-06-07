import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, NoSuchMethodException {
        Charts.createAnalogChart("U мгн", 0);
        Charts.addSeries("Фаза А", 0, 0);
        Charts.addSeries("Фаза B", 0, 1);
        Charts.addSeries("Фаза С", 0, 2);
        Charts.createAnalogChart("I мгн", 1);

        Charts.addSeries("Фаза А", 1, 0);
        Charts.addSeries("Фаза B", 1, 1);
        Charts.addSeries("Фаза С", 1, 2);

        Charts.createAnalogChart("U дейс", 2);
        Charts.addSeries("Фаза А", 2, 0);
        Charts.addSeries("Фаза B", 2, 1);
        Charts.addSeries("Фаза С", 2, 2);

        Charts.createAnalogChart("I дейс", 3);
        Charts.addSeries("Фаза А",3, 0);
        Charts.addSeries("Фаза B", 3, 1);
        Charts.addSeries("Фаза С", 3, 2);

        Charts.createAnalogChart("DI2", 4);
        Charts.addSeries("DI2", 4, 0);

        Charts.createAnalogChart("R", 5);
        Charts.addSeries("Фаза А", 5, 0);
        Charts.addSeries("Фаза B", 5, 1);
        Charts.addSeries("Фаза С", 5, 2);

        Charts.createAnalogChart("Op", 6);
        Charts.addSeries("1 Op", 6, 0);
        Charts.addSeries("2 Op", 6, 1);
        Charts.addSeries("3 Op", 6, 2);

        Charts.createAnalogChart("Str", 7);
        Charts.addSeries("1 Str", 7, 0);
        Charts.addSeries("2 Str", 7, 1);
        Charts.addSeries("3 Str", 7, 2);

        Charts.createAnalogChart("StateBreaker", 8);
        Charts.addSeries("State", 8, 0);
        Charts.createAnalogChart("Blk", 8);
        Charts.addSeries("Blk", 9, 0);

        ChartsXY.createAnalogChart(0);
        ChartsXY.addSeries("Za",0,0);
        ChartsXY.addSeries("Zb",0,1);
        ChartsXY.addSeries("Zc",0,2);
        ChartsXY.addSeries("GI",0,3);
        ChartsXY.addSeries("GII",0,4);
        ChartsXY.addSeries("GIII ",0,5);

        //Класс, необходимый для построения характеристики срабатывания
        Graf.GrafDraw();
        InputData inD1 = new InputData("KZ7");
        inD1.start();

    }
}
