package utils.task2;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CostCalculator {
    private Integer TEST_NUMBER = null;
    private Integer CITIES_NUMBER = null;
    private Integer PATH_TO_FIND_NUMBER = null;
    private final List<String> pathsToCalculate = new ArrayList<>();
    private final List<City> cities = new ArrayList<>();

    public void writeOutput(String fileName) {
        String resource = CostCalculator.class.getClassLoader().getResource("").getPath();
        File file = new File(resource + fileName.split("\\.")[0] + "_output.txt");
        try (FileWriter myWriter = new FileWriter(file)) {

        initializeVariables(fileName);
        for (String pathToCalculate : pathsToCalculate) {
            String[] paths = pathToCalculate.split(" ");
            City targetCity = cities.stream().filter(city -> city.getName().equals(paths[1])).findAny().get();
            City sourceCity = cities.stream().filter(city -> city.getName().equals(paths[0])).findAny().get();
            int bestCost = getBestCost(targetCity, sourceCity);
            System.out.println("Best cost from \"" + sourceCity.getName() + "\" to \""+ targetCity.getName() + "\" equals: \"" + bestCost + "\"");
            myWriter.write(bestCost + "\n");
        }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        System.out.println(cities);
    }

    private void initializeVariables(String fileName) {
        List<String> incomeData = readFile(fileName);
        TEST_NUMBER = Integer.parseInt(incomeData.get(0));
        CITIES_NUMBER = Integer.parseInt(incomeData.get(1));

        for (int i = incomeData.size() - 1; i > 0; i--) {
            String data = incomeData.get(i);
            if (Pattern.compile("[0-9]").matcher(data).find()) {
                PATH_TO_FIND_NUMBER = Integer.parseInt(data);
                while (i < incomeData.size() - 1) pathsToCalculate.add(incomeData.get(++i));
                break;
            }
        }

        Map<String, String> citiesVariablesMap = new HashMap<>();

        for (int i = 2; i < incomeData.size() - (PATH_TO_FIND_NUMBER + 1); i++) {
            String cityName = incomeData.get(i);
            cities.add(new City(cityName));
            String data = incomeData.get(++i);

            StringBuilder variablesCollector = new StringBuilder();
            while (Pattern.compile("[0-9]").matcher(data).find() && i < incomeData.size() - (PATH_TO_FIND_NUMBER + 1)) {
                variablesCollector.append(data);
                variablesCollector.append(" ");
                data = incomeData.get(++i);
            }

            citiesVariablesMap.put(cityName, variablesCollector.toString());
            i--;
        }

        for (City city : cities) {
            List<Integer> variables = Arrays.stream(citiesVariablesMap.get(city.getName()).split(" "))
                    .map(Integer::parseInt).collect(Collectors.toList());
            city.setNeighborsNumber(variables.get(0));

            for (int i = 1; i < variables.size(); i = i + 2) {
                int cityIndex = variables.get(i) - 1;
                city.getCityCosts().put(cities.get(cityIndex), variables.get(i + 1));
            }
        }

    }

    private List<String> readFile(String fileName) {
        List<String> resultList = new ArrayList<>();
        try (InputStream inputStream = Task2.class.getClassLoader().getResourceAsStream(fileName);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                resultList.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public int getBestCost(City targetCity, City sourceCity) {
        return calculateBestCost(targetCity, sourceCity, sourceCity, new ArrayList<>(), 0);
    }


    /** Recursion function returns the best price to get from current city to another one.
     * @param targetCity end point.
     * @param sourceCity start point.
     * @param previousCity previous
     * @param previousCities points which we'd already calculated.(add and remove every new recursion)
     * @param cost previousCity cost to current one or 0 if it is enter point
     * @return
     * 0 - no way to get from current city to another
     * <p> any number > 0 - best price to get from current city to another</p>
     */
    private static int calculateBestCost(City targetCity, City sourceCity, City previousCity, List<City> previousCities, int cost) {
        // if no way it returns 0
        int bestCount = 0;
        // get all connection of previous city
        for (City currentCity : previousCity.getCityCosts().keySet()) {
            int currentCost = cost;
            String currentCityName = currentCity.getName();
            // if current city was before just keep bestCount the same and continue iteration
            if (currentCityName.equals(sourceCity.getName()) || previousCities.contains(currentCity)) continue;
            // pay for travel
            currentCost += previousCity.getCityCosts().get(currentCity);
            // if it's not ours targetCity continue iteration, with adding of current city to previous and than remove after recursion cycle over.
            if (!currentCityName.equals(targetCity.getName())) {
                previousCities.add(currentCity);
                // if the same combo already calculated(was in list or sourceCity) it can returns 0.
                int calculatedCost = calculateBestCost(targetCity, sourceCity, currentCity, previousCities, currentCost);
                if (calculatedCost != 0) currentCost = calculatedCost;
                previousCities.remove(currentCity);
            }

            // if at least one found best count will not be 0
            bestCount = bestCount == 0 ? currentCost : Math.min(bestCount, currentCost);
        }

        return bestCount;
    }
}
