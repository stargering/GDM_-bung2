public class RGB {
    private int red;
    private int green;
    private int blue;

    public int getRed() { return red; }
    public int getGreen() { return green; }
    public int getBlue() { return blue; }

    public YCbCr transformToYUV() {
        double Y = 0.299 * getRed() + 0.587 * getGreen() + 0.114 * getBlue();
        // from moodle
        double U = -0.147 * getRed() - 0.289 * getGreen() + 0.436 * getBlue();
        double V = 0.615 * getRed() - 0.515 * getGreen() - 0.1 * getBlue();
        //double V = ( getRed() * Y ) * 0.877;

        return new YCbCr();
    }
}
