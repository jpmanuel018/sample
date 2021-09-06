package ph.sdsolutions.studentsapp.activity.add_student.model;

public class Student {

    public String first_name;
    public String last_name;
    public String middle_name;
    public String gender;
    public int age;
    public String address;
    public String course;
    public boolean is_active;

    public Student(String first_name, String last_name, String middle_name, String gender, int age, String address, String course, boolean is_active) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.course = course;
        this.is_active = is_active;
    }
}
