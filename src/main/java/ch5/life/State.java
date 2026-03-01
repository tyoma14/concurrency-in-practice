package ch5.life;

/**
 * Created by Artyom Zheltyshev on 15.02.2026
 */
public enum State {
    ALIVE,
    DEAD;

    @Override
    public String toString() {
        return this == ALIVE ? "A" : "D";
    }
}
