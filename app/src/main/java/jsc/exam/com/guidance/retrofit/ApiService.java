package jsc.exam.com.guidance.retrofit;

import io.reactivex.Observable;
import jsc.exam.com.guidance.BuildConfig;
import retrofit2.http.GET;

public interface ApiService {

    @GET(BuildConfig.VERSION_URL)
    Observable<String> getVersionInfo();

}
