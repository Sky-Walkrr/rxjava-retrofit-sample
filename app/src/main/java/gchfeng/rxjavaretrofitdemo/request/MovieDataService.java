package gchfeng.rxjavaretrofitdemo.request;

import gchfeng.rxjavaretrofitdemo.entity.MovieEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gchfeng on 2017/1/5.
 */

public interface MovieDataService {
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start")int start,@Query("count")int count);
}
