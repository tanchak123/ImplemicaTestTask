package utils;

import java.util.*;
import java.util.stream.Collectors;

public class Task1 {
    public static final Character LEFT_BRACKET_EXPRESSION = '(';
    public static final Character RIGHT_BRACKET_EXPRESSION = ')';
    public static final String COUNT = "count";

    public static Integer calculateCountOfBracketExpression(String bracketExpression) {
        bracketExpression = bracketExpression.replaceAll("[^()]", "");
        List<Character> characters = bracketExpression.chars().mapToObj(character -> (char) character).collect(Collectors.toList());
        if (characters.size() < 1) return 0;

        Map<String, Integer> indexCountMap = new HashMap<>();
        indexCountMap.put(COUNT, 0);
        int resultIndex = 1;
        // move till array is over(if startCalculation result == - 1 array is over)
        while (resultIndex != -1) resultIndex = startCalculation(characters, indexCountMap, resultIndex, resultIndex - 1);

        return indexCountMap.get(COUNT);
    }

    // main formula
    private static int startCalculation(List<Character> characters, Map<String, Integer> indexCountMap, Integer currentIndex, Integer previousIndex) {
        // if array is over return -1
        if (currentIndex >= characters.size()) return -1;

        Character currentSign = characters.get(currentIndex);
        Character previousSign = characters.get(previousIndex);

        // if previousSing '(' equals currentSign ')' increase count
        if (previousSign.equals(LEFT_BRACKET_EXPRESSION) && currentSign.equals(RIGHT_BRACKET_EXPRESSION)) {
            indexCountMap.replace(COUNT, indexCountMap.get(COUNT) + 1);
        // if currentSign and previousSing equals '(' '(' recursion begins!
        } else if (previousSign.equals(LEFT_BRACKET_EXPRESSION) && currentSign.equals(LEFT_BRACKET_EXPRESSION)) {
            //  calculate last index where '(' equals ')' or return -1 if outOfBounds
            int localIndex = startCalculation(characters, indexCountMap, currentIndex + 1, currentIndex);
            if (localIndex == -1) return -1;

            //start new calculation cycle with local index
            return startCalculation(characters, indexCountMap, localIndex, currentIndex);
        }

        // move next
        return currentIndex + 1;
    }


    // tests
    public static void main(String[] args) {
        List<String> expressions = new ArrayList<>();
        expressions.add("(()(()))"); // 4
        expressions.add("(()(()))(())"); // 6
        expressions.add("()()()()()"); //5
        expressions.add("(((())))"); //4
        expressions.add(")()"); // 1
        expressions.add("(((()((((((()))"); // 4
        expressions.add("()))))))))))))))))(((((((((((((((((((((((((((((((((()(((((((((((((((((((((((((((((((((((((((((((((((((((((()"); // 3
        expressions.add("(2 + 2) + ( 2 * (24 * 2))"); //3

        // system out result
        for (String expression : expressions) System.out.println("RESULT: " + calculateCountOfBracketExpression(expression));
    }

}
