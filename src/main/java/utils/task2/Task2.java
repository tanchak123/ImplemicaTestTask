package utils.task2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task2 {
    public static void main(String[] args) {
        new CostCalculator().writeOutput("task2_data1.txt");
        new CostCalculator().writeOutput("task2_data.txt");
    }
}
