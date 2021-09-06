package ph.sdsolutions.studentsapp.activity.student_list.view;

import java.util.List;

import ph.sdsolutions.studentsapp.activity.student_list.model.GetStudents;

public interface StudentListContract {

    interface View{
        void onSuccessGetStudents(List<GetStudents> students);
        void onError(int code, String message);
    }

    interface Presenter{
        void onGetStudents(String token);
    }
}
