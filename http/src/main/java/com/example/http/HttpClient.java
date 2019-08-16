package com.example.http;


import com.waw.hr.mutils.base.BaseBean;
import com.waw.hr.mutils.base.BaseBeanEntity;
import com.waw.hr.mutils.bean.BillListBean;
import com.waw.hr.mutils.bean.RateModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by jingbin on 16/11/21.
 * 网络请求类（一个接口一个方法）
 */
public interface HttpClient {

    class Builder {

        public static HttpClient getServer() {
            return HttpUtils.getInstance().getGuodongServer(HttpClient.class);
        }

        public static HttpClient getOtherServer() {
            return HttpUtils.getInstance().getGuodongServer(HttpClient.class);
        }
    }


    @FormUrlEncoded
    @POST("api/complete")
    Observable<BaseBean<Map>> editInfo(@Header("token") String token,@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/register")
    Observable<BaseBean<String>> register(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/login")
    Observable<BaseBean<String>> login(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/pwdfind")
    Observable<BaseBean<List>> pwdfind(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/code")
    Observable<BaseBean<String>> getcode(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("api/checkVerify")
    Observable<BaseBean<String>> checkVerify(@FieldMap Map<String, Object> params);

//    @FormUrlEncoded
    @GET("api/index")
    Observable<BaseBean<Map>> index();

    @FormUrlEncoded
    @POST("api/applyLimit")
    Observable<BaseBean<Object>> applyLimit(@Header("token") String token,@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("api/delayRefund")
    Observable<BaseBean<Object>> delayRefund(@Header("token") String token,@FieldMap Map<String, Object> params);


    @GET("api/allRefund")
    Observable<BaseBean<Object>> allRefund(@Header("token") String token,@QueryMap Map<String, Object> params);

    @GET("api/refundRecord")
    Observable<BaseBean<Map>> refundRecord(@Header("token") String token,@QueryMap Map<String, Object> params);

    @GET("api/myAccount")
    Observable<BaseBean<Map>> myAccount(@Header("token") String token);

    @GET("api/rateitem")
    Observable<BaseBean<List<RateModel>>> rateitem(@Header("token") String token);

    @GET("api/myindex")
    Observable<BaseBean<Map>> myindex(@Header("token") String token);

    @GET("api/subRefund1")
    Observable<BaseBean<Object>> subRefund1(@Header("token") String token);

    @GET("api/subApply")
    Observable<BaseBean<Object>> subApply(@Header("token") String token);

    @FormUrlEncoded
    @POST("api/withdrawDeposit")
    Observable<BaseBean<Object>> withdrawDeposit(@Header("token") String token,@FieldMap Map<String, Object> params);

    @GET("api/system")
    Observable<BaseBean<Map>> system();


    @Multipart
    @POST("api/upload")
    Observable<BaseBean<String>> upload(@Header("token") String token,@Part MultipartBody.Part img);

    @Multipart
    @POST("api/updateImg")
    Observable<BaseBean<String>> updateImg(@Header("token") String token,@Part MultipartBody.Part img);

}