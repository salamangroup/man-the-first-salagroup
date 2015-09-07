
#
#Android Gradle

apply plugin: 'com.android.application'

android {

    compileSdkVersion 22
    buildToolsVersion "23.0.0"
    
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:22.2.1'
}


