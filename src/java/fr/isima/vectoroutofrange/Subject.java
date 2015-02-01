package fr.isima.vectoroutofrange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LebonNic on 31/01/2015.
 */
public class Subject {

    List<Observer> observers;

    public Subject(){
        observers = new ArrayList<Observer>();
    }

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(Object entity, Object event){
        for(Observer observer : observers){
            observer.update(entity, event);
        }
    }
}
