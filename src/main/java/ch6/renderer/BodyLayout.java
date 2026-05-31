package ch6.renderer;

import java.awt.*;
import java.util.List;

/**
 * Created by Artyom Zheltyshev on 06.05.2026
 */
public class BodyLayout implements ElementLayout {

    private static final int MARGIN = 5;
    private final List<ElementLayout> children;
    private final int width;

    public BodyLayout(List<ElementLayout> children, int width) {
        this.children = children;
        this.width = width;
    }

    @Override
    public Rectangle render(Graphics2D graphics, int x, int y, RendererPanel rendererPanel) {
        graphics.setBackground(Color.WHITE);
        int childY = y + MARGIN;
        for (ElementLayout elementLayout : children) {
            Rectangle bounds = elementLayout.render(graphics, x, childY, rendererPanel);
            childY += (int) bounds.getHeight() + MARGIN;
        }
        return new Rectangle(x, y, width, childY - y);
    }
}
