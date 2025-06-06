package by.it.group351051.kuchmel.lesson15;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class SourceScannerB {
    public static void main(String[] args) {
        // 1) Определяем рабочую директорию
        Path userDir = Path.of(System.getProperty("user.dir"), "src");

        // Проверяем существование каталога "src"
        if (!Files.exists(userDir) || !Files.isDirectory(userDir)) {
            System.err.println("Directory 'src' not found.");
            return;
        }

        // 2) Рекурсивно обрабатываем файлы
        try (Stream<Path> paths = Files.walk(userDir)) {
            paths.filter(path -> path.toString().endsWith(".java"))
                    .filter(path -> !containsTestAnnotation(path))
                    .map(userDir::relativize)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для проверки, содержит ли файл аннотации теста
    private static boolean containsTestAnnotation(Path path) {
        try {
            String content = Files.readString(path);
            return content.contains("@Test") || content.contains("org.junit.Test");
        } catch (IOException e) {
            return false;
        }
    }
}
