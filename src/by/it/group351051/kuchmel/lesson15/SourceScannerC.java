package by.it.group351051.kuchmel.lesson15;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class SourceScannerC {
    public static void main(String[] args) {
        // 1) Получаем рабочую директорию, добавляем "src", если нужно
        Path userDir = Path.of(System.getProperty("user.dir"));
        if (!userDir.getFileName().toString().equals("src")) {
            userDir = userDir.resolve("src");
        }

        // Проверка на существование каталога
        if (!Files.exists(userDir) || !Files.isDirectory(userDir)) {
            System.err.println("Not found or not directory: " + userDir);
            return;
        }

        // Список для хранения путей всех файлов
        List<Path> allJava = new ArrayList<>();

        // Чтение всех файлов *.java
        try (var walk = Files.walk(userDir)) {
            walk.filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> processFile(path, allJava));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Печатаем относительные пути файлов, которые прошли фильтрацию
        Path finalUserDir = userDir;
        allJava.forEach(path -> System.out.println(finalUserDir.relativize(path)));
    }

    // Обработка каждого файла
    private static void processFile(Path path, List<Path> allJava) {
        try {
            String content = Files.readString(path);
            if (!content.contains("@Test") && !content.contains("org.junit.Test")) {
                // Добавляем файл в список, если не является тестом
                allJava.add(path);
            }
        } catch (MalformedInputException mie) {
            // Пропускаем файлы с некорректной кодировкой
            System.err.println("Skipping file due to malformed input: " + path);
        } catch (IOException e) {
            // Прочие ошибки при чтении файла
            System.err.println("Error reading file: " + path);
        }
    }
}
