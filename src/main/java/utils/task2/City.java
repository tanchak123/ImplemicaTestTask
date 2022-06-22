package utils.task2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class City {
    private final String name;
    private final Map<City, Integer> cityCosts = new HashMap<>();
    private Integer neighborsNumber;


    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getNeighborsNumber() {
        return neighborsNumber;
    }

    public void setNeighborsNumber(Integer neighborsNumber) {
        this.neighborsNumber = neighborsNumber;
    }

    public Map<City, Integer> getCityCosts() {
        return cityCosts;
    }

    @Override
    public String toString() {
        HashMap<String, Integer> cityCostsForString = new HashMap<>();
        for (City city : cityCosts.keySet()) {
            cityCostsForString.put(city.getName(), cityCosts.get(city));
        }

        return "City{" +
                "name='" + name + '\'' +
                ", cityCosts=" + cityCosts.keySet().stream().collect(Collectors.toMap(City::getName, cityCosts::get)) +
                ", neighborsNumber=" + neighborsNumber +
                '}';
    }
}
