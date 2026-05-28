import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.sun.net.httpserver.HttpServer;

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

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8000), 0);

        server.createContext("/", exchange -> {

            List<Student> students = new ArrayList<>();

            List<String> lines =
                    Files.readAllLines(Paths.get("students.txt"));

            for (String line : lines) {

                String[] data = line.split(",");

                students.add(
                        new Student(
                                data[0],
                                Integer.parseInt(data[1])
                        )
                );
            }

            StringBuilder response = new StringBuilder();

            response.append("<h1>Student Report</h1>");

            for (Student s : students) {

                response.append("<p>")
                        .append("Name: ")
                        .append(s.name)
                        .append("<br>Marks: ")
                        .append(s.marks)
                        .append("<br>Grade: ")
                        .append(s.getGrade())
                        .append("</p><hr>");
            }

            exchange.sendResponseHeaders(
                    200,
                    response.toString().getBytes().length
            );

            OutputStream os = exchange.getResponseBody();

            os.write(response.toString().getBytes());

            os.close();
        });

        server.start();

        System.out.println(
                "Server started on port 8000"
        );
    }
}