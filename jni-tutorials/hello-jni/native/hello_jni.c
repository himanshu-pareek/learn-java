#include <stdio.h>
#include <jni.h>
#include "HelloJNI.h"

JNIEXPORT jint JNICALL Java_HelloJNI_add(
    JNIEnv *env,
    jobject thisObj,
    jint a,
    jint b)
{
    printf("Adding %d and %d...\n", a, b);
    return a + b;
}
