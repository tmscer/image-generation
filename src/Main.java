import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {

    private static final int MONOKAI_BLACK = new Color(21, 21, 21).getRGB();
    private static final int MONOKAI_RED = new Color(249, 38, 114).getRGB();

    private static final int[] MONOKAI = {
            new Color(21, 21, 21).getRGB(),
            new Color(249, 38, 114).getRGB(),
            new Color(102, 217, 239).getRGB(),
            new Color(166, 226, 46).getRGB(),
            new Color(253, 151, 31).getRGB()
    };

    private static final Random r = new Random();

    private static final ImageGenerator[] generators = {
            (width, height) -> {
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

                int r = new Random().nextInt(width / 2) + width / 4;
                System.out.println(r);
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (i + j == width || i - j == r) {
                            image.setRGB(i, j, MONOKAI_RED);
                        } else {
                            image.setRGB(i, j, MONOKAI_BLACK);
                        }
                    }
                }

                return new BufferedImage[]{image};
            },
            (width, height) -> {
                int[] palette = {
                        new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)).getRGB(),
                        new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)).getRGB(),
                        new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)).getRGB(),
                        new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)).getRGB(),
                        new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)).getRGB()
                };
                ;
                BufferedImage[] images = new BufferedImage[10];
                Random randomizer = new Random();
                for (int c = 0; c < images.length; c++) {
                    images[c] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            images[c].setRGB(i, j, palette[randomizer.nextInt(palette.length)]);
                        }
                    }
                }
                return images;
            },
            (width, height) -> {
                BufferedImage[] images = new BufferedImage[3];
                for (int c = 0; c < images.length; c++) {
                    images[c] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            images[c].setRGB(i, j, MONOKAI[new Random().nextInt(MONOKAI.length)]);
                        }
                    }
                }
                return images;
            },
            (width, height) -> {
                BufferedImage[] images = new BufferedImage[height / 2 + 1];
                for (int c = 0; c < images.length; c++) {
                    images[c] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if ((i + j == width / 2 || i + j == width / 2 + 1 || i - j == width / 2 || i - j == width / 2 - 1) && j < height / 2 - 1.5 * c) {
                                images[c].setRGB(i, j, MONOKAI[1]);
                            } else
                                images[c].setRGB(i, j, MONOKAI[0]);
                        }
                    }
                }
                return images;
            },
            (width, height) -> {
                int n = 85;
                BufferedImage[] images = new BufferedImage[n];
                int[] alphaFade = new int[n];
                for (int x = 0; x < alphaFade.length; x++) {
                    alphaFade[x] = x == 0 ? 255 : x * (255 / n);
                }
                for (int c = 0; c < images.length; c++) {
                    images[c] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            images[c].setRGB(i, j, new Color(21, 21, 21, alphaFade[alphaFade.length - c - 1]).getRGB());
                        }
                    }
                }
                return images;
            }
    };

    private static ImageGenerator theGen = (width, height) -> {
        BufferedImage[] smallImages = generators[3].apply(60, 60);
        BufferedImage[] connectedImages = new BufferedImage[smallImages.length];
        for (int counter = 0; counter < connectedImages.length; counter++) {
            connectedImages[counter] = new BufferedImage(1920, 1080, BufferedImage.TYPE_4BYTE_ABGR);
            for (int i = 0; i < 1920 / 60; i++) {
                for (int j = 0; j < 1080 / 60; j++) {
                    for (int x = 0; x < 60; x++) {
                        for (int y = 0; y < 60; y++) {
                            Color color = new Color(smallImages[counter].getRGB(x, y));
                            int alpha = 255;//255 - ((counter + 1) * 256 / 32);
                            connectedImages[counter].setRGB(i * 60 + x, j * 60 + y,
                                    new Color(color.getRed(), color.getGreen(), color.getBlue(),
                                            alpha < 0 ? 0 : alpha).getRGB());
                        }
                    }
                }
            }
        }
        return connectedImages;
    };

    /*
    1368 = 2 * 683
    768 = 2^8 * 3
    common factors = {2}

    1920 = 2^7 * 3 * 5
    1080 = 2^3 * 3^3 * 5
    common factors = {2^3, 3, 5}
     */

    public static void main(String... args) throws IOException, InterruptedException {
        BufferedImage[] gens = theGen.apply(1920, 1080);

        GifMaker.makeGif(new File("./imgs/GIF.gif"), gens, 800 / gens.length, true);

        long time = System.currentTimeMillis();
        for (int i = 0; i < gens.length; i++) {
            File imageFile = new File("./imgs/" + i + ".png");
            try {
                ImageIO.write(gens[i], "PNG", imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
