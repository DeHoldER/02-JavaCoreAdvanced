package HomeWork05;

public class Main {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        methodA();
        methodB();
    }

    public static void methodA() {
        System.out.println("Выполняется метод-A...");
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1f;
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время выполнения метода-А: " + (System.currentTimeMillis() - start) + " ms");
    }

    public static void methodB() {
        System.out.println("\n----------------\nВыполняется метод-B...");
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1f;
        }
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        long totalTime = System.currentTimeMillis();
        long split = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        System.out.println("\nМассив разделён за: " + (System.currentTimeMillis() - split) + " ms");

        Thread thread1 = new Thread(() -> {
            long count = System.currentTimeMillis();
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.out.println("Массив-1 посчитан за: " + (System.currentTimeMillis() - count) + " ms");
        });

        Thread thread2 = new Thread(() -> {
            long count = System.currentTimeMillis();
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.out.println("Массив-2 посчитан за: " + (System.currentTimeMillis() - count) + " ms");
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long unite = System.currentTimeMillis();
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.println("Массив склеен за: " + (System.currentTimeMillis() - unite) + " ms");
        System.out.println("\nОбщее время выполнения метода-B: " + (System.currentTimeMillis() - totalTime) + " ms");
    }
}
