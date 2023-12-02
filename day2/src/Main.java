import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final int RED = 12;
    private static final int GREEN = 13;
    private static final int BLUE = 14;

    public static void main(String[] args) throws URISyntaxException, IOException {
        int part1 = part1();
        int part2 = part2();

        System.out.println(part1);
        System.out.println(part2);
    }

    private static int part1() throws URISyntaxException, IOException {
        return readFile()
                .map(Main::toGame)
                .filter(game -> game.gameSets.stream().allMatch(set -> set.red <= RED && set.green <= GREEN && set.blue <= BLUE))
                .map(Game::id)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static int part2() throws URISyntaxException, IOException {
        return readFile()
                .map(Main::toGame)
                .map(Main::toMinGameSet)
                .peek(System.out::println)
                .map(GameSet::power)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static GameSet toMinGameSet(Game game) {
        int green = maxResult(game, set -> set.green);
        int red = maxResult(game, set -> set.red);
        int blue = maxResult(game, set -> set.blue);
        return new GameSet(red, green, blue);
    }

    private static int maxResult(Game game, Function<GameSet, Integer> gameSetIntegerFunction) {
        return game.gameSets.stream()
                .map(gameSetIntegerFunction)
                .max(Integer::compareTo)
                .orElse(0);
    }

    private static Game toGame(String s) {
        String[] idToGame = s.split(":");
        int id = Integer.parseInt(idToGame[0].split(" ")[1]);

        List<String> listOfGames = Arrays.asList(idToGame[1].split(";"));
        List<GameSet> gameSets = listOfGames.stream()
                .map(game -> Arrays.asList(game.split(",")))
                .map(Main::toSet)
                .collect(Collectors.toList());

        return new Game(id, gameSets);
    }

    private static GameSet toSet(List<String> cubeSets) {
        Map<Colors, Integer> colorMap = cubeSets.stream()
                .map(String::trim)
                .collect(Collectors.toMap(
                        s -> Colors.valueOf(s.split(" ")[1].toUpperCase()),
                        s -> Integer.parseInt(s.split(" ")[0]))
                );

        return new GameSet(
                colorMap.getOrDefault(Colors.RED, 0),
                colorMap.getOrDefault(Colors.GREEN, 0),
                colorMap.getOrDefault(Colors.BLUE, 0)
        );
    }

    private enum Colors {
        RED,
        GREEN,
        BLUE
    }

    public static Stream<String> readFile() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(Main.class.getClassLoader()
                .getResource("input.txt")).toURI());

        return Files.lines(path);
    }

    private record Game(int id, List<GameSet> gameSets) {
    }

    private record GameSet(int red, int green, int blue) {
        public int power() {
            return red * green * blue;
        }
    }
}
