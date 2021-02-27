public class WeatherDisplay implements Observer, Display{
    private double temperature;
    private double pressure;
    @Override
    public void display() {
        System.out.println("temperature:"+temperature);
        System.out.println("pressure:"+pressure);

    }

    @Override
    public void update(double temperature, double pressure) {
        this.temperature = temperature;
        this.pressure = pressure;
        display();
    }
}
