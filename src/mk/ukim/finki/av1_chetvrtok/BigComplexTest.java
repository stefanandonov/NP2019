package mk.ukim.finki.np.av2;

import java.math.BigDecimal;

class BigComplex {
    BigDecimal real;
    BigDecimal imaginary;

    public BigComplex(BigDecimal real, BigDecimal imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public BigComplex add (BigComplex otherBigComplex) {
        BigDecimal newReal = this.real.add(otherBigComplex.real);
        BigDecimal newImaginary = this.imaginary.add(otherBigComplex.imaginary);
        return new BigComplex(newReal, newImaginary);
    }


    public BigComplex subtract (BigComplex otherBigComplex) {
        BigDecimal newReal = this.real.subtract(otherBigComplex.real);
        BigDecimal newImaginary = this.imaginary.subtract(otherBigComplex.imaginary);
        return new BigComplex(newReal, newImaginary);
    }

    @Override
    public String toString() {
        return real.toString() + " + (" + imaginary.toString() + "i)";
    }
}

public class BigComplexTest {

    public static void main(String[] args) {

        BigDecimal r1 = BigDecimal.valueOf(123.55);
        BigDecimal r2 =  BigDecimal.valueOf(128.33);

        BigDecimal i1 = BigDecimal.valueOf(10.555);
        BigDecimal i2 = BigDecimal.valueOf(-11.12222222222);

        BigComplex complex1 = new BigComplex(r1,i1);
        BigComplex complex2 = new BigComplex(r2,i2);

        System.out.println(complex1.toString());
        System.out.println(complex2.toString());

        System.out.println(complex1.add(complex2));
        System.out.println(complex1.subtract(complex2));

    }
}
