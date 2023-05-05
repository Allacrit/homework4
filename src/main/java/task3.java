// В калькулятор добавьте возможность отменить последнюю операцию.


import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class task3 {
    private static Logger logger;

    public static void main(String[] args) throws IOException {
        logger = Logger.getLogger(task3.class.getName());
        FileHandler fh = new FileHandler("src/main/java/txt/calcLogs.txt");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        Scanner scanner = new Scanner(System.in);
        Deque<Double> number_list = new LinkedList<>();
        System.out.println(
                "Вас приветствует калькулятор!\n" +
                        "Введите оператор действия (+, -, *, /)\n" +
                        "'.' - для отмены последней операции\n" +
                        "'0' - Выход\n");

        boolean first_input = true;
        boolean calc = true;
        double number1 = 0.0;
        String operation;

        while (calc) {
            if (first_input) {
                number1 = input_number(scanner);
                first_input = false;
            }
            operation = input_operation(scanner);
            if (operation.equals("0")) {
                logger.info("Завершение работы");
                System.out.println("<<< Завершение работы >>>");
                break;
            }
            if (operation.equals(".")) {
                if (!number_list.isEmpty()) {
                    reset_operation(number_list);
                    number1 = number_list.getLast();
                } else {
                    System.out.println("Текущий результат отсутствует. Давайте начнем сначала!");
                    number1 = input_number(scanner);
                }
                operation = input_operation(scanner);
            }
            double number2 = input_number(scanner);
            double result = calculation(number1, number2, operation);
            number1 = result;
            add_element(number_list, result);
            System.out.println("Результат = " + result);
        }
    }

    public static double input_number(Scanner scanner) {
        System.out.print("Введите число: ");
        double number = 0.0;
        try {
            number = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("Вы ввели не число. Попробуйте еще раз");
            scanner.nextLine();
            input_number(scanner);
        }
        return number;
    }

    public static String input_operation(Scanner scanner) {
        System.out.println(
                        "Введите оператор действия (+, -, *, /)\n" +
                        "'.' - для отмены последней операции\n" +
                        "'0' - Выход\n");
        String oper = scanner.next().toLowerCase();
        if ((!oper.equals("+")) & (!oper.equals("-")) & (!oper.equals("*")) & (!oper.equals("/"))
                & (!oper.equals(".")) & (!oper.equals("0"))) {
            System.out.println("Неправильная операция. Попробуйте снова!");
            logger.info("Ввод неправильной операции: " + "'" + oper + "'");
            oper = input_operation(scanner);
            scanner.nextLine();
        }
        return oper;
    }

    public static void reset_operation(Deque<Double> list) {
        if (!list.isEmpty()) {
            list.removeLast();
            System.out.println("Операция отменена");
            logger.warning("Отмена последней операции");
            if (list.isEmpty()) {
                list.addLast(0.0);
            }
            System.out.println("Работаем с результатом " + list.getLast());
        }
    }

    public static double calculation(double num1, double num2, String oper) {
        String sol = num1 + " " + oper + " " + num2 + " = ";
        double result = 0.0;
        switch (oper.toLowerCase()) {
            case "+":
                result = num1 + num2;
                logger.info(sol + result);
                break;
            case "-":
                result = num1 - num2;
                logger.info(sol + result);
                break;
            case "*":
                result = num1 * num2;
                logger.info(sol + result);
                break;
            case "/":
                result = num1 / num2;
                logger.info(sol + result);
                break;
        }
        return result;
    }

    public static void add_element(Deque<Double> list, double element) {
        list.addLast(element);
    }
}
