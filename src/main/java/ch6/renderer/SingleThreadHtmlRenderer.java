package ch6.renderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artyom Zheltyshev on 02.05.2026
 */
public class SingleThreadHtmlRenderer implements HtmlRenderer {

    private final LayoutFactory layoutFactory = new LayoutFactory();

    @Override
    public void renderPage(DocumentSource documentSource, JScrollPane parent) {
        List<ImgLayout> imgLayouts = new ArrayList<>();
        ElementLayout layout = layoutFactory.createDocumentLayout(documentSource, imgLayouts);
        RendererPanel rendererPanel = new RendererPanel(layout);
        parent.setViewportView(rendererPanel);
        for (ImgLayout imgLayout : imgLayouts) {
            imgLayout.downloadImage();
        }
        rendererPanel.repaint();
    }

}
