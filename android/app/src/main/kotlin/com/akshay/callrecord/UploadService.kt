package com.akshay.callrecord

import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object UploadService {

    private const val BASE_URL = "https://yourserver.com/api/"

    interface ApiService {
        @Multipart
        @POST("upload")
        suspend fun uploadFile(
            @Part file: MultipartBody.Part
        ): retrofit2.Response<Any>
    }

    private val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun uploadRecording(context: Context, file: File) {
        // Run the upload in the background
        print("Hey it's ready uploading")
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val requestBody = file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
                val multipart = MultipartBody.Part.createFormData("file", file.name, requestBody)
                val response = api.uploadFile(multipart)

                if (response.isSuccessful) {
                    // File uploaded successfully
                    println("Upload successful: ${response.body()}")
                } else {
                    // Handle upload failure
                    println("Upload failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // Handle exceptions
                e.printStackTrace()
            }
        }
    }
}
