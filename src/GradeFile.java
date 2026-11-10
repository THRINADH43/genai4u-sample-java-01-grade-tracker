import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves and loads the grade book as a text file, one student per line:
 * name;mark,mark,mark
 */
public class GradeFile {
    private final Path path;

    public GradeFile(Path path) {
        this.path = path;
    }

    public void save(GradeBook book) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Student s : book.all()) {
            StringBuilder line = new StringBuilder(s.getName()).append(";");
            List<Integer> marks = s.getMarks();
            for (int i = 0; i < marks.size(); i++) {
                if (i > 0) line.append(",");
                line.append(marks.get(i));
            }
            lines.add(line.toString());
        }
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    /** Loads the file. A missing file gives an empty book; bad lines are skipped with a warning. */
    public GradeBook load() throws IOException {
        GradeBook book = new GradeBook();
        if (!Files.exists(path)) {
            return book;
        }
        int lineNo = 0;
        for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
            lineNo++;
            if (line.isBlank()) continue;
            try {
                String[] parts = line.split(";", -1);
                Student s = book.addStudent(parts[0]);
                if (parts.length > 1 && !parts[1].isBlank()) {
                    for (String m : parts[1].split(",")) {
                        s.addMark(Integer.parseInt(m.trim()));
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping line " + lineNo + " of " + path + ": " + e.getMessage());
            }
        }
        return book;
    }
}
