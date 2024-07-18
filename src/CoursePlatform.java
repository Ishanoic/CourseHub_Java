import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// User base class
abstract class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
}

// Student class
class Student extends User {
    private List<Course> enrolledCourses;

    public Student(String username, String password, String email) {
        super(username, password, email);
        this.enrolledCourses = new ArrayList<>();
    }

    public void enrollCourse(Course course) {
        enrolledCourses.add(course);
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void viewLectures() {
        for (Course course : enrolledCourses) {
            System.out.println("Lectures for course: " + course.getTitle());
            for (Lecture lecture : course.getLectures()) {
                System.out.println(lecture.getTitle() + ": " + lecture.getDuration() + " minutes");
            }
        }
    }
}

// Instructor class
// Instructor class
class Instructor extends User {
    private List<Course> createdCourses;

    public Instructor(String username, String password, String email) {
        super(username, password, email);
        this.createdCourses = new ArrayList<>();
    }

    public void createCourse(Course course) {
        createdCourses.add(course);
    }

    public void editCourse(int courseIndex, String newTitle, String newDescription) {
        if (courseIndex >= 0 && courseIndex < createdCourses.size()) {
            Course course = createdCourses.get(courseIndex);
            course.setTitle(newTitle);
            course.setDescription(newDescription);
            System.out.println("Course updated successfully!");
        } else {
            System.out.println("Invalid course index!");
        }
    }

    public void deleteCourse(int courseIndex) {
        if (courseIndex >= 0 && courseIndex < createdCourses.size()) {
            createdCourses.remove(courseIndex);
            System.out.println("Course deleted successfully!");
        } else {
            System.out.println("Invalid course index!");
        }
    }

    public void addLectureToCourse(int courseIndex, Lecture lecture) {
        if (courseIndex >= 0 && courseIndex < createdCourses.size()) {
            createdCourses.get(courseIndex).addLecture(lecture);
        } else {
            System.out.println("Invalid course index!");
        }
    }

    public void viewEnrolledStudents(int courseIndex) {
        if (courseIndex >= 0 && courseIndex < createdCourses.size()) {
            Course course = createdCourses.get(courseIndex);
            System.out.println("Students enrolled in " + course.getTitle() + ":");
            for (Student student : course.getEnrolledStudents()) {
                System.out.println(student.getUsername());
            }
        } else {
            System.out.println("Invalid course index!");
        }
    }

    public void uploadMaterialToCourse(int courseIndex, String material) {
        if (courseIndex >= 0 && courseIndex < createdCourses.size()) {
            createdCourses.get(courseIndex).addMaterial(material);
            System.out.println("Material uploaded successfully!");
        } else {
            System.out.println("Invalid course index!");
        }
    }

    // Add this method
    public List<Course> getCreatedCourses() {
        return createdCourses;
    }
}


// Course class
class Course {
    private String title;
    private String description;
    private Instructor instructor;
    private List<Lecture> lectures;
    private List<Student> enrolledStudents;
    private List<String> materials; // List to hold course materials

    public Course(String title, String description, Instructor instructor) {
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.lectures = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
        this.materials = new ArrayList<>();
    }

    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
        student.enrollCourse(this);
    }

    public void addMaterial(String material) {
        materials.add(material);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public List<String> getMaterials() {
        return materials;
    }
}

// Lecture class
class Lecture {
    private String title;
    private String content;
    private int duration; // in minutes

    public Lecture(String title, String content, int duration) {
        this.title = title;
        this.content = content;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getDuration() {
        return duration;
    }
}

// Main class
public class CoursePlatform {
    private List<User> users;
    private List<Course> courses;

