package com.rizqyep.tubesappmob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.android.synthetic.main.activity_edit_movie.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMovieActivity : AppCompatActivity() {
    private var nameField = ""
    private var yearField = ""
    private var ratingField = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Movflix"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getStringExtra("id").toString()

        //Set Data

        this.getMovieData(movieId)

        submit.setOnClickListener {
            this.updateMovie(movieId, this.nameField, this.yearField, this.ratingField)
        }
    }


    private fun getMovieData(id: String) {
        RetrofitClient.instance.getMovie(id).enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                tvInputResult.text = t.message
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                nameInput.setText(response.body()?.name)
                yearInput.setText(response.body()?.year_released)
                ratingInput.setText(response.body()?.rating.toString())

                this@EditMovieActivity.nameField = nameInput.text.toString()
                this@EditMovieActivity.yearField = yearInput.text.toString()
                this@EditMovieActivity.ratingField = ratingInput.text.toString().toDouble()
            }

        })

    }

    private fun updateMovie(id: String, name: String, year_released: String, rating: Double) {
        RetrofitClient.instance.updateMovie(
            id,
            name,
            year_released,
            rating
        ).enqueue(object : Callback<MovieResponse> {

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                SweetAlertDialog(this@EditMovieActivity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("Terjadi kesalahan")
                    .setConfirmText("Coba Lagi")
                    .show()
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val intent = Intent(this@EditMovieActivity, MainActivity::class.java)
                intent.putExtra("success", "Item berhasil di Update!")
                startActivity(intent)
                finish()
            }

        })
    }

}