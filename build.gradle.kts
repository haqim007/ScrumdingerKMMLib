plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.sqldelight).apply(false)
    alias(libs.plugins.mokoresources).apply(false)
}

buildscript {
    dependencies {
        classpath(libs.mokoresourcesGradlePlugin)
    }
}
