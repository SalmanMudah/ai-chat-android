# AI Chat SDK ProGuard Rules
# Keep this file for production builds to ensure SDK works correctly

# ===============================
# Keep SDK public API
# ===============================

# Keep all public classes, methods, and fields
-keep public class com.sa.aichatlib.** {
    public protected *;
}

# Keep data models for serialization
-keep class com.sa.aichatlib.model.** { *; }

# Keep Room entities and DAOs
-keep class com.sa.aichatlib.dao.** { *; }

# ===============================
# Kotlin Serialization
# ===============================
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.sa.aichatlib.**$$serializer { *; }
-keepclassmembers class com.sa.aichatlib.** {
    *** Companion;
}
-keepclasseswithmembers class com.sa.aichatlib.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# ===============================
# OkHttp
# ===============================
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# ===============================
# Room Database
# ===============================
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# ===============================
# Jetpack Compose
# ===============================
-dontwarn androidx.compose.**
-keep class androidx.compose.** { *; }

# Keep Compose runtime
-keepclassmembers class androidx.compose.runtime.** { *; }

# ===============================
# Coroutines
# ===============================
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# ===============================
# Debugging
# ===============================
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep exceptions for better crash reports
-keepattributes Exceptions

# ===============================
# General Android
# ===============================
-keepattributes Signature
-keepattributes *Annotation*

# ===============================
# Remove Logging (Optional)
# ===============================
# Uncomment to remove all logging in release builds
#-assumenosideeffects class android.util.Log {
#    public static *** d(...);
#    public static *** v(...);
#    public static *** i(...);
#    public static *** w(...);
#    public static *** e(...);
#}

# Uncomment to remove println statements
#-assumenosideeffects class kotlin.io.ConsoleKt {
#    public static *** println(...);
#}
#-assumenosideeffects class java.io.PrintStream {
#    public *** println(...);
#    public *** print(...);
#}