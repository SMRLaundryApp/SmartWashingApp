package nl.jordyu.smartwashing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    final String urlToImageServer = "https://jordyu.nl/washmachine.php?id=1";
    private TextView textViewTemperature; //temperatureTxt
    private TextView washingTypeTxt; //washingTypeTxt
    private TextView receiveDataTxt; //receiveDataTxt
    private TextView statusCodeTxt; //statusCodeTxt

    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTemperature = findViewById(R.id.temperatureTxt);
        washingTypeTxt = findViewById(R.id.washingTypeTxt);
        receiveDataTxt = findViewById(R.id.receiveDataTxt);
        statusCodeTxt = findViewById(R.id.statusCodeTxt);


    }

    public void clothesBtn1OnClick(View view) {
        System.out.println("Clothes button 1 pressed");
        getWashingProgramOld();

    }

    public void getWashingProgramOld() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jordyu.nl/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                int responseCode = response.code(); //Show response code
                statusCodeTxt.setText(Integer.toString(responseCode));
                if (!response.isSuccessful()) { // if responseCode is not in 200-299

                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "Temperature: " + post.getTemperature() + "\n";
                    content += "Washing type: " + post.getWashingType() + "\n\n";
                    receiveDataTxt.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                receiveDataTxt.setText(t.getMessage());

            }
        });

    }

}
