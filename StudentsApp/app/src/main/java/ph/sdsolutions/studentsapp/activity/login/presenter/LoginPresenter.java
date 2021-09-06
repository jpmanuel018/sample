package ph.sdsolutions.studentsapp.activity.login.presenter;

import android.text.TextUtils;

import org.json.JSONObject;

import ph.sdsolutions.studentsapp.activity.login.model.Login;
import ph.sdsolutions.studentsapp.activity.login.view.LoginContract;
import ph.sdsolutions.studentsapp.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void signIn(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            view.onEmptyFields();
            return;
        }
        Call<String> call = RetrofitInstance.getApiService().signIn(new Login(username,password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() >= 400 /*&& response.code() < 599*/) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        view.onError(response.code(), jObjError.getString("Message"));
                    } catch (Exception e) {
                        view.onError(response.code(), "");
                        e.printStackTrace();
                    }
                } else {
                    view.onSuccessLogin(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.onError(t.hashCode(),t.getLocalizedMessage());
            }
        });
    }
}
