import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

@FunctionalInterface
public interface ImageGenerator extends BiFunction<Integer, Integer, BufferedImage[]> {

    @Override
    BufferedImage[] apply(Integer width, Integer height);

}
