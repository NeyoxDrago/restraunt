package com.example.nirnaymittal.restraunt;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Nirnay Mittal on 27-07-2018.
 */

public interface API {

     @POST("data")
     Call<Data> sendData(@Body Data data);


}
