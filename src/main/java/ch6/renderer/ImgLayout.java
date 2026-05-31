package ch6.renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Artyom Zheltyshev on 11.05.2026
 */
public class ImgLayout implements ElementLayout {

    private final String src;
    private final int width;
    private final int height;
    private State state;

    public ImgLayout(String src, int width, int height) {
        this.src = src;
        this.width = width;
        this.height = height;
        this.state = new UnloadedState();
    }

    @Override
    public Rectangle render(Graphics2D g, int x, int y, RendererPanel rendererPanel) {
        return state.render(g, x, y, rendererPanel);
    }

    public void downloadImage() {
        try {
            // имитация долгой загрузки изобржения
            Thread.sleep(2000);
            InputStream is = getClass().getClassLoader().getResourceAsStream("ch6/renderer/" + src);
            BufferedImage image = ImageIO.read(is);
            state = new LoadedState(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private interface State {

        Rectangle render(Graphics2D g, int x, int y, RendererPanel rendererPanel);

    }

    private class UnloadedState implements State {

        @Override
        public Rectangle render(Graphics2D g, int x, int y, RendererPanel rendererPanel) {
            Rectangle rectangle = new Rectangle(x, y, width, height);
            g.draw(rectangle);
            return rectangle;
        }
    }

    private class LoadedState implements State {

        private final BufferedImage image;

        public LoadedState(BufferedImage image) {
            this.image = image;
        }

        @Override
        public Rectangle render(Graphics2D g, int x, int y, RendererPanel rendererPanel) {
            g.drawImage(image, x, y, width, height, rendererPanel);
            return new Rectangle(x, y, width, height);
        }
    }

}
