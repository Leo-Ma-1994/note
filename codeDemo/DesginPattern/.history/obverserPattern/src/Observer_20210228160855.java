/**
 * 抽象观察者
 */
public interface Observer {
    /**
     * 当观察者调用notifyObserver时，观察者的update会被回调
     * @param temperature
     * @param pressure
     */
    public void update(double temperature, double pressure);
}
