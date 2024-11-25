package org.example;

import java.util.Arrays;
import java.util.Scanner;
import static org.example.Constant.*;

public class Utils {
    private static final String[] resultsHistory = new String[HISTORY_SIZE]; // Массив для запоминания истории 5 последних вычислений
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Ввод с клавиатуры вычисляемого математического выражения
     */
    public static String inputExpression() {
        System.out.println("Введите математическое выражение. Например, 10.5*5+1-7.1 : ");
        return scanner.next();
    }

    /**
     * Подсчет количества аргументов в математическом выражении
     */
    public static int countArgumentsNumber(String expression) {
        int argumentsNumber = 1; // Счётчик количества аргументов в математическом выражении

        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            if (isSign(symbol))
                argumentsNumber++; // Если в математическом выражении встречается очередной знак математической операции, увеличиваем счетчик аргументов на единицу
        }

        return argumentsNumber;
    }

    /**
     * Проверка является ли символ знаком математической операции
     */
    public static boolean isSign(char symbol) {
        return symbol == PLUS_SIGN || symbol == MINUS_SIGN || symbol == MULTIPLY_SIGN || symbol == DIVIDE_SIGN;
    }

    /**
     * Метод получает массив значений аргументов из строки, введенной пользователем
     */
    public static double[] parseArguments(String expression) {
        int argumentsNumber = countArgumentsNumber(expression);
        double[] arguments = new double[argumentsNumber]; // Массив для хранения аргументов

        // Получение значений аргументов из строки математического выражения, приведение их к дробному типу данных, и сохранение значений в массиве
        StringBuilder argumentAsString = new StringBuilder(); // Переменная для посимвольного накопления очередного значения аргумента из введенного пользователем матемаатического выражения
        int currentArgumentNumber = 0; // Счётчик количества аргументов, добавленных в массив аргументов
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);

            if (!isSign(symbol)) { // Если текущий символ строки - не знак математической операции, значит это одна из цифр, составляющая значение аргумента, добавляем её в переменную, в которой цифра за цифрой накапливается значение текущего аргумента
                argumentAsString.append(symbol);
            }

            if (isSign(symbol) || i == expression.length() - 1) { // Если текущий символ строки является знаком математической операции или мы дошли до последнего символа в строке - то мы закончили получать значение цифр одного из аргументов, добавляем его в массив
                double argument = Double.parseDouble(argumentAsString.toString()); // Преобразование представления числа из формата строки в тип число с дробной частью
                arguments[currentArgumentNumber++] = argument;
                argumentAsString.delete(0, argumentAsString.length()); // Очистка значения записанного в массив аргумента для накопления символов следующего значения
            }
        }

        return arguments;
    }

    /**
     * Метод получает массив знаков математических операций из строки, введенной пользователем
     */
    public static char[] parseSigns(String expression) {
        int argumentsNumber = countArgumentsNumber(expression);
        char[] signs = new char[argumentsNumber - 1]; // Массив для хранения знаков математических операций

        // Получение знаков математических операций из строки математического выражения и сохранение значений в массиве
        int currentSignNumber = 0;
        for (int i = 0; i < expression.length(); i++) {
            char symbol = expression.charAt(i);
            if (isSign(symbol)) { // Если текущий символ строки - знак математической операции - то добавляем его в массив
                signs[currentSignNumber++] = symbol;
            }
        }

        return signs;
    }

    /**
     * Метод выполняет математическую операцию sign над парой аргументов argumentOne и argumentTwo
     */
    public static double calculate(double argumentOne, char sign, double argumentTwo) {
        switch (sign) { // Выбор указанной математической операции
            case PLUS_SIGN -> { // Если указан знак + ...
                return argumentOne + argumentTwo; // ... выполняется операция сложения значения очередного аргумента с общим результатом
            }
            case MINUS_SIGN -> { // Если указан знак - ...
                return argumentOne - argumentTwo; // ... выполняется операция вычитания значения очередного аргумента с общим результатом
            }
            case MULTIPLY_SIGN -> { // Если указан знак * ...
                return argumentOne * argumentTwo; // ... выполняется операция умножения значения очередного аргумента с общим результатом
            }
            case DIVIDE_SIGN -> { // Если указан знак / ...
                return argumentOne / argumentTwo; // ... выполняется операция деления значения очередного аргумента с общим результатом
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Метод выводит на экран результат вычислений
     */
    public static void printResult(String expression, double result) {
        System.out.printf("%s=%.2f%n", expression, result); // Ограничиваем значение результата при выводе на экран до двух цифр после запятой
        System.out.println();
    }

    /**
     * Метод добавляет результат вычислений в историю вычислений
     */
    public static void addResultToHistory(String expression, double result) {
        // Освобождаем первую ячейку массива для сохранения последнего результата работы программы, сдвигая значения на 1 ячейку вперёд, начиная с конца
        for (int i = resultsHistory.length - 1; i > 0; i--) {
            resultsHistory[i] = resultsHistory[i - 1];
        }
        resultsHistory[0] = String.format("%s=%.2f", expression, result);
    }

    /**
     * Метод выводит на экран историю вычислений
     */
    public static void printHistory() {
        System.out.println("История выполненных вычислений:");
        System.out.println(Arrays.toString(resultsHistory));
        System.out.println();
    }

    /**
     * Метод выводит на экран интерактивное меню и завершает программу по команде пользователя
     */
    public static void askUserForNextCalculation() {
        System.out.println("Выполнить еще одно вычисление? (Y/N):");
        String needNextCalculation = scanner.next(); // Получение введенного значения - требуется ли калькулятору выполнить еще одно вычисление
        if (!ANSWER_YES.equalsIgnoreCase(needNextCalculation)) System.exit(0);// Завершение работы программы
    }
}
