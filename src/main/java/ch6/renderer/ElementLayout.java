package ch6.renderer;

import java.awt.*;

/**
 * Created by Artyom Zheltyshev on 06.05.2026
 */
public interface ElementLayout {

    Rectangle render(Graphics2D g, int x, int y, RendererPanel rendererPanel);

}
