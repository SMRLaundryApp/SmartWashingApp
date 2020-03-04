package nl.jordyu.smartwashing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("washmachine.php")
    Call<List<Post>> getPosts();

}
