import java.util.Objects;

// Класс ComplexMatrix представляет комплексную матрицу
public class ComplexMatrix {
    private int rows;

    private int cols;

    private Number[][] elements;

    // Конструктор принимает количество строк и столбцов для создания матрицы
    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.elements = new Number[rows][cols];
    }

    // Метод получения количества строк в матрице
    public int getRows() {
        return rows;
    }

    // Метод получения количества столбцов в матрице
    public int getCols() {
        return cols;
    }

    // Метод установки значения элемента матрицы по указанным индексам
    public void setElement(int row, int col, Number value) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid matrix indices");
        }
        elements[row][col] = value;
    }

    // Метод получения значения элемента матрицы по указанным индексам
    public Number getElement(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid matrix indices");
        }
        return elements[row][col];
    }

    // Метод инициализации матрицы заданными значениями
    public void initializeMatrix(Number[][] values) {
        if (values.length != rows || values[0].length != cols) {
            throw new IllegalArgumentException("Invalid matrix dimensions");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.elements[i][j] = values[i][j];
            }
        }
    }

    // Метод сложения матриц
    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrix dimensions must match for addition");
        }

        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.elements[i][j] = this.elements[i][j].add(other.elements[i][j]);
            }
        }
        return result;
    }

    // Метод умножения матриц
    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Number of columns in the first matrix must match the number of rows in the second matrix for multiplication");
        }

        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                result.elements[i][j] = new RealNumber(0); // Инициализация элемента матрицы перед использованием
                for (int k = 0; k < this.cols; k++) {
                    result.elements[i][j] = result.elements[i][j].add(multiplyHelper(this.elements[i][k], other.elements[k][j]));
                }
            }
        }
        return result;
    }

    // Вспомогательный метод умножения для элементов матрицы
    private Number multiplyHelper(Number num1, Number num2) {
        if (num1 instanceof ComplexNumber && num2 instanceof ComplexNumber) {
            return ((ComplexNumber) num1).multiply((ComplexNumber) num2);
        } else if (num1 instanceof RealNumber && num2 instanceof RealNumber) {
            return ((RealNumber) num1).multiply((RealNumber) num2);
        } else {
            throw new IllegalArgumentException("Matrix elements must be of the same type for multiplication");
        }
    }

    // Метод транспонирования матрицы
    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.elements[j][i] = this.elements[i][j];
            }
        }
        return result;
    }

    // Метод вычисления детерминанта
    public double determinant() {
        if (rows != cols) {
            System.out.println("Determinant is only defined for square matrices.");
            return Double.NaN; // или любое другое значение или маркер, который указывает на ошибку
        }

        return determinantHelper(this.elements);
    }

    // Вспомогательный метод для вычисления детерминанта
    private double determinantHelper(Number[][] matrix) {
        int size = matrix.length;

        if (size == 1) {
            return matrix[0][0].getValue();
        } else if (size == 2) {
            return matrix[0][0].multiply(matrix[1][1]).getValue() -
                    matrix[0][1].multiply(matrix[1][0]).getValue();
        } else {
            double det = 0;

            for (int i = 0; i < size; i++) {
                Number factor = matrix[0][i];
                Number[][] submatrix = getSubmatrix(matrix, i);
                det += factor.multiply(new RealNumber(Math.pow(-1, i))).getValue() * determinantHelper(submatrix);
            }

            return det;
        }
    }

    // Вспомогательный метод для получения подматрицы
    private Number[][] getSubmatrix(Number[][] matrix, int colToRemove) {
        int size = matrix.length - 1;
        Number[][] submatrix = new Number[size][size];

        for (int i = 1; i < matrix.length; i++) {
            int submatrixCol = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                if (j != colToRemove) {
                    submatrix[i - 1][submatrixCol++] = matrix[i][j];
                }
            }
        }

        return submatrix;
    }
}
