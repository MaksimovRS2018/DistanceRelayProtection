public class OutputData {
    private Logic logic;
    private boolean[]op;
    private boolean[]str;
    private Breaker breaker;

    public void controlState(){
        op = logic.getTrip();
        str = logic.getStr();
        Charts.addAnalogData(6, 0, boolToInt(op[0]));
        Charts.addAnalogData(6, 1, boolToInt(op[1]));
        Charts.addAnalogData(6, 2, boolToInt(op[2]));
        Charts.addAnalogData(7, 0, boolToInt(str[0]));
        Charts.addAnalogData(7, 1, boolToInt(str[1]));
        Charts.addAnalogData(7, 2, boolToInt(str[2]));
        boolean actualOp = Breaker.isState();
        Charts.addAnalogData(8, 0, boolToInt(Breaker.isState()));
        for (int i = 0;i<op.length;i++){
            if (op[i] || !actualOp){
                breaker.setState(false);
            }
        }
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setBreaker(Breaker breaker) {
        this.breaker = breaker;
    }

    // функция для преобразования boolean -> int
    public int boolToInt(boolean b) {
        return Boolean.compare(b, false);
    }
}
