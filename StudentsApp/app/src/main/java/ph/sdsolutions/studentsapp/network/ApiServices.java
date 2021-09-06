package ph.sdsolutions.studentsapp.network;


import java.util.List;

import ph.sdsolutions.studentsapp.activity.add_student.model.Student;
import ph.sdsolutions.studentsapp.activity.login.model.Login;
import ph.sdsolutions.studentsapp.activity.student_list.model.GetStudents;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServices {
    //CALENDAR FOR FUTURE PICKUPS.
    @POST("api/auth/signin")
    Call<String> signIn(@Body Login login);

    @POST("api/students")
    Call<Void> addStudent(@Header("Authorization") String token, @Body Student student);

    @GET("api/students")
    Call<List<GetStudents>> getStudents(@Header("Authorization") String token);

    @PUT("api/students/{id}")
    Call<Void> updateStudent(@Header("Authorization") String token, @Path("id") int id, @Body Student student);
}
