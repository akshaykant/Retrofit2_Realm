package com.example.akant.retrofitrealm;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.akant.retrofitrealm.databinding.ActivityMainBinding;
import com.example.akant.retrofitrealm.services.APIService;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    APIService getTodos;
    public static final String ENDPOINT_URL = "http://www.ssaurel.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);



        //Configure Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ENDPOINT_URL)
                // Add Stetho interceptor
                .client(new OkHttpClient.Builder()
                        .addNetworkInterceptor(new StethoInterceptor())
                        .build())

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getTodos = retrofit.create(APIService.class);

        binding.oneBtn.setOnClickListener(this);
        binding.getAllBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.oneBtn) {

            loadTodos(1);
        } else if (view.getId() == R.id.getAllBtn) {
            loadTodos();
        }
    }

    private void loadTodos() {
        Call<Result> call = getTodos.all();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                displayResult(result);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    private void displayResult(Result r) {
        if (r != null) {
            List<Todo> todos = r.getTodos();
            String tmp = "";

            for (Todo todo : todos) {
                tmp += todo.getId() + " | " + todo.getTitle() + " | " + (todo.getCompleted() ? "Completed" : "Todo" + "\n");
            }
            binding.resultTv.setText(tmp);
        } else {
            binding.resultTv.setText("Error to get Todos");
        }
    }

    private void loadTodos(int id) {
        Call<Todo> call = getTodos.select(id);
        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                displayTodo(response.body());
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {

            }
        });
    }

    private void displayTodo(Todo todo) {
        if (todo != null) {
            String tmp = todo.getId() + " | " + todo.getTitle() + " | " + (todo.getCompleted() ? "Completed" : "Todo");
            binding.resultTv.setText(tmp);
        } else {
            binding.resultTv.setText("Error to get Todos");
        }
    }
}




