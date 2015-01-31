package fr.isima.vectoroutofrange;

import java.util.ArrayList;

/**
 * Created by LebonNic on 31/01/2015.
 */
public class Subject {

    ArrayList<Observer> observers;

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
            observer.onNotify(entity, event);
        }
    }
}
