plugins { id("com.android.application") }

android {
    namespace = "com.drawabattle.tv"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.drawabattle.tv"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes { getByName("debug") { } }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}
