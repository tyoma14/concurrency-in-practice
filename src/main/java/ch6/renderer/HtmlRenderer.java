package ch6.renderer;

import javax.swing.*;

/**
 * Created by Artyom Zheltyshev on 17.05.2026
 */
public interface HtmlRenderer {

    void renderPage(DocumentSource documentSource, JScrollPane parent);

}
