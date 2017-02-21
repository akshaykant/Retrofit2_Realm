package com.example.akant.retrofitrealm.services;

import com.example.akant.retrofitrealm.Result;
import com.example.akant.retrofitrealm.Todo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by akant on 2/21/2017.
 */

public interface APIService {

    @GET("tmp/todos")
    Call<Result> all();

    @GET("tmp/{id}/todo")
    Call<Todo> select(@Path("id") int id);

}
