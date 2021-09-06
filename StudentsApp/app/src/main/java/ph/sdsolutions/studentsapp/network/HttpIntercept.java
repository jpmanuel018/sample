package ph.sdsolutions.studentsapp.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpIntercept implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!request.url().encodedPath().equalsIgnoreCase("api/auth/signin")
                || (!request.url().encodedPath().equalsIgnoreCase("api/auth/signin")
                && !request.method().equalsIgnoreCase("post"))) {
            return  chain.proceed(request);
        }
        Request newRequest = request.newBuilder()
                .addHeader("Content-Type", "undefined")
                .build();
        Response response = chain.proceed(newRequest);
        return response;
    }
}
