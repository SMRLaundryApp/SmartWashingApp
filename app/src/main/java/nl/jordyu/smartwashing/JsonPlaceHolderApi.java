package nl.jordyu.smartwashing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET("washmachine.php?id=1")
    Call<List<Post>> getPosts();

    @GET("washmachine.php")
    Call<List<Post>> washProgram(@Query("id") int imageId);

}
