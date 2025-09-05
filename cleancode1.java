import java.util.*;

// ===== MODEL CLASSES =====
class Student {
    String id;
    String name;
    int age;
    double gpa;

    Student(String id, String name, int age, double gpa) {
        this.id = id; this.name = name; this.age = age; this.gpa = gpa;
    }

    @Override
    public String toString() {
        return String.format("ID:%s | Name:%s | Age:%d | GPA:%.2f", id, name, age, gpa);
    }
}

class Teacher {
    String id, name, major;
    Teacher(String id, String name, String major) {
        this.id = id; this.name = name; this.major = major;
    }

    @Override
    public String toString() {
        return String.format("ID:%s | Name:%s | Major:%s", id, name, major);
    }
}

class Course {
    String id, name;
    int credits;
    Course(String id, String name, int credits) {
        this.id = id; this.name = name; this.credits = credits;
    }

    @Override
    public String toString() {
        return String.format("ID:%s | Name:%s | Credits:%d", id, name, credits);
    }
}

class Enrollment {
    String studentId, courseId;
    Enrollment(String studentId, String courseId) {
        this.studentId = studentId; this.courseId = courseId;
    }
}

class Grade {
    String studentId, courseId;
    double score;
    Grade(String studentId, String courseId, double score) {
        this.studentId = studentId; this.courseId = courseId; this.score = score;
    }
}

// ===== REPOSITORY =====
class SchoolRepository {
    List<Student> students = new ArrayList<>();
    List<Teacher> teachers = new ArrayList<>();
    List<Course> courses = new ArrayList<>();
    List<Enrollment> enrollments = new ArrayList<>();
    List<Grade> grades = new ArrayList<>();

    // --- Student CRUD ---
    void addStudent(Student s) { students.add(s); }
    void removeStudent(String id) { students.removeIf(s -> s.id.equals(id)); }
    Student findStudentById(String id) { return students.stream().filter(s -> s.id.equals(id)).findFirst().orElse(null); }
    List<Student> findStudentByName(String name) {
        return students.stream().filter(s -> s.name.contains(name)).toList();
    }

    // --- Teacher CRUD ---
    void addTeacher(Teacher t) { teachers.add(t); }
    void removeTeacher(String id) { teachers.removeIf(t -> t.id.equals(id)); }

    // --- Course CRUD ---
    void addCourse(Course c) { courses.add(c); }
    void removeCourse(String id) { courses.removeIf(c -> c.id.equals(id)); }

    // --- Enrollment ---
    void enroll(String studentId, String courseId) { enrollments.add(new Enrollment(studentId, courseId)); }
    void unenroll(String studentId, String courseId) {
        enrollments.removeIf(e -> e.studentId.equals(studentId) && e.courseId.equals(courseId));
    }

    // --- Grade ---
    void addGrade(String studentId, String courseId, double score) {
        grades.add(new Grade(studentId, courseId, score));
    }
    void updateGrade(String studentId, String courseId, double newScore) {
        for (Grade g : grades) {
            if (g.studentId.equals(studentId) && g.courseId.equals(courseId)) {
                g.score = newScore;
            }
        }
    }
}

// ===== MENU / UI =====
class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final SchoolRepository repo;

    Menu(SchoolRepository repo) { this.repo = repo; }

    void show() {
        int choice;
        do {
            System.out.println("\n===== MENU CHINH =====");
            System.out.println("1. Quan ly Sinh vien");
            System.out.println("2. Quan ly Giao vien");
            System.out.println("3. Quan ly Mon hoc");
            System.out.println("4. Quan ly Dang ky");
            System.out.println("5. Quan ly Diem");
            System.out.println("6. Bao cao tong hop");
            System.out.println("99. Thoat");
            System.out.print("Chon: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageTeachers();
                case 3 -> manageCourses();
                case 4 -> manageEnrollments();
                case 5 -> manageGrades();
                case 6 -> report();
            }
        } while (choice != 99);
    }

    // --- Student Menu ---
    private void manageStudents() {
        int sm;
        do {
            System.out.println("\n--- QUAN LY SINH VIEN ---");
            System.out.println("1. Them SV");
            System.out.println("2. Xoa SV");
            System.out.println("3. Hien thi tat ca SV");
            System.out.println("4. Tim SV theo ten");
            System.out.println("9. Quay lai");
            sm = Integer.parseInt(sc.nextLine());
            switch (sm) {
                case 1 -> {
                    System.out.print("ID: "); String id = sc.nextLine();
                    System.out.print("Ten: "); String name = sc.nextLine();
                    System.out.print("Tuoi: "); int age = Integer.parseInt(sc.nextLine());
                    System.out.print("GPA: "); double gpa = Double.parseDouble(sc.nextLine());
                    repo.addStudent(new Student(id, name, age, gpa));
                }
                case 2 -> {
                    System.out.print("Nhap ID can xoa: ");
                    repo.removeStudent(sc.nextLine());
                }
                case 3 -> repo.students.forEach(System.out::println);
                case 4 -> {
                    System.out.print("Nhap ten: ");
                    repo.findStudentByName(sc.nextLine()).forEach(System.out::println);
                }
            }
        } while (sm != 9);
    }

    private void manageTeachers() {
        System.out.println("--- Quan ly GV --- (Tuong tu Student, lam them)");
    }

    private void manageCourses() {
        System.out.println("--- Quan ly Mon hoc --- (Tuong tu Student, lam them)");
    }

    private void manageEnrollments() {
        System.out.println("--- Quan ly Dang ky --- (Tuong tu)");
    }

    private void manageGrades() {
        System.out.println("--- Quan ly Diem --- (Tuong tu)");
    }

    private void report() {
        System.out.println("=== BAO CAO ===");
        for (Student s : repo.students) {
            System.out.println("Sinh vien: " + s.name);
            for (Enrollment e : repo.enrollments) {
                if (e.studentId.equals(s.id)) {
                    Course c = repo.courses.stream().filter(cc -> cc.id.equals(e.courseId)).findFirst().orElse(null);
                    if (c != null) {
                        System.out.print(" - Mon hoc: " + c.name);
                        repo.grades.stream()
                                .filter(g -> g.studentId.equals(s.id) && g.courseId.equals(c.id))
                                .forEach(g -> System.out.print(" | Diem: " + g.score));
                        System.out.println();
                    }
                }
            }
        }
    }
}

// ===== MAIN =====
public class cleancode1 {
    public static void main(String[] args) {
        SchoolRepository repo = new SchoolRepository();
        Menu menu = new Menu(repo);
        menu.show();
    }
}
//thêm