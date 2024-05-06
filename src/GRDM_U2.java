import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.ImageCanvas;
import ij.gui.ImageWindow;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 Opens an image window and adds a panel below the image
 */
public class GRDM_U2 implements PlugIn {

    ImagePlus imp; // ImagePlus object
    private int[] origPixels;
    private int width;
    private int height;


    public static void main(String args[]) {
        //new ImageJ();
        //IJ.open("/users/barthel/applications/ImageJ/_images/orchid.jpg");
        IJ.open("src\\fireworks.jpg");

        GRDM_U2 pw = new GRDM_U2();
        pw.imp = IJ.getImage();
        pw.run("");
    }

    public void run(String arg) {
        if (imp==null)
            imp = WindowManager.getCurrentImage();
        if (imp==null) {
            return;
        }
        CustomCanvas cc = new CustomCanvas(imp);

        storePixelValues(imp.getProcessor());

        new CustomWindow(imp, cc);
    }


    private void storePixelValues(ImageProcessor ip) {
        width = ip.getWidth();
        height = ip.getHeight();

        origPixels = ((int []) ip.getPixels()).clone();
    }


    class CustomCanvas extends ImageCanvas {

        CustomCanvas(ImagePlus imp) {
            super(imp);
        }

    } // CustomCanvas inner class


    class CustomWindow extends ImageWindow implements ChangeListener {

        private JSlider jSliderBrightness;
        private JSlider jSliderSaturation;
        private JSlider jSliderHue;
        private JSlider jSliderContrast;

        private double brightness;
        private double contrast;
        private double saturation;
        private double hue;

        CustomWindow(ImagePlus imp, ImageCanvas ic) {
            super(imp, ic);
            addPanel();
        }

        void addPanel() {
            //JPanel panel = new JPanel();
            Panel panel = new Panel();

            panel.setLayout(new GridLayout(4, 1));
            jSliderBrightness = makeTitledSilder("Brightness", 0, 200, 100);
            jSliderSaturation = makeTitledSilder("Saturation", 0, 100, 50);
            jSliderContrast = makeTitledSilder("Contrast " + 1, 0, 100, 10);
            jSliderHue = makeTitledSilder("Hue", 0, 360, 90);
            panel.add(jSliderBrightness);
            panel.add(jSliderSaturation);
            panel.add(jSliderContrast);
            panel.add(jSliderHue);

            add(panel);

            pack();
        }

        private JSlider makeTitledSilder(String string, int minVal, int maxVal, int val) {

            JSlider slider = new JSlider(JSlider.HORIZONTAL, minVal, maxVal, val );
            Dimension preferredSize = new Dimension(width, 50);
            slider.setPreferredSize(preferredSize);
            TitledBorder tb = new TitledBorder(BorderFactory.createEtchedBorder(),
                    string, TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM,
                    new Font("Sans", Font.PLAIN, 11));
            slider.setBorder(tb);
            slider.setMajorTickSpacing((maxVal - minVal)/10 );
            slider.setPaintTicks(true);
            slider.addChangeListener(this);

            return slider;
        }

        private void setSliderTitle(JSlider slider, String str) {
            TitledBorder tb = new TitledBorder(BorderFactory.createEtchedBorder(),
                    str, TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM,
                    new Font("Sans", Font.PLAIN, 11));
            slider.setBorder(tb);
        }

        public void stateChanged( ChangeEvent e ){
            JSlider slider = (JSlider)e.getSource();

            if (slider == jSliderBrightness) {
                brightness = slider.getValue();
                String str = "Brightness " + brightness;
                setSliderTitle(jSliderBrightness, str);
            }

            if (slider == jSliderSaturation) {
                int value = slider.getValue();
                String str = "Saturation" + value;
                setSliderTitle(jSliderSaturation, str);
            }

            changePixelValues(imp.getProcessor());

            imp.updateAndDraw();
        }


        private void changePixelValues(ImageProcessor ip) {

            // Array fuer den Zugriff auf die Pixelwerte
            int[] pixels = (int[])ip.getPixels();

            for (int y=0; y<height; y++) {
                for (int x=0; x<width; x++) {
                    int pos = y*width + x;
                    int argb = origPixels[pos];  // Lesen der Originalwerte 

                    int r = (argb >> 16) & 0xff;
                    int g = (argb >>  8) & 0xff;
                    int b =  argb        & 0xff;


                    // anstelle dieser drei Zeilen später hier die Farbtransformation durchführen,
                    // die Y Cb Cr -Werte verändern und dann wieder zurücktransformieren
                    int rn = (int) (r + brightness);
                    int gn = (int) (g + brightness);
                    int bn = (int) (b + brightness);

                    // Hier muessen die neuen RGB-Werte wieder auf den Bereich von 0 bis 255 begrenzt werden

                    pixels[pos] = (0xFF<<24) | (rn<<16) | (gn<<8) | bn;
                }
            }
        }

    } // CustomWindow inner class
} 