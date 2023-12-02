import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    static final Map<String, String> numberMap = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );

    public static void main(String[] args) throws URISyntaxException, IOException {
        int sum = part1(readFile());
        int sum2 = part2(readFile());

        System.out.println(sum);
        System.out.println(sum2);
    }

    private static int part1(Stream<String> stringStream) {
        return stringStream.map(line -> line.replaceAll("([a-zA-Z])*", ""))
                .map(line -> String.valueOf(line.charAt(0)) + line.charAt(line.length() - 1))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private static int part2(Stream<String> stringStream) {
        return stringStream
                .map(Main::replaceWithNumber)
                .mapToInt(Integer::parseInt)
                .sum();
    }

    public static String replaceWithNumber(String line) {
        Pattern pattern = Pattern.compile("one|two|three|four|five|six|seven|eight|nine|\\d");
        Matcher matcher = pattern.matcher(line);

        String first = null;
        String last = null;

        while (matcher.find()) {
            if (first == null) {
                first = matcher.group();
            }
            last = matcher.group();
            matcher.region(matcher.start() + 1, line.length());
        }
        return numberMap.getOrDefault(first, first) + numberMap.getOrDefault(last, last);
    }

    public static Stream<String> readFile() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(Main.class.getClassLoader()
                .getResource("input.txt")).toURI());

        return Files.lines(path);
    }
}