package gchfeng.rxjavaretrofitdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import gchfeng.rxjavaretrofitdemo.BR;
import gchfeng.rxjavaretrofitdemo.R;
import gchfeng.rxjavaretrofitdemo.databinding.ActivityMainBinding;
import gchfeng.rxjavaretrofitdemo.entity.MovieEntity;
import gchfeng.rxjavaretrofitdemo.entity.SubjectsEntity;
import gchfeng.rxjavaretrofitdemo.entity.User;
import gchfeng.rxjavaretrofitdemo.request.MovieDataService;
import gchfeng.rxjavaretrofitdemo.subscriber.ProgressSubscriber;
import gchfeng.rxjavaretrofitdemo.subscriber.SubscribeOnNextListener;
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
    @Bind(R.id.btnTest2)
    Button btnTest2;

    private static final String TAG = "MainActivity";

    private SubscribeOnNextListener getTopMovieOnNextListener;
    private User user =  new User("Walker");
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setUser(user);
        activityMainBinding.setPresenter(new Presenter());
        ButterKnife.bind(this);

        getTopMovieOnNextListener = new SubscribeOnNextListener() {
            @Override
            public void onNext(Object o) {
                if (o instanceof MovieEntity) {
                    MovieEntity movieEntity = (MovieEntity) o;
                    tvTest.setText(TextUtils.isEmpty(movieEntity.getTitle()) ? "" : movieEntity.getTitle());
                }
            }
        };

        initViews();
    }

    public class Presenter {
        //方法绑定：方法名跟原名完全一致
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            user.setName(s.toString());
            activityMainBinding.setVariable(BR.user,user);
        }

        //监听器绑定
        public void textClickListener(User user) {
            Toast.makeText(MainActivity.this, user.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getData();
                requestData();
            }
        });

        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,InstalledApplicationActivity.class));
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
//        HttpUtil.getInstance().getTopMovie(subscriber,0,10);
        HttpUtil.getInstance().getTopMovie(new ProgressSubscriber<MovieEntity>(getTopMovieOnNextListener,this),0,10);
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
