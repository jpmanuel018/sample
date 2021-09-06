package ph.sdsolutions.studentsapp.activity.add_student.presenter;

import org.json.JSONObject;

import ph.sdsolutions.studentsapp.activity.add_student.model.Student;
import ph.sdsolutions.studentsapp.activity.add_student.view.AddStudentContract;
import ph.sdsolutions.studentsapp.activity.login.model.Login;
import ph.sdsolutions.studentsapp.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentPresenter implements AddStudentContract.Presenter {

    AddStudentContract.View view;

    public AddStudentPresenter(AddStudentContract.View view) {
        this.view = view;
    }

    @Override
    public void onAddStudent(String token, String firstName, String lastName, String middleName, String gender, int age, String address, String course) {
        Call<Void> call = RetrofitInstance.getApiService().addStudent("Bearer "+token,new Student(firstName,lastName,middleName,gender,age,address,course,false));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() >= 400 /*&& response.code() < 599*/) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        view.onError(response.code(), jObjError.getString("Message"));
                    } catch (Exception e) {
                        view.onError(response.code(), "");
                        e.printStackTrace();
                    }
                } else {
                    view.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onError(t.hashCode(),t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onUpdateStudent(String token, int id, String firstName, String lastName, String middleName, String gender, int age, String address, String course) {
        Call<Void> call = RetrofitInstance.getApiService().updateStudent("Bearer "+token,id,new Student(firstName,lastName,middleName,gender,age,address,course,false));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() >= 400 /*&& response.code() < 599*/) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        view.onError(response.code(), jObjError.getString("Message"));
                    } catch (Exception e) {
                        view.onError(response.code(), "");
                        e.printStackTrace();
                    }
                } else {
                    view.onSuccessUpdate();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.onError(t.hashCode(),t.getLocalizedMessage());
            }
        });
    }
}
