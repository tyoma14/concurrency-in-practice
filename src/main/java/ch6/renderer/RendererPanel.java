package ch6.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Artyom Zheltyshev on 10.05.2026
 */
public class RendererPanel extends JPanel {

    private ElementLayout documentLayout;

    public RendererPanel(ElementLayout documentLayout) {
        super();
        this.documentLayout = documentLayout;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle renderArea = documentLayout.render((Graphics2D) g, 0, 0, this);
        setPreferredSize(new Dimension((int) renderArea.getWidth(), (int) renderArea.getHeight()));
    }
}
