package ch5;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Artyom Zheltyshev on 08.02.2026
 */
public class Indexer implements Runnable {
    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void indexFile(File file) {
        System.out.println("Indexed " + file.getAbsolutePath());
    }
}
