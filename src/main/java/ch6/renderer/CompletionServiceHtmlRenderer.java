package ch6.renderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Artyom Zheltyshev on 17.05.2026
 */
public class CompletionServiceHtmlRenderer implements HtmlRenderer {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final LayoutFactory layoutFactory = new LayoutFactory();

    @Override
    public void renderPage(DocumentSource documentSource, JScrollPane parent) {
        List<ImgLayout> imgLayouts = new ArrayList<>();
        ElementLayout layout = layoutFactory.createDocumentLayout(documentSource, imgLayouts);
        CompletionService<ImgLayout> completionService = new ExecutorCompletionService<>(executorService);
        for (ImgLayout imgLayout : imgLayouts) {
            completionService.submit(imgLayout::downloadImage, imgLayout);
        }

        RendererPanel rendererPanel = new RendererPanel(layout);
        parent.setViewportView(rendererPanel);

        try {
            for (int i = 0; i < imgLayouts.size(); i++) {
                Future<ImgLayout> future = completionService.take();
                future.get();
                rendererPanel.repaint();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
