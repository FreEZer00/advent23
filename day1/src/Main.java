import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Stream<String> stringStream = readFile();

        int sum = stringStream.map(line -> line.replaceAll("([a-zA-Z])*", ""))
                .map(line -> String.valueOf(line.charAt(0)) + line.charAt(line.length() - 1))
                .mapToInt(Integer::parseInt)
                .sum();

        System.out.println(sum);
    }

    public static Stream<String> readFile() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(Main.class.getClassLoader()
                .getResource("input.txt")).toURI());

        return Files.lines(path);
    }
}