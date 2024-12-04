package org.example;

import java.util.List;

import static org.example.Utils.*;

public class Calculator {
    public void run() {
        do {
            String expression = inputExpression(); // Ввод с клавиатуры математического выражения в виде строки
            List<Double> arguments = parseArguments(expression); // Получение из строки математического выражения аргументов в виде массива чисел с дробной частью
            List<Character> signs = parseSigns(expression); // Получение из строки математического выражения знаков математических операций

            double result = arguments.get(0); // Объявление переменной для хранения результата вычислений и инициализируем её значением первого аргумента
            for (int i = 1; i < arguments.size(); i++) { // Последовательное выполнение математических операций над каждым из последующих аргументов, начиная со второго
                double argument = arguments.get(i);
                result = calculate(result, signs.get(i - 1), argument); // Выполнение очередной математической операции над значениями промежуточного результата и следующего аргумента
            }

            printResult(expression, result); // Вывод на экран результата вычислений
            addResultToHistory(expression, result); // Сохранение результата в истории результатов
            printHistory(); // Вывод на экран содержимого истории результатов

            askUserForNextCalculation(); // Вывод интерактивного меню выбора между выполнением еще одного вычисления или завершением работы программы
        } while (true);
    }
}