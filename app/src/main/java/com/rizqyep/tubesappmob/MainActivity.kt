package com.rizqyep.tubesappmob

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val movieList = ArrayList<MovieResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swiperefresh.setOnRefreshListener {
            movieList.clear()
            showMovies()
        }

        var message = intent.getStringExtra("success")

        if (message == null) {
            message = "extra not set"
            tvResponseCode.text = message
        }
        else{
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Berhasil")
                .setContentText(message)
                .setConfirmText("Lanjut")
                .show()
            message = null
        }


        newMovieButton.setOnClickListener{
            val intent = Intent(this, CreateMovieActivity::class.java)
            startActivity(intent)
        }
        this.showMovies()

    }



    private fun showMovies(){
        rvMovie.setHasFixedSize(true)
        rvMovie.layoutManager = LinearLayoutManager(this)

        RetrofitClient.instance.getMovies().enqueue(object : Callback<ArrayList<MovieResponse>>{
            override fun onFailure(call: Call<ArrayList<MovieResponse>>, t: Throwable) {
                tvResponseCode.text = t.message
            }

            override fun onResponse(
                call: Call<ArrayList<MovieResponse>>,
                response: Response<ArrayList<MovieResponse>>
            ) {
                val responseCode = response.code().toString()
                tvResponseCode.text = ""
                response.body()?.let{movieList.addAll(it)}
                print(movieList)
                val adapter = MovieAdapter(movieList)
                rvMovie.adapter = adapter
                swiperefresh.isRefreshing = false
            }

        })
    }


}