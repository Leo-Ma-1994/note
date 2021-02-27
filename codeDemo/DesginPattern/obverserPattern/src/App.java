public class App {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        weatherData.registerObserver(new WeatherDisplay());
        weatherData.weatherChanged(10,10);

    }
}
