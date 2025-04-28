package flappybird;

import java.io.*;
import java.util.*;

public class Leaderboard {
    private static final int MAX_ENTRIES = 10;
    private List<ScoreEntry> scores = new ArrayList<>();
    private final String filename = "leaderboard.txt";

    public Leaderboard() {
        loadScores();
    }

    public void addScore(String name, int score) {
        scores.add(new ScoreEntry(name, score));
        Collections.sort(scores);
        if (scores.size() > MAX_ENTRIES) {
            scores = scores.subList(0, MAX_ENTRIES);
        }
        saveScores();
    }

    public boolean isHighScore(int score) {
        if (scores.size() < MAX_ENTRIES) return true;
        return score > scores.get(scores.size() - 1).getScore();
    }

    public List<ScoreEntry> getScores() {
        return scores;
    }

    private void loadScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new ScoreEntry(name, score));
                }
            }
            Collections.sort(scores);
        } catch (IOException e) {
            // File chưa tồn tại, không cần xử lý
        }
    }

    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (ScoreEntry entry : scores) {
                writer.println(entry.getName() + "," + entry.getScore());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
