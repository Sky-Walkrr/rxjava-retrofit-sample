package gchfeng.rxjavaretrofitdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gchfeng.rxjavaretrofitdemo.R;
import gchfeng.rxjavaretrofitdemo.entity.MovieEntity;
import gchfeng.rxjavaretrofitdemo.request.MovieDataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gchfeng on 2017/1/5.
 */

public class MainActivity extends Activity {

    @Bind(R.id.tvTest)
    TextView tvTest;
    @Bind(R.id.btnTest)
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private String baseUrl = "https://api.douban.com/v2/movie/";

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDataService movieDataService = retrofit.create(MovieDataService.class);
        Call<MovieEntity> call = movieDataService.getTopMovie(0,10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {

            }
        });
    }
}
