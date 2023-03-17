package components;

import gameUtil.Path;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum Transparent {
    WHITE(Path.ROOT + "white2.png"),
    BLACK(Path.ROOT + "black2.png");

    private BufferedImage image;
    Transparent(String path) {

        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
