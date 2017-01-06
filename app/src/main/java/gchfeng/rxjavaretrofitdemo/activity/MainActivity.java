package gchfeng.rxjavaretrofitdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gchfeng.rxjavaretrofitdemo.R;
import gchfeng.rxjavaretrofitdemo.entity.MovieEntity;
import gchfeng.rxjavaretrofitdemo.entity.SubjectsEntity;
import gchfeng.rxjavaretrofitdemo.request.MovieDataService;
import gchfeng.rxjavaretrofitdemo.utils.HttpUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by gchfeng on 2017/1/5.
 */

public class MainActivity extends Activity {

    @Bind(R.id.tvTest)
    TextView tvTest;
    @Bind(R.id.btnTest)
    Button btnTest;

    private static final String TAG = "MainActivity";

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
//                requestData();
            }
        });
    }

    private void requestData() {
        Subscriber<MovieEntity> subscriber = new Subscriber<MovieEntity>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "requestOnFailure", e);
            }

            @Override
            public void onNext(MovieEntity movieEntity) {
                if (movieEntity != null) {
                    tvTest.setText(TextUtils.isEmpty(movieEntity.getTitle()) ? "" : movieEntity.getTitle());
                }
            }
        };
        HttpUtil.getInstance().getTopMovie(subscriber,0,10);
    }

    private void getData() {

        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        final MovieDataService movieDataService = retrofit.create(MovieDataService.class);
//        Call<MovieEntity> call = movieDataService.getTopMovie(0,10);
//        call.enqueue(new Callback<MovieEntity>() {
//            @Override
//            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
//                MovieEntity movieEntity = response.body();
//                if (movieEntity != null) {
//                    tvTest.setText(TextUtils.isEmpty(movieEntity.getTitle()) ? "" : movieEntity.getTitle());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieEntity> call, Throwable t) {
//                Log.e(TAG,"requestOnFailure",t);
//            }
//        });

//        movieDataService.getTopMovie(0,10)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<MovieEntity>() {
//                    @Override
//                    public void onCompleted() {
//                        Toast.makeText(MainActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "requestOnFailure", e);
//                    }
//
//                    @Override
//                    public void onNext(MovieEntity movieEntity) {
//                        if (movieEntity != null) {
//                            tvTest.setText(TextUtils.isEmpty(movieEntity.getTitle()) ? "" : movieEntity.getTitle());
//                        }
//                    }
//                });

        movieDataService.getTopMovie(0,10)
                .map(new HttpFunc<MovieEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SubjectsEntity>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "requestOnFailure", e);
                    }

                    @Override
                    public void onNext(List<SubjectsEntity> subjectsEntities) {
                        if (subjectsEntities != null && !subjectsEntities.isEmpty()) {
                            tvTest.setText(TextUtils.isEmpty(subjectsEntities.get(0).getTitle()) ? "" : subjectsEntities.get(0).getTitle());
                        }
                    }
                });

    }

    class HttpFunc<T> implements Func1<MovieEntity,List<SubjectsEntity>> {
        @Override
        public List<SubjectsEntity> call(MovieEntity movieEntity) {
            if (movieEntity != null) {
                return movieEntity.getSubjects();
            }
            return null;
        }
    }
}
