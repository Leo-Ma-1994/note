public class EcoState extends StateBase{
    @Override
    public void switchToPARKING() {
        System.out.println("ECO切换到P档");

    }

    @Override
    public void switchToECO() {
        System.out.println("ECO档位不变");
    }

    @Override
    public String toString() {
        return "ECO_STATE";
    }
}
