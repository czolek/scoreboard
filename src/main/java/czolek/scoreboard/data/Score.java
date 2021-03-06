package czolek.scoreboard.data;

public record Score(int home, int away) {
    public Score() {
        this(0, 0);
    }

    public static Score score(int home, int away) {
        return new Score(home, away);
    }
}
