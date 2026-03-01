package ch5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Artyom Zheltyshev on 08.02.2026
 */
public class IndexingDemo {

    private static final int BOUND = 1;
    private static final int N_CONSUMERS = 10;

    public static void main(String[] args) {
        File[] roots = {
                new File("C:\\Users\\Artyom\\Ресурсы")
        };
        startIndexing(roots);
    }

    private static void startIndexing(File[] roots) {
        LinkedBlockingQueue<File> queue = new LinkedBlockingQueue<>(BOUND);
        FileFilter filter = file -> file.isDirectory() || file.getName().endsWith(".pdf");
        for (File root : roots) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }

        Thread.currentThread().interrupt();
    }

}
