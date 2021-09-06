package ph.sdsolutions.studentsapp.activity.student_list.model;

public class GetStudents {

    public int Id;
    public String Firstname;
    public String Lastname;
    public String Middlename;
    public String Gender;
    public int Age;
    public String Address;
    public String Course;

    public GetStudents(int id, String firstname, String lastname, String middlename, String gender, int age, String address, String course) {
        Id = id;
        Firstname = firstname;
        Lastname = lastname;
        Middlename = middlename;
        Gender = gender;
        Age = age;
        Address = address;
        Course = course;
    }
}
