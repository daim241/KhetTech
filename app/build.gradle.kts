plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {

    namespace = "com.example.khettech"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.khettech"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
    buildFeatures{
        viewBinding =true
        mlModelBinding = true
    }


}

dependencies {

    implementation ("androidx.activity:activity-ktx:1.8.2")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation ("androidx.recyclerview:recyclerview:1.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.activity:activity:1.8.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
//    implementation("com.android.tools.compose:compose-preview-renderer:0.0.1-alpha01")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.google.code.gson:gson:2.10.1")


    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-firestore:24.0.1")

    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    kapt("androidx.lifecycle:lifecycle-common-java8:2.7.0")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.16")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.google.android.material:material:1.4.0")

    val lifecycle_version = "2.7.0"
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    kapt("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")



    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")//KTX Extensions/Coroutines for Room
    kapt("androidx.room:room-compiler:$room_version")


    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.46")

    implementation ("com.android.support:multidex:1.0.3")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")
    implementation ("com.squareup.retrofit:adapter-rxjava:2.0.0-beta1")





}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}