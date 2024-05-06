
    public class YCbCr {
        private double y;
        private double cb;
        private double cr;

        public YCbCr() {

        }

        public YCbCr(double y, double cb, double cr) {
            this.setY(y);
            this.setCb(cb);
            this.setCr(cr);
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getCb() {
            return cb;
        }

        public void setCb(double cb) {
            this.cb = cb;
        }

        public double getCr() {
            return cr;
        }

        public void setCr(double cr) {
            this.cr = cr;
        }

        public YCbCr changeBrightness(double value) {
            YCbCr numb = new YCbCr();

            numb.setY(getY() + value);
            numb.setCb(getCb());
            numb.setCr(getCr());

            return numb;
        }

        public YCbCr changeContrast(double value) {
            YCbCr numb = new YCbCr();

            numb.setY(value * (getY() - 128) + 128);
            numb.setCb(getCb());
            numb.setCr(getCr());

            return numb;
        }

        public YCbCr changeSaturation(double value) {
            YCbCr numb = new YCbCr();

            numb.setY(getY());
            numb.setCb(getCb() * value);
            numb.setCr(getCr() * value);

            return numb;
        }

        public YCbCr changeHue(double value) {
            YCbCr numb = new YCbCr();

            numb.setY(getY());
            numb.setCb(getCb() * (Math.cos(Math.toRadians(value)) + Math.sin(Math.toRadians(value))));
            numb.setCr(getCr() * (Math.sin(Math.toRadians(value)) - Math.cos(Math.toRadians(value))));

            return numb;
        }


        public RGB transformToRGB() {
            double red = getY() + 1.402 * getCr();
            double green = getY() - 0.3441 * getCb() - 0.7141 * getCr();
            double blue = getY() + 1.772 * getCb();

            return new RGB();
        }


    }


