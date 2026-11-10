import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A student and the marks they have scored, each from 0 to 100. */
public class Student {
    private final String name;
    private final List<Integer> marks = new ArrayList<>();

    public Student(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    public void addMark(int mark) {
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("A mark must be between 0 and 100.");
        }
        marks.add(mark);
    }

    public List<Integer> getMarks() {
        return Collections.unmodifiableList(marks);
    }

    public double average() {
        if (marks.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (int m : marks) {
            total += m;
        }
        return (double) total / marks.size();
    }
}
