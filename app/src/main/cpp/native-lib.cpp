//
// Created by Muhammad Adi Febri Setiawan on 2019-07-10.
//

#include <jni.h>
#include <string>
extern "C" JNIEXPORT jstring
JNICALL
Java_com_docotel_muhadif_first_ui_main_MainActivity_stringFromJNI(JNIEnv *env, jobject object) {
    std::string hello = "Hello from Jni";

return env->NewStringUTF(hello.c_str());
}
