package ph.sdsolutions.studentsapp.activity.student_list.presenter;

import org.json.JSONObject;

import java.util.List;

import ph.sdsolutions.studentsapp.activity.add_student.model.Student;
import ph.sdsolutions.studentsapp.activity.student_list.model.GetStudents;
import ph.sdsolutions.studentsapp.activity.student_list.view.StudentListContract;
import ph.sdsolutions.studentsapp.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListPresenter implements StudentListContract.Presenter {

    StudentListContract.View view;

    public StudentListPresenter(StudentListContract.View view) {
        this.view = view;
    }

    @Override
    public void onGetStudents(String token) {
        Call<List<GetStudents>> call = RetrofitInstance.getApiService().getStudents("Bearer "+token);
        call.enqueue(new Callback<List<GetStudents>>() {
            @Override
            public void onResponse(Call<List<GetStudents>> call, Response<List<GetStudents>> response) {
                if (response.code() >= 400 /*&& response.code() < 599*/) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        view.onError(response.code(), jObjError.getString("Message"));
                    } catch (Exception e) {
                        view.onError(response.code(), "");
                        e.printStackTrace();
                    }
                } else {
                    view.onSuccessGetStudents(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GetStudents>> call, Throwable t) {
                view.onError(t.hashCode(),t.getLocalizedMessage());
            }
        });
    }
}
