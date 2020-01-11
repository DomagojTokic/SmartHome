package hr.fer.zemris.dipl.model;

import hr.fer.zemris.dipl.model.appliances.enums.ApplianceEnum;
import hr.fer.zemris.dipl.model.sensors.enums.SensorEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Domagoj on 22.4.2017..
 */
public class Simulation implements Serializable, Cloneable {

    private StringProperty name;

    private List<SensorEnum> sensorEnums;

    private List<ApplianceEnum> applianceEnums;

    private List<RuleProcessPair> ruleProcessPairs;

    public Simulation(String name) {
        this.name = new SimpleStringProperty(name);
        sensorEnums = new ArrayList<>();
        ruleProcessPairs = new ArrayList<>();
        applianceEnums = new ArrayList<>();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public List<SensorEnum> getSensorEnums() {
        return sensorEnums;
    }

    public void setSensors(List<SensorEnum> sensorEnums) {
        this.sensorEnums.clear();
        this.sensorEnums.addAll(sensorEnums);
    }

    public List<ApplianceEnum> getApplianceEnums() {
        return applianceEnums;
    }

    public void setApplianceEnums(List<ApplianceEnum> applianceEnums) {
        this.applianceEnums.clear();
        this.applianceEnums.addAll(applianceEnums);
    }

    public List<RuleProcessPair> getRuleProcessPairs() {
        return ruleProcessPairs;
    }

    public void addRuleProcessPair(RuleProcessPair ruleProcessPair) {
        ruleProcessPairs.add(ruleProcessPair);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Simulation))
            return false;
        Simulation that = (Simulation) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSensorEnums(), that.getSensorEnums()) &&
                Objects.equals(getApplianceEnums(), that.getApplianceEnums()) &&
                Objects.equals(getRuleProcessPairs(), that.getRuleProcessPairs());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSensorEnums(), getApplianceEnums(), getRuleProcessPairs());
    }
}