    public CoursePlatform() {
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.authenticate(password)) {
                return user;
            }
        }
        return null;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public static void main(String[] args) {
        CoursePlatform platform = new CoursePlatform();
        Scanner scanner = new Scanner(System.in);

        // Sample Data
        Instructor instructor = new Instructor("instructor1", "pass123", "instructor1@example.com");
        platform.registerUser(instructor);
        Student student = new Student("student1", "pass123", "student1@example.com");
        platform.registerUser(student);

        Course course = new Course("Java Programming", "Learn Java from scratch", instructor);
        platform.addCourse(course);
        instructor.createCourse(course);
        course.addLecture(new Lecture("Introduction", "Welcome to Java Programming", 30));

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Are you a student or instructor (s/i)? ");
                String userType = scanner.nextLine();

                if (userType.equalsIgnoreCase("s")) {
                    Student newStudent = new Student(username, password, email);
                    platform.registerUser(newStudent);
                    System.out.println("Student registered successfully!");
                } else if (userType.equalsIgnoreCase("i")) {
                    Instructor newInstructor = new Instructor(username, password, email);
                    platform.registerUser(newInstructor);
                    System.out.println("Instructor registered successfully!");
                } else {
                    System.out.println("Invalid user type!");
                }
            } else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User user = platform.loginUser(username, password);
                if (user != null) {
                    System.out.println("Login successful!");

                    if (user instanceof Instructor) {
                        while (true) {
                            System.out.println("Courses created by you:");
                            for (Course c : ((Instructor) user).getCreatedCourses()) {
                                System.out.println(c.getTitle() + ": " + c.getDescription());
                            }
                            System.out.println("1. Create new course");
                            System.out.println("2. Edit course");
                            System.out.println("3. Delete course");
                            System.out.println("4. Add lecture to course");
                            System.out.println("5. Upload material to course");
                            System.out.println("6. View enrolled students");
                            System.out.println("7. Update profile");
                            System.out.println("8. Logout");
                            System.out.print("Choose an option: ");
                            int instructorChoice = Integer.parseInt(scanner.nextLine());

                            if (instructorChoice == 1) {
                                System.out.print("Enter course title: ");
                                String courseTitle = scanner.nextLine();
                                System.out.print("Enter course description: ");
                                String courseDescription = scanner.nextLine();

                                Course newCourse = new Course(courseTitle, courseDescription, (Instructor) user);
                                ((Instructor) user).createCourse(newCourse);
                                platform.addCourse(newCourse);
                                System.out.println("Course created successfully!");
                            } else if (instructorChoice == 2) {
                                System.out.println("Courses created by you:");
                                List<Course> courses = ((Instructor) user).getCreatedCourses();
                                for (int i = 0; i < courses.size(); i++) {
                                    System.out.println((i + 1) + ". " + courses.get(i).getTitle());
                                }
                                System.out.print("Enter course number to edit: ");
                                int courseNumber = Integer.parseInt(scanner.nextLine()) - 1;

                                if (courseNumber >= 0 && courseNumber < courses.size()) {
                                    System.out.print("Enter new title: ");
                                    String newTitle = scanner.nextLine();
                                    System.out.print("Enter new description: ");
                                    String newDescription = scanner.nextLine();

                                    ((Instructor) user).editCourse(courseNumber, newTitle, newDescription);
                                } else {
                                    System.out.println("Invalid course number!");
                                }
                            } else if (instructorChoice == 3) {
                                System.out.println("Courses created by you:");
                                List<Course> courses = ((Instructor) user).getCreatedCourses();
                                for (int i = 0; i < courses.size(); i++) {
                                    System.out.println((i + 1) + ". " + courses.get(i).getTitle());
                                }
                                System.out.print("Enter course number to delete: ");
                                int courseNumber = Integer.parseInt(scanner.nextLine()) - 1;

                                if (courseNumber >= 0 && courseNumber < courses.size()) {
                                    ((Instructor) user).deleteCourse(courseNumber);
                                } else {
                                    System.out.println("Invalid course number!");
                                }
                            } else if (instructorChoice == 4) {
                                System.out.println("Courses created by you:");
                                List<Course> courses = ((Instructor) user).getCreatedCourses();
                                for (int i = 0; i < courses.size(); i++) {
                                    System.out.println((i + 1) + ". " + courses.get(i).getTitle());
                                }
                                System.out.print("Enter course number to add lecture: ");
                                int courseNumber = Integer.parseInt(scanner.nextLine()) - 1;

                                if (courseNumber >= 0 && courseNumber < courses.size()) {
                                    System.out.print("Enter lecture title: ");
                                    String lectureTitle = scanner.nextLine();
                                    System.out.print("Enter lecture content: ");
                                    String lectureContent = scanner.nextLine();
                                    System.out.print("Enter lecture duration (in minutes): ");
                                    int lectureDuration = Integer.parseInt(scanner.nextLine());

                                    Lecture newLecture = new Lecture(lectureTitle, lectureContent, lectureDuration);
                                    ((Instructor) user).addLectureToCourse(courseNumber, newLecture);
                                    System.out.println("Lecture added successfully!");
                                } else {
                                    System.out.println("Invalid course number!");
                                }
                            } else if (instructorChoice == 5) {
                                System.out.println("Courses created by you:");
                                List<Course> courses = ((Instructor) user).getCreatedCourses();
                                for (int i = 0; i < courses.size(); i++) {
                                    System.out.println((i + 1) + ". " + courses.get(i).getTitle());
                                }
                                System.out.print("Enter course number to upload material: ");
                                int courseNumber = Integer.parseInt(scanner.nextLine()) - 1;

                                if (courseNumber >= 0 && courseNumber < courses.size()) {
                                    System.out.print("Enter material (e.g., PDF or video URL): ");
                                    String material = scanner.nextLine();

                                    ((Instructor) user).uploadMaterialToCourse(courseNumber, material);
                                } else {
                                    System.out.println("Invalid course number!");
                                }
                            } else if (instructorChoice == 6) {
                                System.out.println("Courses created by you:");
                                List<Course> courses = ((Instructor) user).getCreatedCourses();
                                for (int i = 0; i < courses.size(); i++) {
                                    System.out.println((i + 1) + ". " + courses.get(i).getTitle());
                                }
                                System.out.print("Enter course number to view enrolled students: ");
                                int courseNumber = Integer.parseInt(scanner.nextLine()) - 1;

                                if (courseNumber >= 0 && courseNumber < courses.size()) {
                                    ((Instructor) user).viewEnrolledStudents(courseNumber);
                                } else {
                                    System.out.println("Invalid course number!");
                                }
                            } else if (instructorChoice == 7) {
                                System.out.print("Enter new username: ");
                                String newUsername = scanner.nextLine();
                                System.out.print("Enter new password: ");
                                String newPassword = scanner.nextLine();
                                System.out.print("Enter new email: ");
                                String newEmail = scanner.nextLine();

                                user.setUsername(newUsername);
                                user.setPassword(newPassword);
                                user.setEmail(newEmail);
                                System.out.println("Profile updated successfully!");
                            } else if (instructorChoice == 8) {
                                break;
                            } else {
                                System.out.println("Invalid choice!");
                            }
                        }
                    } else if (user instanceof Student) {
                        while (true) {
                            System.out.println("Courses enrolled by you:");
                            for (Course c : ((Student) user).getEnrolledCourses()) {
                                System.out.println(c.getTitle() + ": " + c.getDescription());
                            }
                            System.out.println("1. Enroll in a course");
                            System.out.println("2. View lectures");
                            System.out.println("3. Update profile");
                            System.out.println("4. Logout");
                            System.out.print("Choose an option: ");
                            int studentChoice = Integer.parseInt(scanner.nextLine());

                            if (studentChoice == 1) {
                                System.out.println("Available courses:");
                                List<Course> availableCourses = platform.getCourses();
                                for (int i = 0; i < availableCourses.size(); i++) {
                                    Course c = availableCourses.get(i);
                                    System.out.println((i + 1) + ". " + c.getTitle() + " by " + c.getInstructor().getUsername());
                                }
                                System.out.print("Enter course number to enroll: ");
                                int courseNumber = Integer.parseInt(scanner.nextLine()) - 1;

                                if (courseNumber >= 0 && courseNumber < availableCourses.size()) {
                                    Course selectedCourse = availableCourses.get(courseNumber);
                                    selectedCourse.enrollStudent((Student) user);
                                    System.out.println("Enrolled in course successfully!");
                                } else {
                                    System.out.println("Invalid course number!");
                                }
                            } else if (studentChoice == 2) {
                                ((Student) user).viewLectures();
                            } else if (studentChoice == 3) {
                                System.out.print("Enter new username: ");
                                String newUsername = scanner.nextLine();
                                System.out.print("Enter new password: ");
                                String newPassword = scanner.nextLine();
                                System.out.print("Enter new email: ");
                                String newEmail = scanner.nextLine();

                                user.setUsername(newUsername);
                                user.setPassword(newPassword);
                                user.setEmail(newEmail);
                                System.out.println("Profile updated successfully!");
                            } else if (studentChoice == 4) {
                                break;
                            } else {
                                System.out.println("Invalid choice!");
                            }
                        }
                    }
                } else {
                    System.out.println("Login failed!");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
}
