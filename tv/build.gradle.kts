plugins {
    id("com.android.application")
    id("com.google.android.gms.oss-licenses-plugin")
    kotlin("android")
    kotlin("kapt")
}

setupApp()

android {
    namespace = "com.github.shadowsocks.tv"
    defaultConfig {
        applicationId = "com.github.shadowsocks.tv"
        buildConfigField("boolean", "FULLSCREEN", "false")
    }
    flavorDimensions.add("market")
    productFlavors {
        create("freedom") {
            dimension = "market"
        }
        create("google") {
            dimension = "market"
            buildConfigField("boolean", "FULLSCREEN", "true")
        }
    }
}

dependencies {
    implementation("androidx.leanback:leanback-preference:1.1.0-rc01")
}
