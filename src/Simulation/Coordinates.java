package Simulation;

import java.util.Objects;

public class Coordinates {
    public Integer rows;
    public Integer columns;

    public Coordinates(Integer rows, Integer columns) {
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(rows, that.rows) && Objects.equals(columns, that.columns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, columns);
    }
}
