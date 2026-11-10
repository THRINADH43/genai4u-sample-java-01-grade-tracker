import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class GradeTracker {
    private static final Path DATA = Path.of("grades.txt");

    public static void main(String[] args) {
        GradeFile file = new GradeFile(DATA);
        GradeBook book;
        try {
            book = file.load();
        } catch (IOException e) {
            System.out.println("Could not read " + DATA + ": " + e.getMessage());
            book = new GradeBook();
        }
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("1) Add student  2) Add mark  3) Show report  4) Quit");
            System.out.print("Choose: ");
            if (!in.hasNextLine()) break;
            String choice = in.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Name: ");
                        book.addStudent(in.nextLine());
                        save(file, book);
                        System.out.println("Added.");
                    }
                    case "2" -> {
                        System.out.print("Name: ");
                        Student s = book.find(in.nextLine());
                        System.out.print("Mark (0-100): ");
                        s.addMark(Integer.parseInt(in.nextLine().trim()));
                        save(file, book);
                        System.out.println("Mark added.");
                    }
                    case "3" -> printReport(book);
                    case "4" -> {
                        System.out.println("Bye!");
                        return;
                    }
                    default -> System.out.println("Please type 1, 2, 3 or 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a whole number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void printReport(GradeBook book) {
        if (book.all().isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        for (Student s : book.all()) {
            System.out.printf("%-15s marks %-20s average %.1f%n", s.getName(), s.getMarks(), s.average());
        }
        System.out.printf("Class average: %.1f%n", book.classAverage());
        List<Student> top = book.topScorers();
        if (!top.isEmpty()) {
            System.out.print("Top scorer(s): ");
            for (Student s : top) System.out.print(s.getName() + " ");
            System.out.println();
        }
    }

    static void save(GradeFile file, GradeBook book) {
        try {
            file.save(book);
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }
}
