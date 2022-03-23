package czolek.scoreboard;

public record Score(int home, int away) {
    public Score() {
        this(0, 0);
    }
}
