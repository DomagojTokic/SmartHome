package hr.fer.zemris.dipl.controllers;

import hr.fer.zemris.dipl.model.Simulation;

import java.io.IOException;

/**
 * Created by Domagoj on 15.6.2017..
 */
public interface ISimulationController {
	
	void setSimulation(Simulation simulation);
	void postInitialize() throws IOException;
}
