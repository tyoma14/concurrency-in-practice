package ch6.renderer;

import java.awt.*;

/**
 * Created by Artyom Zheltyshev on 10.05.2026
 */
public class EmptyLayout implements ElementLayout {

    @Override
    public Rectangle render(Graphics2D g, int x, int y, RendererPanel rendererPanel) {
        return new Rectangle(x, y, 0, 0);
    }
}
