import java.io.*;
import java.util.*;

public class StudentApp {

    static class Student {
        String name;
        int marks;

        Student(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        String getGrade() {
            if (marks >= 90) return "A";
            if (marks >= 75) return "B";
            if (marks >= 60) return "C";
            if (marks >= 40) return "D";
            return "Fail";
        }
    }

    public static void main(String[] args) {

        List<Student> students = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("students.txt"));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                String name = data[0];
                int marks = Integer.parseInt(data[1]);

                students.add(new Student(name, marks));
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error reading file.");
            return;
        }

        System.out.println("\n===== STUDENT REPORT =====\n");

        for (Student s : students) {

            System.out.println("Name   : " + s.name);
            System.out.println("Marks  : " + s.marks);
            System.out.println("Grade  : " + s.getGrade());

            System.out.println("--------------------------");
        }
    }
}