package nl.jordyu.smartwashing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("washmachine.php")
    Call<List<Post>> getPosts();

    @GET("washmachine.php?id={id}")
    Call<List<Post>> washProgram(@Path("id") int imageId);

}
