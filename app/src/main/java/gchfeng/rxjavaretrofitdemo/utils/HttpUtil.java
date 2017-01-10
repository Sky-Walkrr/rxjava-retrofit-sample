package gchfeng.rxjavaretrofitdemo.utils;

import java.util.concurrent.TimeUnit;

import gchfeng.rxjavaretrofitdemo.entity.MovieEntity;
import gchfeng.rxjavaretrofitdemo.request.MovieDataService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by walker on 2017/1/6.
 */

public class HttpUtil {

    private static final String baseUrl = "https://api.douban.com/v2/movie/";
    private static final int TIMEOUT = 5;

    private MovieDataService movieDataService;

    private HttpUtil() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieDataService = retrofit.create(MovieDataService.class);

    }

    //singleton holder
    private static class singletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    //instance
    public static HttpUtil getInstance() {
        return singletonHolder.INSTANCE;
    }

    public void getTopMovie(Subscriber<MovieEntity> subscriber,int start,int count) {
        movieDataService.getTopMovie(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
