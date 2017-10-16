#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_mert_getty_data_api_GettyClientConfig_getApiKey(JNIEnv *env, jobject instance) {

    return (*env)->  NewStringUTF(env, "7yumftd9wdb6frx27fppwdy2");
}