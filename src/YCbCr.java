public class YCbCr {
    double y, cb, cr;

    public YCbCr(double y, double cb, double cr) {
        this.y = y;
        this.cb = cb;
        this.cr = cr;
    }

    public YCbCr changeBrightness(double brightness) {
        return new YCbCr(y + brightness, cb, cr);
    }

    public YCbCr changeContrast(double contrast) {
        double factor = (259 * (contrast + 255)) / (255 * (259 - contrast));
        double newY = factor * (y - 128) + 128;
        return new YCbCr(newY, cb, cr);
    }

    public YCbCr changeSaturation(double saturation) {
        double newCb = (cb - 128) * saturation + 128;
        double newCr = (cr - 128) * saturation + 128;
        return new YCbCr(y, newCb, newCr);
    }


}
