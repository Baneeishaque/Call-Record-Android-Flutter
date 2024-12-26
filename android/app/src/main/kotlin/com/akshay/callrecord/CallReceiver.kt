package com.akshay.callrecord
import android.content.BroadcastReceiver
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import android.widget.Toast

class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == TelephonyManager.EXTRA_STATE_IDLE) {
            Log.d("hey1","EXTRA IDLE YEEEEEESSSSS")
            // Call ended
            val recordingPath = "/storage/self/primary/Music/Recordings/Call Recordings/"
            val mostRecentFile = getMostRecentFile(recordingPath)
            if (mostRecentFile != null) {
                UploadService.uploadRecording(context, mostRecentFile)
                val data = Data.Builder()
                    .putString("filePath", mostRecentFile.absolutePath)
                    .build()

                val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                    .setInputData(data)
                    .build()

                WorkManager.getInstance(context).enqueue(uploadWorkRequest)
            }else{
                Log.d("hey2","PATH IS NULL")
                Toast.makeText(context, "Broadcast received!, hey2 - PATH IS NULL", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Log.d("hey","EXTRA IDLE NOOOOOOOO")
            Toast.makeText(context, "Broadcast received!, hey - EXTRA IDLE NOOOOOOOO", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMostRecentFile(path: String): File? {
        val dir = File(path)
        if (dir.exists() && dir.isDirectory) {
            return dir.listFiles()?.maxByOrNull { it.lastModified() }
        }
        return null
    }
}

class UploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val appContext = context.applicationContext

    override fun doWork(): Result {
        val filePath = inputData.getString("filePath") ?: return Result.failure()
        val file = File(filePath)

        return try {
            // Use runBlocking to call the suspend function
            runBlocking {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://yourserver.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val api = retrofit.create(UploadService.ApiService::class.java)

                val requestBody = file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
                val multipart = MultipartBody.Part.createFormData("file", file.name, requestBody)
                val response = api.uploadFile(multipart)

                if (response.isSuccessful) {
                    Toast.makeText(appContext, "Upload Success", Toast.LENGTH_SHORT).show()
                    Result.success()
                } else {
                    Toast.makeText(appContext, "Upload Failure", Toast.LENGTH_SHORT).show()
                    Result.retry()
                }
            }
        } catch (e: Exception) {
            // e.printStackTrace()
            Toast.makeText(appContext, "Upload Exception ${e.toString()}", Toast.LENGTH_SHORT).show()
            Result.retry()
        }
    }
}
