package org.tmscer.imagegeneration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static org.tmscer.imagegeneration.Colors.MONOKAI;
import static org.tmscer.imagegeneration.Colors.TRANSPARENT;

public final class ImageGenerators {

    private ImageGenerators() {
    }

    public static final ImageGenerator RANDOM_DIAGONAL = (width, height) -> {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        int r = new Random().nextInt(width / 2) + width / 4;
        System.out.println(r);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i + j == width || i - j == r) {
                    image.setRGB(i, j, MONOKAI[1]);
                } else {
                    image.setRGB(i, j, MONOKAI[0]);
                }
            }
        }
        return new BufferedImage[]{image};
    };

    public static final ImageGenerator FIVE_RANDOM = (width, height) -> {
        Random random = new Random();
        int[] palette = {
                new Color(random.nextInt(255), random.nextInt(255),
                        random.nextInt(255)).getRGB(),
                new Color(random.nextInt(255), random.nextInt(255),
                        random.nextInt(255)).getRGB(),
                new Color(random.nextInt(255), random.nextInt(255),
                        random.nextInt(255)).getRGB(),
                new Color(random.nextInt(255), random.nextInt(255),
                        random.nextInt(255)).getRGB(),
                new Color(random.nextInt(255), random.nextInt(255),
                        random.nextInt(255)).getRGB()
        };
        ;
        BufferedImage[] images = new BufferedImage[10];
        for (int c = 0; c < images.length; c++) {
            images[c] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    images[c].setRGB(i, j, palette[random.nextInt(palette.length)]);
                }
            }
        }
        return images;
    };

    public static final ImageGenerator RANDOM_MONOKAI = (width, height) -> {
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
    };

    public static final ImageGenerator SHORTENING_ARROW = (width, height) -> {
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
    };

    public static final ImageGenerator ENLARGING_CIRCLE = (width, height) -> {
        int range1 = 20;
        int range2 = 2;
        BufferedImage[] images = new BufferedImage[10];
        Random rand = new Random(0);
        int pixSize = 2;
        for (int c = 0; c < images.length; c++) {
            images[c] = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x += pixSize) {
                for (int y = 0; y < height; y += pixSize) {
                    int color = MONOKAI[0];
                    int dist = (int) (Math.sqrt(Math.pow(Math.abs(x - width / 2), 2) +
                            Math.pow(Math.abs(y - height / 2), 2)));
                    if (dist > range2 && dist < range1)
                        if (rand.nextInt(3) == 1)
                            color = MONOKAI[1];
                    for (int i = 0; i < pixSize; i++) {
                        for (int j = 0; j < pixSize; j++) {
                            images[c].setRGB(x + i, y + j, color);
                        }
                    }
                }
            }
            range1 += 4;
            range2 += 5;
        }
        return images;
    };

    public static final ImageGenerator FADING_ENLARGING_ARROWS = (width, height) -> {
        BufferedImage[] smallImages = SHORTENING_ARROW.apply(60, 60);
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

	public static final ImageGenerator DIAGONAL = (width, height) -> {
		BufferedImage[] images = new BufferedImage[1];
		images[0] = new BufferedImage(width, height, 1);
		int mod = 40;
		int c = new Color(200, 32, 100).getRGB();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((x + y) % mod == 0 || (x - y) % mod == 0) {
					images[0].setRGB(x, y, c);
				} else {
					images[0].setRGB(x, y, MONOKAI[0]);
				}
			}
		}
		return images;
	};

    public static final ImageGenerator CENTER_OPENING = (width, height) -> {
        BufferedImage[] images = new BufferedImage[120];
        for (int c = 0; c < images.length; c++) {
            int i = images.length - 1 - c;
            images[i] = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int xDiffA = Math.abs(x - width / 2);
                    int xDiffB = Math.abs(x - width / 2 + 1);
                    int yDiffA = Math.abs(y - height / 2);
                    int yDiffB = Math.abs(y - height / 2 + 1);
                    int limit = width / 2 - c;
                    if (xDiffA + yDiffA <= limit || xDiffA + yDiffB <= limit || xDiffB + yDiffA <= limit || xDiffB + yDiffB <= limit)
                        images[i].setRGB(x, y, Color.WHITE.getRGB());
                    else
                        images[i].setRGB(x, y, MONOKAI[0]);
                }
            }
        }
        return images;
    };

    public static final ImageGenerator OPENING_COMPOSITION = (width, height) -> {
        BufferedImage[] images = new BufferedImage[60];
        width = 1920;
        height = 1080;
        BufferedImage[] center = CENTER_OPENING.apply(120, 120);

        return center;
    };

}
