package ph.sdsolutions.studentsapp.activity.add_student.view;

public interface AddStudentContract {

    interface View{
        void onSuccess();
        void onSuccessUpdate();
        void onError(int code, String message);
    }

    interface Presenter{
        void onAddStudent(String token, String firstName,String lastName,String middleName,String gender,int age,String address,String course);
        void onUpdateStudent(String token,int id,String firstName,String lastName,String middleName,String gender,int age,String address,String course);
    }
}
