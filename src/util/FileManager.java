package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public String openFile(Path path) throws IOException {
        return Files.readString(path, StandardCharsets.UTF_8);
    }
    public void saveFile(Path path, String content) throws IOException {
        Path absolute = path.toAbsolutePath();
        if (absolute.getParent() != null) Files.createDirectories(absolute.getParent());
        Files.writeString(absolute, content, StandardCharsets.UTF_8);
    }
}
