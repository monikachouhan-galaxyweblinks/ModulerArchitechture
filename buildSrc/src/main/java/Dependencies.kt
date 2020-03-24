/**
 * All dependencies and gradle constants declared here.
 */
object ApplicationId {
    const val id = "com.gwl"
}

/**
 * FeatureModules defines set of modules dependencies used in project.
 * Add dynamic module dependency here.
 */
object FeatureModules {
    var modules = arrayOf(
        Modules.home,
        Modules.launcher,
        Modules.login,
        Modules.profile,
        Modules.fingure,
        Modules.mpin,
        Modules.googleLogin,
        Modules.search,
        Modules.fbLogin,
        Modules.instaFeed,
        Modules.settings,
        Modules.signup
    )

    val settingModules = arrayOf(
        Modules.app,
        Modules.networking,
        Modules.exoplayerLib,
        Modules.playerCore,
        Modules.model,
        Modules.core,
        Modules.navigation,
        Modules.login,
        Modules.home,
        Modules.profile,
        Modules.launcher,
        Modules.cache,
        Modules.presentation,
        Modules.mpinauthentication,
        Modules.fingure,
        Modules.googleLogin,
        Modules.signup,
        Modules.instaFeed,
        Modules.settings,
        Modules.search,
        Modules.fbLogin
    )
}

/**
 *  Every module used in project declare here.
 */
object Modules {
    val app = ":app"
    val navigation = ":navigation"
    val cache = ":common:cache"

    val core = ":common:core"
    val networking = ":common:networking"
    val presentation = ":common:core"
    val model = ":common:model"
    val home = ":features:home"
    val settings = ":features:settings"
    val login = ":features:login"
    val exoplayerLib = ":libraries:exoplayer"
    val playerCore = ":libraries:player-core"
    val launcher = ":features:launcher"
    val profile = ":features:profile"

    /*val player = ":features:player_feeds"*/
    val instaFeed = ":features:insta_feeds"
    val signup = ":features:signup"
    val mpinauthentication = ":features:authenticationLock:mpin:mpinauthentication"
    val mpin = ":features:authenticationLock:mpin"
    val fingure = ":features:authenticationLock:fingure"
    val googleLogin = ":features:social_login:google_login"
    val fbLogin = ":features:social_login:fblogin"
    val search = ":features:search"
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

/**
 * Libraries version declare here.
 */
object Versions {
    val gradle = "3.5.2"

    val compileSdk = 28
    val minSdk = 18
    val targetSdk = 28

    val googleAuth = "16.0.1"

    val googleServices = "4.3.3"

    val firebaseAuth = "16.0.4"
    val firebaseCore = "16.0.4"
    val facebookSdk = "4.30.0"

    val fabric = "1.27.1"
    val multidex = "2.0.1"

    val appcompat = "1.0.2"
    val design = "1.2.0-alpha03"
    val cardview = "1.0.0"
    val recyclerview = "1.0.0"
    val constraint = "1.1.3"
    val maps = "15.0.1"

    val ktx = "1.0.0-alpha1"
    val coroutines = "1.2.0"

    val kotlin = "1.3.50"
    val timber = "4.7.1"
    val rxjava = "2.2.6"
    val rxkotlin = "2.3.0"
    val rxAndroid = "2.1.1"
    val retrofit = "2.5.0"
    val loggingInterceptor = "3.12.1"
    val glide = "4.9.0"
    val rxpaper = "1.4.0"
    val paperdb = "2.6"
    val moshi = "1.8.0"
    val lifecycle = "2.0.0"
    val leakCanary = "1.6.3"
    val crashlytics = "2.9.9"
    val koin = "2.0.0-beta-1"
    val shimmerVersion = "2.1.0"
    val releaseVersion = "3.7.0.2905-A1"
    val exoPlayer2Version = "2.10.1"
    val butterKnifVersion = "10.1.0"

    val roomVersion = "2.2.3"
    val roomCompiler = "2.2.3"
    val playCore = "1.6.4"
    val pagingVersion = "2.1.1"

    val databindingCompiler = "3.5.3"

    val junit = "4.12"
    val assertjCore = "3.12.0"
    val mockitoKotlin = "2.1.0"
    val mockitoInline = "2.24.5"
}

// Libraries used in modules
object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    val multidex = "androidx.multidex:multidex:${Versions.multidex}"

    val coroutinescore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutinesandroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    val kotlincoroutinesretrofit = "ru.gildor.coroutines:kotlin-coroutines-retrofit:1.1.0"
    val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitgson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val exoPlayer = "com.google.android.exoplayer:exoplayer:${Versions.exoPlayer2Version}"
    val butterknif = "com.jakewharton:butterknife:${Versions.butterKnifVersion}"
    val butterknifCompiler = "com.jakewharton:butterknife-compiler:${Versions.butterKnifVersion}"
    val flexBox = "com.google.android:flexbox:1.1.0"

    //paging
    val paging = "androidx.paging:paging-runtime:${Versions.pagingVersion}"

    val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"

    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"


    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    // Room
    val roomDB = "androidx.room:room-ktx:${Versions.roomVersion}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    val paperdb = "io.paperdb:paperdb:${Versions.paperdb}"
    val rxpaper = "com.github.pakoito:RxPaper2:${Versions.rxpaper}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"

    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    val leakCanaryAndroid = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    val leakCanaryAndroidNoOp =
        "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanary}"
    val leakCanaryAndroidSupportFragment =
        "com.squareup.leakcanary:leakcanary-support-fragment:${Versions.leakCanary}"

    val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"
    val databinding = "androidx.databinding:databinding-compiler:${Versions.databindingCompiler}"
    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    val shimmer = "io.supercharge:shimmerlayout:${Versions.shimmerVersion}"

}

object SupportLibraries {
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val design = "com.google.android.material:material:${Versions.design}"
    val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    val constrainLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
}

object GoogleLibraries {
    val auth = "com.google.android.gms:play-services-auth:${Versions.googleAuth}"
    val playCore = "com.google.android.play:core:${Versions.playCore}"
}

object FirebaseLibraries {
    val auth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    val core = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
}

object FacebookLibraries {
    val sdk = "com.facebook.android:facebook-android-sdk:${Versions.facebookSdk}"
}

object TestLibraries {
    val junit = "junit:junit:${Versions.junit}"
    val assertjCore = "org.assertj:assertj-core:${Versions.assertjCore}"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    val lifecycleTesting = "androidx.arch.core:core-testing:${Versions.lifecycle}"
}
