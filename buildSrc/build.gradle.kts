import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}
object PluginsVersions {
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

    var settingModules = arrayOf(
        app,
        networking,
        exoplayerLib,
        playerCore,
        model,
        core,
        navigation,
        login,
        home,
        profile,
        cache,
        mpinauthentication,
        fingure,
        googleLogin,
/*
        signup,
*/
        player,
        settings,
        search,
        fbLogin
    )
}
dependencies {
    //  implementation(kotlin("stdlib-jdk8"))
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}