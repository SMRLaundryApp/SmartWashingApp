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
    private Retrofit retrofit;

    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTemperature = findViewById(R.id.temperatureTxt);
        washingTypeTxt = findViewById(R.id.washingTypeTxt);
        receiveDataTxt = findViewById(R.id.receiveDataTxt);
        statusCodeTxt = findViewById(R.id.statusCodeTxt);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jordyu.nl/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    public void clothesBtn1OnClick(View view) {
        System.out.println("Clothes button 1 pressed");
        //getWashingProgramOld();
        getWashingProgram(1);

    }

    public void getWashingProgram(int imageId) {

        Call<List<Post>> call;
        call = jsonPlaceHolderApi.washProgram(imageId);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                int responseCode = response.code(); //Show response code
                statusCodeTxt.setText(Integer.toString(responseCode));
                if (!response.isSuccessful()) { // if responseCode is not in 200-299

                    return;
                }

                List<Post> posts = response.body();

                receiveDataTxt.setText("");

                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "Temperature: " + post.getTemperature() + "\n";
                    content += "Washing type: " + post.getWashingType() + "\n\n";
                    receiveDataTxt.append(content);
                    System.out.println(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println("ERROR");
                receiveDataTxt.setText(t.getMessage());
                System.out.println(t.getMessage());

            }
        });
    }

    public void getWashingProgramOld() {


        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                int responseCode = response.code(); //Show response code
                statusCodeTxt.setText(Integer.toString(responseCode));
                System.out.println(response.body());
                if (!response.isSuccessful()) { // if responseCode is not in 200-299

                    return;
                }

                List<Post> posts = response.body();

                receiveDataTxt.setText("");

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
                System.out.println("ON FAILURE:");
                System.out.println(t.getMessage());

            }
        });

    }

    public void clothesBtn2OnClick(View view) {
        System.out.println("Clothes button 2 pressed");
        getWashingProgram(2);
    }

    public void clothesBtn3OnClick(View view) {
        System.out.println("Clothes button 3 pressed");
        getWashingProgram(3);
    }
}
