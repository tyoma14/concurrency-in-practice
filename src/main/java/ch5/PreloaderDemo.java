package ch5;

/**
 * Created by Artyom Zheltyshev on 08.02.2026
 */
public class PreloaderDemo {

    public static void main(String[] args) throws InterruptedException, DataLoadException {
        Preloader preloader = new Preloader();
        preloader.start();
        System.out.println("Doing some other work...");
        Thread.sleep(400);
        ProductInfo productInfo = preloader.get();
        System.out.println("Product info received: " + productInfo);
    }

}
