public class Complex {
    private double re;
    private double im;

    public Complex (double real, double imag){
        re=real;
        im=imag;
    }
    // return abs
    public double abs() {
        return Math.sqrt(re*re+im*im);
    }

    //Complex object whose value is (this + b)
    public Complex plus(Complex b) {
        double real = this.re + b.re;
        double imag = this.im + b.im;
        return new Complex(real, imag);
    }

    //Complex object whose value is (this - b)
    public Complex minus(Complex b) {
        double real = this.re - b.re;
        double imag = this.im - b.im;
        return new Complex(real, imag);
    }


    public Complex divides(Complex b) {
        double real = (this.re * b.re + this.im * b.im)/(b.re*b.re + b.im*b.im);
        double imag = (this.im * b.re - this.re * b.im)/(b.re*b.re + b.im*b.im);
        return new Complex(real, imag);

    }

    public Complex divides(double constanta) {
        double real = this.re/constanta;
        double imag = this.im/constanta;
        return new Complex(real, imag);
    }

    public Complex product(Complex b){
        double real = this.re * b.re - this.im * b.im;
        double imag = this.im * b.re + this.re * b.im;
        return new Complex(real, imag);
    }

    public Complex product(double constanta) {
        double real = this.re*constanta;
        double imag = this.im*constanta;
        return new Complex(real, imag);
    }

    // return the real or imaginary part
    public double re() { return re; }
    public double im() { return im; }

}
