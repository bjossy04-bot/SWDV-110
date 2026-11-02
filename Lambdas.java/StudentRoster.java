import java.util.ArrayList;
import java.util.List;

public class StudentRoster {

    record Student(String name, String major, double gpa) {}

    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();

        students.add(new Student("Alice", "Computer Science", 3.8));
        students.add(new Student("Bob", "Biology", 2.5));
        students.add(new Student("Charlie", "Math", 1.8));
        students.add(new Student("Diana", "Computer Science", 3.9));
        students.add(new Student("Ethan", "Biology", 3.2));
        students.add(new Student("Fiona", "Math", 2.9));
        students.add(new Student("George", "Computer Science", 1.5));
        students.add(new Student("Hannah", "Biology", 3.7));

        students.sort((s1, s2) -> s1.name().compareTo(s2.name()));
        System.out.println("Students Sorted by Name:");
        students.forEach(student -> System.out.println(student));

        students.sort((s1, s2) -> {
            int majorCompare = s1.major().compareTo(s2.major());

            if (majorCompare != 0) {
                return majorCompare;
            }

            return Double.compare(s2.gpa(), s1.gpa());
        });

        System.out.println("\nStudent Sorted by Major and GPA:");
        students.forEach(student -> System.out.println(student));

        students.removeIf(student -> student.gpa() < 2.0);
        System.out.println("\nStudents After Removing GPA Below 2.0:");
        students.forEach(student -> System.out.println(student));

        System.out.println("\nStudent Names in Uppercase:");
        students.forEach(student -> {
            String upperName = student.name().toUpperCase();
            System.out.println(upperName);
        });
    }
}
