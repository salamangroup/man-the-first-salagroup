# man-the-first-salagroup
#
#Android Gradle

apply plugin: 'com.android.application'

android {

    compileSdkVersion 22
    buildToolsVersion "23.0.0"

    defaultConfig {
        applicationId "com.salagroup.salaman"
        minSdkVersion 15
        targetSdkVersion 22
        versionCode 1
        versionName "1.0"
    }
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:22.2.1'
}


