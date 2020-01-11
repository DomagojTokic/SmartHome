package hr.fer.zemris.dipl.model.interfaces;

import hr.fer.zemris.dipl.gui.observers.IObserver;

/**
 * Created by Domagoj on 2.6.2017..
 */
public interface Subject {
	
	void addObserver(IObserver observer);
	void removeObserver(IObserver observer);
	
}
