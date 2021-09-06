package ph.sdsolutions.studentsapp.activity.login.view;

public interface LoginContract {

    interface View{
        void onSuccessLogin(String token);
        void onError(int code, String message);
        void onEmptyFields();
    }

    interface Presenter{
        void signIn(String username, String password);
    }
}
