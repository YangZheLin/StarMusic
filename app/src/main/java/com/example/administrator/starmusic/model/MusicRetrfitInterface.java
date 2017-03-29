package com.example.administrator.starmusic.model;

import com.example.administrator.starmusic.entity.Music;
import com.squareup.okhttp.ResponseBody;

import java.io.InputStream;
import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by adminwk on 2017/1/25.
 */

public interface MusicRetrfitInterface {
    @GET("Login")
    Call<ResponseBody> getLoginMessage(@Query("UserNumber") String usernumber,@Query("UserPass") String usepass);


    @GET("Register")
    Call<ResponseBody> getRegisterMessage(@Query("UserNumber") String userNumber,@Query("UserPass") String usepass,@Query("UserName") String username,@Query("UserAge") String  userAge,@Query("UserSex") String userSex);


}
