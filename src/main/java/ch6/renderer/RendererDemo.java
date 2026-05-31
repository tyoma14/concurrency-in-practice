package ch6.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Artyom Zheltyshev on 02.05.2026
 * Приложение для демонстрации разных подходов к распараллеливанию задач
 */
public class RendererDemo {

    public static void main(String[] args) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame mainWindow = new JFrame();
        mainWindow.setPreferredSize(new Dimension(450, 110));
        mainWindow.setSize(800, 600);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.add(scrollPane);
        mainWindow.setVisible(true);

        DocumentSource documentSource = new ClasspathDocumentSource("ch6/renderer/sample.html", ".");
        // можно менять реализацию для демонстрации разных подходов
        HtmlRenderer renderer = new CompletionServiceHtmlRenderer();
        renderer.renderPage(documentSource, scrollPane);
    }

}
