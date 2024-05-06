public class RGB {
    int r, g, b;

    public RGB(int rgb) {
        this.r = (rgb >> 16) & 0xFF;
        this.g = (rgb >> 8) & 0xFF;
        this.b = rgb & 0xFF;
    }

    public int getRed() { return r; }
    public int getGreen() { return g; }
    public int getBlue() { return b; }

    public YCbCr transformToYCbCr() {
        double Y = 0.299 * getRed() + 0.587 * getGreen() + 0.114 * getBlue();
        double U = -0.147 * getRed() - 0.289 * getGreen() + 0.436 * getBlue();
        double V = 0.615 * getRed() - 0.515 * getGreen() - 0.1 * getBlue();
        return new YCbCr(Y, U, V);
    }
}
