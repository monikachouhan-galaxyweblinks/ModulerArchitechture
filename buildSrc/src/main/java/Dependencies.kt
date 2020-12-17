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
        Modules.login,
        Modules.profile,
        Modules.fingure,
        Modules.mpin,
        Modules.googleLogin,
        Modules.search,
        Modules.fbLogin,
        Modules.player,
        Modules.settings/*,
        Modules.signup*/
    )

     var settingModules = arrayOf(
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
        Modules.cache,
        Modules.mpinauthentication,
        Modules.fingure,
        Modules.googleLogin,
/*
        Modules.signup,
*/
        Modules.player,
        Modules.settings,
        Modules.search,
        Modules.fbLogin
    )
}

/**
 *  Every module used in project declare here.
 */
object Modules {
    const val app = ":app"
    const val navigation = ":navigation"
    const val cache = ":common:cache"

    const val core = ":common:core"
    const val networking = ":common:networking"
    const val presentation = ":common:core"
    const val model = ":common:model"
    const val home = ":features:home"
    const val settings = ":features:settings"
    const val login = ":features:login"
    const val exoplayerLib = ":libraries:exoplayer"
    const val playerCore = ":libraries:player-core"
    const val launcher = ":features:launcher"
    const val profile = ":features:profile"

    const val player = ":features:player_feeds"
    const val instaFeed = ":features:insta_feeds"
    const val signup = ":features:signup"
    const val mpinauthentication = ":features:authenticationLock:mpin:mpinauthentication"
    const val mpin = ":features:authenticationLock:mpin"
    const val fingure = ":features:authenticationLock:fingure"
    const val googleLogin = ":features:social_login:google_login"
    const val fbLogin = ":features:social_login:fblogin"
    const val search = ":features:search"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

/**
 * Libraries version declare here.
 */
object Versions {
    const val gradle = "3.5.2"

    const val compileSdk = 28
    const val minSdk = 18
    const val targetSdk = 28

    const val googleAuth = "16.0.1"

    const val googleServices = "4.3.4"

    const val firebaseAuth = "16.0.4"
    const val firebaseCore = "16.0.4"
    const val facebookSdk = "4.30.0"

    const val fabric = "1.27.1"
    const val multidex = "2.0.1"

    const val appcompat = "1.0.2"
    const val design = "1.2.0-alpha03"
    const val cardview = "1.0.0"
    const val recyclerview = "1.0.0"
    const val constraint = "1.1.3"
    const val maps = "15.0.1"

    const val ktx = "1.3.2"
    const val coroutines = "1.2.0"

    const val kotlin = "1.4.21"
    const val timber = "4.7.1"
    const val rxjava = "2.2.6"
    const val rxkotlin = "2.3.0"
    const val rxAndroid = "2.1.1"
    const val retrofit = "2.5.0"
    const val loggingInterceptor = "3.12.1"
    const val glide = "4.9.0"
    const val rxpaper = "1.4.0"
    const val paperdb = "2.6"
    const val moshi = "1.8.0"
    const val lifecycle = "2.0.0"
    const val leakCanary = "1.6.3"
    const val crashlytics = "2.9.9"
    const val koin = "2.0.0-beta-1"
    const val shimmerVersion = "2.1.0"
    const val swipeRefresh = "1.1.0"
    const val releaseVersion = "3.7.0.2905-A1"
    const val exoPlayer2Version = "2.10.0"
    const val butterKnifVersion = "10.1.0"

    const val roomVersion = "2.2.3"
    const val roomCompiler = "2.2.3"
    const val playCore = "1.6.4"
    const val pagingVersion = "3.0.0-alpha08"
    const val paging2Version = "2.1.1"

    const val databindingCompiler = "3.5.3"

    const val junit = "4.12"
    const val assertjCore = "3.12.0"
    const val mockitoKotlin = "2.1.0"
    const val mockitoInline = "2.24.5"
}

// Libraries used in modules
object Libraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"

    const val coroutinescore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesandroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val kotlincoroutinesretrofit = "ru.gildor.coroutines:kotlin-coroutines-retrofit:1.1.0"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitgson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val exoPlayer = "com.google.android.exoplayer:exoplayer:${Versions.exoPlayer2Version}"
    const val butterknif = "com.jakewharton:butterknife:${Versions.butterKnifVersion}"
    const val butterknifCompiler = "com.jakewharton:butterknife-compiler:${Versions.butterKnifVersion}"
    const val flexBox = "com.google.android:flexbox:1.1.0"

    //paging
    const val paging2 = "androidx.paging:paging-runtime:${Versions.pagingVersion}"
    const val paging = "androidx.paging:paging-runtime:${Versions.pagingVersion}"

    const val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    const val rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"


    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    // Room
    const val roomDB = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    const val paperdb = "io.paperdb:paperdb:${Versions.paperdb}"
    const val rxpaper = "com.github.pakoito:RxPaper2:${Versions.rxpaper}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"

    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    const val leakCanaryAndroid = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    const val leakCanaryAndroidNoOp =
        "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanary}"
    const val leakCanaryAndroidSupportFragment =
        "com.squareup.leakcanary:leakcanary-support-fragment:${Versions.leakCanary}"

    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"
    const val databinding = "androidx.databinding:databinding-compiler:${Versions.databindingCompiler}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val shimmer = "io.supercharge:shimmerlayout:${Versions.shimmerVersion}"
    const val swipeReresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.shimmerVersion}"

}

object SupportLibraries {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val design = "com.google.android.material:material:${Versions.design}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val constrainLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
}

object GoogleLibraries {
    const val auth = "com.google.android.gms:play-services-auth:${Versions.googleAuth}"
    const val playCore = "com.google.android.play:core:${Versions.playCore}"
}

object FirebaseLibraries {
    const val auth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    const val core = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
}

object FacebookLibraries {
    const val sdk = "com.facebook.android:facebook-android-sdk:${Versions.facebookSdk}"
}

object TestLibraries {
    const val junit = "junit:junit:${Versions.junit}"
    const val assertjCore = "org.assertj:assertj-core:${Versions.assertjCore}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    const val lifecycleTesting = "androidx.arch.core:core-testing:${Versions.lifecycle}"
}
