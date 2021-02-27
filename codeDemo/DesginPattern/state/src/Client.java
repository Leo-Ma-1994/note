public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        //切档位
        context.switchToECO();
        context.switchToPARKING();

        context.switchToECO();
        context.switchToECO();


    }
}
