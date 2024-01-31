import java.util.InputMismatchException;
import java.util.Scanner;

// Класс MatrixExample предоставляет пример использования класса ComplexMatrix
public class MatrixExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = getPositiveIntegerInput("Enter the number of rows for the matrix: ", scanner);
        int cols = getPositiveIntegerInput("Enter the number of columns for the matrix: ", scanner);

        ComplexMatrix matrix1 = createMatrix(rows, cols, scanner);

        // Вывод исходной матрицы
        System.out.println("\nOriginal Matrix:");
        printMatrix(matrix1);

        // Вычисление и вывод детерминанта
        double determinant = matrix1.determinant();
        System.out.println("Determinant: " + determinant);

        // Транспонирование матрицы
        ComplexMatrix transposedMatrix = matrix1.transpose();
        System.out.println("\nTransposed Matrix:");
        printMatrix(transposedMatrix);

        // Сложение матриц
        System.out.print("Creating the matrix to add to the initial one\n");
        ComplexMatrix matrix2 = createMatrix(rows, cols, scanner);
        System.out.print("\nMatrix that will be added to the initial one:\n");
        printMatrix(matrix2);
        ComplexMatrix sumMatrix = matrix1.add(matrix2);
        System.out.println("\nSum of Matrices:");
        printMatrix(sumMatrix);

        // Умножение матриц
        System.out.print("Creating the matrix to multiply with the initial one\n");
        int cols2 = getPositiveIntegerInput("Enter the number of columns for the matrix: ", scanner);
        ComplexMatrix matrix3 = createMatrix(cols, cols2, scanner);
        System.out.print("\nMatrix that will be multiplied to the initial one:\n");
        printMatrix(matrix3);
        ComplexMatrix productMatrix = matrix1.multiply(matrix3);
        System.out.println("\nProduct of Matrices:");
        printMatrix(productMatrix);
    }

    // Метод для создания матрицы заданного размера и заполнения её значениями
    private static ComplexMatrix createMatrix(int rows, int cols, Scanner scanner) {
        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("\nEnter the elements of the matrix:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Enter element at position (" + (i + 1) + ", " + (j + 1) + "): ");

                try {
                    String input = scanner.next();
                    double realPart = Double.parseDouble(input);

                    System.out.print("Enter imaginary part for complex number (or enter 0 for real number): ");
                    double imaginaryPart = scanner.nextDouble();
                    matrix.setElement(i, j, new ComplexNumber(realPart, imaginaryPart));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    j--;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number.");
                    scanner.next();
                    j--;
                }
            }
        }

        return matrix;
    }


    // Метод для получения положительного целочисленного ввода от пользователя с обработкой исключений
    private static int getPositiveIntegerInput(String prompt, Scanner scanner) {
        int input = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                input = scanner.nextInt();

                if (input <= 0) {
                    System.out.println("Please enter a positive integer.");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer.");
                scanner.next(); // consume the invalid input to avoid an infinite loop
            }
        }

        return input;
    }

    // Метод для вывода матрицы на экран
    private static void printMatrix(ComplexMatrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                System.out.print(matrix.getElement(i, j) + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
