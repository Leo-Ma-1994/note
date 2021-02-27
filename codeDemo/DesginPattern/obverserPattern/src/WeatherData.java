import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject{
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
