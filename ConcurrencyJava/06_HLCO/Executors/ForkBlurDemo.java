import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.io.IOException;
import java.nio.file.Paths;

class ForkBlur extends RecursiveAction {
  private final int[] source;
  private final int[] destination;
  private final int start;
  private final int length;
  private final int blurWidth = 15; // Should be odd

  ForkBlur (int[] source, int[] destination, int start, int length) {
    this.source = source;
    this.destination = destination;
    this.start = start;
    this.length = length;
  }

  protected void computeDirectly() {
    int sidePixels = (blurWidth - 1) / 2;
    for (int index = start; index < start + length; ++index) {
      float r = 0.0f, g = 0.0f, b = 0.0f;
      for (int diff = -sidePixels; diff <= sidePixels; ++diff) {
        int pos = index + diff;
        if (pos < 0) {
          pos = 0;
        }
        if (pos >= source.length) {
          pos = source.length - 1;
        }
        int pixel = source[pos];
        r += (float) ((pixel & 0x00ff0000) >> 16) / blurWidth;
        g += (float) ((pixel & 0x0000ff00) >> 8) / blurWidth;
        b += (float) ((pixel & 0x000000ff) >> 0) / blurWidth;
      }

      int pixel = (0xff000000)
        | (((int) r) << 16)
        | (((int) g) << 8)
        | (((int) b) << 0);
      destination[index] = pixel;
    }
  }

  protected static int threshold = 10000;

  @Override
  protected void compute() {
    if (length < threshold) {
      computeDirectly();
      return;
    }

    int split = length / 2;

    System.out.printf ("Splitting %d into %d and %d\n", length, split, length - split);

    invokeAll (new ForkBlur (source, destination, start, split),
        new ForkBlur (source, destination, start + split, length - split));
  }
}

public class ForkBlurDemo {
  public static void main(String[] args) throws IOException {
    String srcName = "image.png";
    File srcFile = new File (srcName);
    BufferedImage image = ImageIO.read (srcFile);

    System.out.println ("Source image: " + srcName);

    BufferedImage blurredImage = blur (image);

    String destName = "blurred-image.png";
    File destFile = new File (destName).getAbsoluteFile();
    System.out.println (destFile.getAbsoluteFile());
    ImageIO.write (blurredImage, "png", destFile);

    System.out.println ("Output file name: " + destName);
    Paths.get (destFile.getPath()).toRealPath();
  }

  private static BufferedImage blur (BufferedImage srcImage) {
    int w = srcImage.getWidth();
    int h = srcImage.getHeight();

    int[] src = srcImage.getRGB (0, 0, w, h, null, 0, w);
    int[] dest = new int[src.length];

    System.out.printf ("Array size is %d\n", src.length);
    System.out.printf ("Threshold is %d\n", ForkBlur.threshold);

    ForkBlur fb = new ForkBlur (src, dest, 0, src.length);

    ForkJoinPool forkJoinPool = new ForkJoinPool();
    long startTime = System.currentTimeMillis();
    forkJoinPool.invoke (fb);
    long endTime = System.currentTimeMillis();

    System.out.println("Image blur took " + (endTime - startTime) + " milliseconds.");

    BufferedImage destImage = new BufferedImage (w, h, BufferedImage.TYPE_INT_ARGB);
    destImage.setRGB (0, 0, w, h, dest, 0, w);

    return destImage;
  }
}

