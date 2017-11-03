package org.tmscer.imagegeneration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.tmscer.imagegeneration.Colors.MONOKAI;
import static org.tmscer.imagegeneration.ImageGenerators.*;

public class Main {

    public static void main(String... args) throws IOException, InterruptedException {
        /*BufferedImage[] gens = ENLARGING_CIRCLE.apply(100, 100);

        BufferedImage[] reversed = new BufferedImage[gens.length * 2 - 1];
        System.arraycopy(gens, 0, reversed, 0, gens.length);
        for (int i = 0; i < gens.length - 1; i++) {
            reversed[gens.length + i] = gens[gens.length - i - 1];
        }

        GifMaker.makeGif(new File("./imgs/GIF.gif"), reversed, 30, true);*/
		BufferedImage img = DIAGONAL.apply(1366, 768)[0];
		File imgFile = new File("/home/tmscer/Pictures/wm/suspend.png");
		ImageIO.write(img, "PNG", imgFile);
    }

    /*
    1368 = 2 * 683
    768 = 2^8 * 3
    common factors = {2}

    1920 = 2^7 * 3 * 5
    1080 = 2^3 * 3^3 * 5
    common factors = {2^3, 3, 5}
     */

}
