package com.rizqyep.tubesappmob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Movflix"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        var movieId = intent.getStringExtra("id")
        movieId = movieId.toString()

        this.getMovieData(movieId)

        //Button Delete Clicked
        deleteButton.setOnClickListener {
            this.deleteMovie(movieId)
        }

        updateButton.setOnClickListener{

            this.goToEditPage(movieId)

        }
    }

    private fun goToEditPage(id : String){
        val intent = Intent(this, EditMovieActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun getMovieData(id : String){
        RetrofitClient.instance.getMovie(id).enqueue(object : Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                tv_movieTitle.text = t.message
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                tv_movieTitle.text = response.body()?.name
                tv_movieYear.text = response.body()?.year_released
                tv_movieRating.text = response.body()?.rating.toString()
            }

        })

    }
    private fun deleteMovie(id: String){
        RetrofitClient.instance.deleteMovie(id).enqueue(object: Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                SweetAlertDialog(this@MovieDetailActivity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("Terjadi kesalahan")
                    .setConfirmText("Coba Lagi")
                    .show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val intent = Intent(this@MovieDetailActivity, MainActivity::class.java)
                intent.putExtra("success", "Item berhasil dihapus")
                startActivity(intent)
                finish()
            }

        })
    }
}