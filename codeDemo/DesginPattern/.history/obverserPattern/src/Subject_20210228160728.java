/**
 * 抽象主题（抽象的被观察者）
 */
public interface Subject {
    /**
     * 注册观察者
     * @param observer
     */
    public void registerObserver(Observer observer);
    /**
     * 移除观察者
     * @param observer
     */
    public void removeObserver(Observer observer);
    /**
     * 状态发生变化时，通知观察者
     */
    public void notifyObserver();

}
