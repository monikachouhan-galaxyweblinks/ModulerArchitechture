// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    //ext.kotlin_version = '1.3.61' /*'1.3.61'*/
    repositories {
        google()
        jcenter()
    }
    dependencies {
            classpath("com.android.tools.build:gradle:4.1.0")
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
            classpath("com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.8.6")
            classpath("com.google.gms:google-services:${Versions.googleServices}")
/*
        classpath "com.google.gms:google-services:${Versions.googleServices}"
        classpath "io.fabric.tools:gradle:${Versions.fabric}"
*/
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}