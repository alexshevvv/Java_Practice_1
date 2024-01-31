import java.util.Objects;

// Абстрактный класс Number является базовым для действительных и комплексных чисел
abstract class Number {

    public abstract Number add(Number other); // Метод сложения для чисел

    public abstract Number multiply(Number other); // Метод умножения для чисел

    public abstract double getValue(); // Метод получения значения числа
}

// Класс RealNumber представляет действительное число
class RealNumber extends Number {
    private double value;

    // Конструктор принимает действительное значение
    public RealNumber(double value) {
        this.value = value;
    }

    // Метод получения значения действительного числа
    public double getValue() {
        return value;
    }

    // Метод сложения для действительных чисел
    @Override
    public Number add(Number other) {
        if (other instanceof RealNumber) {
            RealNumber realOther = (RealNumber) other;
            return new RealNumber(this.value + realOther.value);
        } else if (other instanceof ComplexNumber) {
            ComplexNumber complexOther = (ComplexNumber) other;
            return new ComplexNumber(this.value + complexOther.getReal(), complexOther.getImaginary());
        } else {
            throw new IllegalArgumentException("Invalid addition operation for different number types");
        }
    }

    // Метод умножения для действительных чисел
    @Override
    public Number multiply(Number other) {
        if (other instanceof RealNumber) {
            RealNumber realOther = (RealNumber) other;
            return new RealNumber(this.value * realOther.value);
        } else if (other instanceof ComplexNumber) {
            ComplexNumber complexOther = (ComplexNumber) other;
            double newReal = this.value * complexOther.getReal();
            double newImaginary = this.value * complexOther.getImaginary();
            return new ComplexNumber(newReal, newImaginary);
        } else {
            throw new IllegalArgumentException("Invalid multiplication operation for different number types");
        }
    }

    // Переопределение методов для корректного сравнения и вывода
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RealNumber that = (RealNumber) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

// Класс ComplexNumber представляет комплексное число
public class ComplexNumber extends Number {
    private double real;

    private double imaginary;

    // Конструктор принимает действительную и мнимую части
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    // Метод получения значения действительной части
    public double getReal() {
        return real;
    }

    // Метод получения значения мнимой части
    public double getImaginary() {
        return imaginary;
    }

    // Метод сложения для комплексных чисел
    @Override
    public Number add(Number other) {
        if (other instanceof ComplexNumber) {
            ComplexNumber complexOther = (ComplexNumber) other;
            return new ComplexNumber(this.real + complexOther.real, this.imaginary + complexOther.imaginary);
        } else if (other instanceof RealNumber) {
            RealNumber realOther = (RealNumber) other;
            return new ComplexNumber(this.real + realOther.getValue(), this.imaginary);
        } else {
            throw new IllegalArgumentException("Invalid addition operation for different number types");
        }
    }

    // Метод умножения для комплексных чисел
    @Override
    public Number multiply(Number other) {
        if (other instanceof ComplexNumber) {
            ComplexNumber complexOther = (ComplexNumber) other;
            double newReal = this.real * complexOther.real - this.imaginary * complexOther.imaginary;
            double newImaginary = this.real * complexOther.imaginary + this.imaginary * complexOther.real;
            return new ComplexNumber(newReal, newImaginary);
        } else if (other instanceof RealNumber) {
            RealNumber realOther = (RealNumber) other;
            return new ComplexNumber(this.real * realOther.getValue(), this.imaginary * realOther.getValue());
        } else {
            throw new IllegalArgumentException("Invalid multiplication operation for different number types");
        }
    }

    // Переопределение методов для корректного сравнения и вывода
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.real, real) == 0 && Double.compare(that.imaginary, imaginary) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }

    @Override
    public String toString() {
        return real + (imaginary >= 0 ? "+" : "-") + Math.abs(imaginary) + "i";
    }

    // Возвращаем действительную часть комплексного числа
    @Override
    public double getValue() {
        return this.real;
    }
}
