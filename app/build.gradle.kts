plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "rx.team.renxinyileng.rxbox"
    compileSdk = 34

    defaultConfig {
        applicationId = "rx.team.renxinyileng.rxbox"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.玩具"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //添加okhttp3
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    //MockWeb服务器
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    //增加pdf支持
    implementation ("es.voghdev.pdfviewpager:library:1.1.2")
    //添加播放器支持
    implementation ("com.aliyun.sdk.android:AliyunPlayer:6.10.0-full")
    //添加json解析
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}