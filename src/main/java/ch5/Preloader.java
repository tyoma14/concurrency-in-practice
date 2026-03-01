package ch5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Artyom Zheltyshev on 07.02.2026
 */
public class Preloader {
    
    private final FutureTask<ProductInfo> future = new FutureTask<>(this::loadProductInfo);
    private final Thread thread = new Thread(future);
    
    public void start() {
        thread.start();
    }
    
    public ProductInfo get() throws DataLoadException, InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else {
                throw launderThrowable(cause);
            }
        }
    }

    private static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not checked", t);
        }
    }

    private ProductInfo loadProductInfo() throws DataLoadException {
        try {
            Thread.sleep(200);
            return new ProductInfo();
        } catch (InterruptedException e) {
            throw new DataLoadException();
        }
    }

}
