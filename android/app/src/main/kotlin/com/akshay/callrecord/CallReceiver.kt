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

val tag = "Call-Record-Android-Flutter"
var message = ""

class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == TelephonyManager.EXTRA_STATE_IDLE) {

            message = "Broadcast received!, Yes TelephonyManager.EXTRA_STATE_IDLE"
            Log.d(tag, message)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            // Call ended
            val recordingPath = "/storage/self/primary/Music/Recordings/Call Recordings/"
            val mostRecentFile = getMostRecentFile(recordingPath)
            if (mostRecentFile != null) {

                message = "Broadcast received!, mostRecentFile ${mostRecentFile.absolutePath}"
                Log.d(tag, message)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                UploadService.uploadRecording(context, mostRecentFile)
                val data = Data.Builder()
                    .putString("filePath", mostRecentFile.absolutePath)
                    .build()

                val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                    .setInputData(data)
                    .build()

                WorkManager.getInstance(context).enqueue(uploadWorkRequest)

            }else{

                message = "Broadcast received!, PATH IS NULL"
                Log.d(tag, message)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
        else {

            message = "Broadcast received!, No TelephonyManager.EXTRA_STATE_IDLE"
            Log.d(tag, message)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                    .baseUrl("https://3000-baneeishaqu-gitpodfullv-8rxwlxj9vof.ws-us117.gitpod.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val api = retrofit.create(UploadService.ApiService::class.java)

                val requestBody = file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
                val multipart = MultipartBody.Part.createFormData("file", file.name, requestBody)
                val response = api.uploadFile(multipart)

                if (response.isSuccessful) {
                    
                    message = "Broadcast received!, Upload Success"
                    Log.d(tag, message)
                    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
                    
                    Result.success()

                } else {
                    
                    message = "Broadcast received!, Upload Failure"
                    Log.d(tag, message)
                    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
                    
                    Result.retry()
                }
            }
        } catch (e: Exception) {

            message = "Broadcast received!, Upload Exception ${e.toString()}"
            Log.d(tag, message)
            Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
            Result.retry()
        }
    }
}
