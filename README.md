# Student grade tracker

A console program that stores students and their marks, and shows each student's average,
the class average and the top scorer(s). Data is saved to `grades.txt` with `java.nio.file`
after every change, and loaded when the program starts.

## Compile and run

You need Java 17 or newer.

```
javac -d out src/*.java
java -cp out GradeTracker
```

Example session:

```
1) Add student  2) Add mark  3) Show report  4) Quit
Choose: 1
Name: Ravi
Added.
Choose: 2
Name: Ravi
Mark (0-100): 88
Mark added.
Choose: 3
Ravi            marks [88]                 average 88.0
Class average: 88.0
Top scorer(s): Ravi
```

## Classes

- `Student` holds a name and a list of marks, and checks each mark is 0 to 100.
- `GradeBook` holds the students in a `LinkedHashMap` (keeps the order they were added) and
  works out averages and top scorers. Ties for top scorer are all shown.
- `GradeFile` saves and loads the book, one line per student: `Ravi;88,92`.
- `GradeTracker` has `main`: it shows the menu, reads input and saves after each change.

## Bad input

Letters where a number is expected (`That is not a whole number.`), a mark outside 0-100, an
empty name, a duplicate student or an unknown student all print a message and return to the
menu, never a stack trace. A missing
`grades.txt` starts an empty book; a broken line in it is skipped with a warning.
