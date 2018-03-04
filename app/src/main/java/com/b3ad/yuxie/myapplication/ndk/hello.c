#include <jni.h>
#include <stdio.h>

jclass    gJniClass;
jmethodID gJinMethod;

JNIEXPORT jstring JNICALL
Java_com_wzhnsc_test_MainActivity_print(JNIEnv* env,
										jclass  cls,
										jstring param)
{
     const char* tpParam;

     gJniClass  = cls;
     gJinMethod = (*env)->GetStaticMethodID(env, gJniClass, "myCallbackFunc", "(Ljava/lang/String;)V");

     if (0 == gJinMethod || NULL == gJinMethod)
     {
         return (*env)->NewStringUTF(env, "-2");
     }

     tpParam =(*env)->GetStringUTFChars(env, param, 0);

     (*env)->CallStaticVoidMethod(env, gJniClass, gJinMethod, (*env)->NewStringUTF(env, tpParam));

     return param;
}
