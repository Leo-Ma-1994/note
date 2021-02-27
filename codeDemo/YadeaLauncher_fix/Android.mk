LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main/java)
LOCAL_RESOURCE_DIR := $(addprefix $(LOCAL_PATH)/, app/src/main/res)
LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml

LOCAL_PACKAGE_NAME := YadeaLauncher
LOCAL_PRIVATE_PLATFORM_APIS := true

LOCAL_CERTIFICATE := platform

LOCAL_MODULE_TAGS := optional
LOCAL_PRIVILEGED_MODULE := true
LOCAL_OVERRIDES_PACKAGES := Launcher2 Launcher3 Launcher3QuickStep MtkLauncher3 MtkLauncher3QuickStep

LOCAL_DEX_PREOPT := false

LOCAL_STATIC_ANDROID_LIBRARIES += \
    android-support-car \
    androidx-constraintlayout_constraintlayout

LOCAL_JAVA_LIBRARIES += android.car

LOCAL_STATIC_JAVA_LIBRARIES += \
    android.support.car \
    android.hardware.automotive.vehicle-V2.0-java \
    vehicle-hal-support-lib \
    androidx-constraintlayout_constraintlayout-solver \
    androidx.annotation_annotation \
    androidx.core_core \
    androidx.customview_customview

LOCAL_USE_AAPT2 := true

# BUILD_PACKAGE 是一个预定义的宏，里面包含编译一个APK的脚本。
include $(BUILD_PACKAGE)
