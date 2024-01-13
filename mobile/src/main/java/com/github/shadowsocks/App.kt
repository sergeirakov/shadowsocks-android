/*******************************************************************************
 *                                                                             *
 *  Copyright (C) 2017 by Max Lv <max.c.lv@gmail.com>                          *
 *  Copyright (C) 2017 by Mygod Studio <contact-shadowsocks-android@mygod.be>  *
 *                                                                             *
 *  This program is free software: you can redistribute it and/or modify       *
 *  it under the terms of the GNU General Public License as published by       *
 *  the Free Software Foundation, either version 3 of the License, or          *
 *  (at your option) any later version.                                        *
 *                                                                             *
 *  This program is distributed in the hope that it will be useful,            *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 *  GNU General Public License for more details.                               *
 *                                                                             *
 *  You should have received a copy of the GNU General Public License          *
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *                                                                             *
 *******************************************************************************/

package com.github.shadowsocks

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.github.shadowsocks.unrealvpn.AdIdProvider
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig


var advertisingId: String = "empty_ads_id"
class App : Application(), androidx.work.Configuration.Provider by Core {
    override fun onCreate() {
        super.onCreate()
        Core.init(this, MainActivity::class)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)


        // Call the AdIdProvider to get the Advertising ID when the application is launched
        AdIdProvider(this).getAdvertisingId(object : AdIdProvider.AdIdListener {
            override fun onAdIdObtained(adId: String) {
                // Save the Advertising ID to the global variable
                advertisingId = adId

                // Now you can use the adId variable as needed
                // For example, you can access it globally in your application

                // Print the Advertising ID to the console for testing
                Log.d("AdvertisingId", "Advertising ID obtained when the application is launched: $adId")
            }

            override fun onAdIdError() {
                // Handle the case where the Advertising ID couldn't be retrieved
                Log.e("AdvertisingId", "Error obtaining Advertising ID when the application is launched")
            }
        })

        val config = AppMetricaConfig.newConfigBuilder(APP_METRICA_API_KEY).build()
        AppMetrica.activate(this, config)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Core.updateNotificationChannels()
    }


   
    companion object {
        private const val APP_METRICA_API_KEY = "dae05da9-a716-4b74-98f3-ec7a30630531"
    }

}
