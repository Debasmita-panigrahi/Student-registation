import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolledStudents;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getSchedule() {
        return schedule;
    }

    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }
}

class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course.enrollStudent()) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            return true;
        }
        return false;
    }
}

public class CourseRegistrationSystem {
    private static Map<String, Course> courses = new HashMap<>();
    private static Map<String, Student> students = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();
        int choice;
        do {
            System.out.println("\nCourse Registration System");
            System.out.println("1. List Courses");
            System.out.println("2. Register Student for a Course");
            System.out.println("3. Drop Course for a Student");
            System.out.println("4. List Student Courses");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerStudentForCourse();
                    break;
                case 3:
                    dropStudentCourse();
                    break;
                case 4:
                    listStudentCourses();
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void initializeData() {
        courses.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basic concepts of computer science", 30, "MWF 10:00-11:00"));
        courses.put("MA101", new Course("MA101", "Calculus I", "Introduction to calculus", 25, "TTh 09:00-10:30"));
        courses.put("PH101", new Course("PH101", "Physics I", "Fundamentals of physics", 20, "MWF 11:00-12:00"));

        students.put("S001", new Student("S001", "John Doe"));
        students.put("S002", new Student("S002", "Jane Smith"));
        students.put("S003", new Student("S003", "Emily Johnson"));
    }

    private static void listCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses.values()) {
            System.out.println("Course Code: " + course.getCourseCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Enrolled Students: " + course.getEnrolledStudents());
            System.out.println("Available Slots: " + course.getAvailableSlots());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println();
        }
    }

    private static void registerStudentForCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = students.get(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Course course = courses.get(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.registerCourse(course)) {
            System.out.println("Student registered for the course successfully.");
        } else {
            System.out.println("Failed to register student for the course. Course might be full.");
        }
    }

    private static void dropStudentCourse() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = students.get(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Course course = courses.get(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("Student dropped the course successfully.");
        } else {
            System.out.println("Failed to drop the course. Student might not be registered in this course.");
        }
    }

    private static void listStudentCourses() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = students.get(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Courses registered by " + student.getName() + ":");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course.getCourseCode() + " - " + course.getTitle());
        }
    }
}

