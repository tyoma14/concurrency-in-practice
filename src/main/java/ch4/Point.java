package ch4;

import annotation.Immutable;

/**
 * Created by Artyom Zheltyshev on 23.06.2024
 */
@Immutable
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
