/**
 * 具体的主题实现类
 */
import java.util.ArrayList;
public class WeatherData implements Subject{
    //存放注册的观察者对象
    private ArrayList<Observer> observerList;

    private double temperature;
    private double pressure;

    public WeatherData(){
        observerList = new ArrayList();
    }
    @Override
    public void registerObserver(Observer observer) {
        if(observerList != null && !observerList.contains(observer)){
            observerList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if(observerList.contains(observer)){
            observerList.remove(observer);
        }
    }

    //状态改变时，通知观察者
    @Override
    public void notifyObserver() {
        for (Observer o:
                observerList) {
            o.update(temperature, pressure);
        }
    }

    public void weatherChanged(double temperature, double pressure){
        this.temperature = temperature;
        this.pressure = pressure;
        notifyObserver();
    }
}
