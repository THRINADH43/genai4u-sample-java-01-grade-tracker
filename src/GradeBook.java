import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Holds all students, keyed by lower-case name, and answers questions about them. */
public class GradeBook {
    private final Map<String, Student> students = new LinkedHashMap<>();

    public Student addStudent(String name) {
        String key = name.trim().toLowerCase();
        if (students.containsKey(key)) {
            throw new IllegalArgumentException("A student called " + name.trim() + " already exists.");
        }
        Student s = new Student(name);
        students.put(key, s);
        return s;
    }

    public Student find(String name) {
        Student s = students.get(name.trim().toLowerCase());
        if (s == null) {
            throw new IllegalArgumentException("No student called " + name.trim() + ".");
        }
        return s;
    }

    public Collection<Student> all() {
        return students.values();
    }

    public double classAverage() {
        double total = 0;
        int count = 0;
        for (Student s : students.values()) {
            for (int m : s.getMarks()) {
                total += m;
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }

    /** The student or students with the highest average (more than one if tied). */
    public List<Student> topScorers() {
        List<Student> top = new ArrayList<>();
        double best = -1;
        for (Student s : students.values()) {
            if (s.getMarks().isEmpty()) {
                continue;
            }
            if (s.average() > best) {
                best = s.average();
                top.clear();
                top.add(s);
            } else if (s.average() == best) {
                top.add(s);
            }
        }
        return top;
    }
}
