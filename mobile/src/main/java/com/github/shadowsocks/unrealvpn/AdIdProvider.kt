package com.github.shadowsocks.unrealvpn

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import java.io.IOException

class AdIdProvider(private val context: Context) {

    interface AdIdListener {
        fun onAdIdObtained(adId: String)
        fun onAdIdError()
    }

    fun getAdvertisingId(listener: AdIdListener) {
        GetAdvertisingIdTask(context, listener).execute()
    }

    private class GetAdvertisingIdTask(
            private val context: Context,
            private val listener: AdIdListener
    ) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String {
            try {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                return adInfo.id ?: ""
            } catch (e: IOException) {
                Log.e("AdvertisingId", "IOException: ${e.message}")
                e.printStackTrace()
            } catch (e: IllegalStateException) {
                Log.e("AdvertisingId", "IllegalStateException: ${e.message}")
                e.printStackTrace()
            } catch (e: GooglePlayServicesNotAvailableException) {
                Log.e("AdvertisingId", "GooglePlayServicesNotAvailableException: ${e.message}")
                e.printStackTrace()
            } catch (e: GooglePlayServicesRepairableException) {
                Log.e("AdvertisingId", "GooglePlayServicesRepairableException: ${e.message}")
                e.printStackTrace()
            }

            return "qwe"
        }

        override fun onPostExecute(result: String) {
            if (result.isNotEmpty()) {
                // Ensure the callback is executed on the main thread
                Handler(Looper.getMainLooper()).post {
                    listener.onAdIdObtained(result)
                }
            } else {
                // Ensure the callback is executed on the main thread
                Handler(Looper.getMainLooper()).post {
                    listener.onAdIdError()
                }
            }
        }
    }
}
