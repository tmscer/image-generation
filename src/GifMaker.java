import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GifMaker {

    public static boolean makeGif(File file, BufferedImage[] images, int msLoop, boolean loop) {
        GifSequenceWriter sequenceWriter = null;
        ImageOutputStream outputStream = null;
        try {
            outputStream = outputStream = new FileImageOutputStream(file);
            sequenceWriter = new GifSequenceWriter(outputStream, images[0].getType(), msLoop, true);
            for (BufferedImage image : images)
                sequenceWriter.writeToSequence(image);
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                sequenceWriter.close();
                outputStream.close();
            } catch(IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
