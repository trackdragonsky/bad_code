using System;
using System.Collections.Generic;

// ===================== MODELS =====================
public class Student
{
    public int Id { get; set; }
    public string Name { get; set; }
    public double GPA { get; set; }
}
public class Teacher
{
    public int Id { get; set; }
    public string Name { get; set; }
}
public class Course
{
    public int Id { get; set; }
    public string Title { get; set; }
}
public class Enrollment
{
    public int StudentId { get; set; }
    public int CourseId { get; set; }
}
public class Grade
{
    public int StudentId { get; set; }
    public int CourseId { get; set; }
    public double Score { get; set; }
}

// ===================== REPOSITORY =====================
public class SchoolRepository
{
    public List<Student> Students { get; } = new List<Student>();
    public List<Teacher> Teachers { get; } = new List<Teacher>();
    public List<Course> Courses { get; } = new List<Course>();
    public List<Enrollment> Enrollments { get; } = new List<Enrollment>();
    public List<Grade> Grades { get; } = new List<Grade>();

    // --- Student CRUD ---
    public void AddStudent(Student s) => Students.Add(s);
    public void RemoveStudent(int id) => Students.RemoveAll(s => s.Id == id);
    public Student FindStudent(string name) => Students.Find(s => s.Name.Contains(name));
}

// ===================== MENU =====================
public class Menu
{
    private readonly SchoolRepository repo;

    public Menu(SchoolRepository repo)
    {
        this.repo = repo;
    }

    public void ShowMainMenu()
    {
        int choice = 0;
        while (choice != 99)
        {
            Console.WriteLine("\n===== MENU CHÍNH =====");
            Console.WriteLine("1. Quản lý Sinh viên");
            Console.WriteLine("2. Quản lý Giáo viên");
            Console.WriteLine("3. Quản lý Môn học");
            Console.WriteLine("99. Thoát");
            Console.Write("Nhập lựa chọn: ");
            if (!int.TryParse(Console.ReadLine(), out choice)) continue;

            switch (choice)
            {
                case 1: ManageStudents(); break;
                case 2: ManageTeachers(); break;
                case 3: ManageCourses(); break;
            }
        }
    }

    private void ManageStudents()
    {
        int smenu = 0;
        while (smenu != 9)
        {
            Console.WriteLine("\n--- Quản lý Sinh viên ---");
            Console.WriteLine("1. Thêm SV");
            Console.WriteLine("2. Xóa SV");
            Console.WriteLine("3. Hiển thị tất cả SV");
            Console.WriteLine("4. Tìm SV theo tên");
            Console.WriteLine("9. Quay lại");
            Console.Write("Chọn: ");
            if (!int.TryParse(Console.ReadLine(), out smenu)) continue;

            switch (smenu)
            {
                case 1:
                    Console.Write("ID: "); int id = int.Parse(Console.ReadLine());
                    Console.Write("Tên: "); string name = Console.ReadLine();
                    Console.Write("GPA: "); double gpa = double.Parse(Console.ReadLine());
                    repo.AddStudent(new Student { Id = id, Name = name, GPA = gpa });
                    Console.WriteLine("Đã thêm sinh viên.");
                    break;

                case 2:
                    Console.Write("Nhập ID cần xóa: ");
                    int delId = int.Parse(Console.ReadLine());
                    repo.RemoveStudent(delId);
                    Console.WriteLine("Đã xóa.");
                    break;

                case 3:
                    Console.WriteLine("Danh sách SV:");
                    foreach (var s in repo.Students)
                        Console.WriteLine($"{s.Id} - {s.Name} - GPA {s.GPA}");
                    break;

                case 4:
                    Console.Write("Nhập tên cần tìm: ");
                    string keyword = Console.ReadLine();
                    var found = repo.FindStudent(keyword);
                    if (found != null)
                        Console.WriteLine($"Tìm thấy: {found.Id} - {found.Name} - GPA {found.GPA}");
                    else
                        Console.WriteLine("Không tìm thấy");
                    break;
            }
        }
    }

    private void ManageTeachers()
    {
        Console.WriteLine("--- Quản lý Giáo viên ---");
        // Có thể triển khai tương tự như Student
    }

    private void ManageCourses()
    {
        Console.WriteLine("--- Quản lý Môn học ---");
        // Có thể triển khai tương tự
    }
}

// ===================== PROGRAM MAIN =====================
class Program
{
    static void Main(string[] args)
    {
        var repo = new SchoolRepository();
        var menu = new Menu(repo);
        menu.ShowMainMenu();
    }
}
