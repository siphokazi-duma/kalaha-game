package za.co.fnb.game.mancala;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestUtils {
    public String getFileContents(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());
        String content = new String(Files.readAllBytes(file.toPath()));

        return content;
    }
}
