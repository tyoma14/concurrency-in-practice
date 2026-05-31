package ch6.renderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Artyom Zheltyshev on 17.05.2026
 */
public class FutureHtmlRenderer implements HtmlRenderer {

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final LayoutFactory layoutFactory = new LayoutFactory();

    @Override
    public void renderPage(DocumentSource documentSource, JScrollPane parent) {
        List<ImgLayout> imgLayouts = new ArrayList<>();
        ElementLayout layout = layoutFactory.createDocumentLayout(documentSource, imgLayouts);
        Runnable task = () -> {
            for (ImgLayout imgLayout : imgLayouts) {
                imgLayout.downloadImage();
            }
        };
        Future<?> future = executorService.submit(task);
        RendererPanel rendererPanel = new RendererPanel(layout);
        parent.setViewportView(rendererPanel);

        try {
            future.get();
            rendererPanel.repaint();
        } catch (InterruptedException e) {
            // Re-assert the thread’s interrupted status
            Thread.currentThread().interrupt();
            // We don’t need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
