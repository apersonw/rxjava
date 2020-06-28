package org.rxjava.third.weixin.pay.util.qrcode;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public final class MatrixToImageWriter {
    private static final int BLACK = -16777216;
    private static final int WHITE = -1;

    private MatrixToImageWriter() {
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, 1);

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                image.setRGB(x, y, matrix.get(x, y) ? -16777216 : -1);
            }
        }

        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
}

