package com.rizqyep.tubesappmob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_movie.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_movie)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "New Activity"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        submit.setOnClickListener{
            val movieName = nameInput.text.toString()
            val yearReleased = yearInput.text.toString()
            val ratingVal = ratingInput.text.toString().toDouble()

            this.createMovie(movieName, yearReleased, ratingVal)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("success", "Film berhasil ditambahkan ke watchlist!")
            startActivity(intent)
            finish()
        }
    }

    private fun createMovie(name:String , year_released:String, rating:Double){
        RetrofitClient.instance.createMovie(
            name,
            year_released,
            rating
        ).enqueue(object : Callback<CreateMovieResponse> {
            override fun onFailure(call: Call<CreateMovieResponse>, t: Throwable) {
                tvResponseCode.text = t.message
            }

            override fun onResponse(
                call: Call<CreateMovieResponse>,
                response: Response<CreateMovieResponse>
            ) {
                val responseText = "Response code : ${response.code()}\n" +
                        "${response.body()?._id}\n" +
                        "${response.body()?.name}\n" +
                        "${response.body()?.year_released}\n" +
                        "${response.body()?.rating}"
//                tvInputResult.text = responseText
            }

        })
    }
}