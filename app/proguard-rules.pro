-keepattributes SourceFile,LineNumberTable

-keepattributes Signature

-keepattributes Exceptions

-keepattributes *Annotation*

# General Options
-verbose

# Reduce the size of the output some more.

-repackageclasses ''
-allowaccessmodification

# Optimize out Log
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int d(...);
}

-keepclassmembers,allowoptimization enum * {
    <init>(...);
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * implements javax.net.ssl.X509TrustManager {
  public java.util.List checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String, java.lang.String);
}

# Looks like the optimizer is optimizing out the empty constructor which is used by reflection in Keyframes parsing.
-keepclassmembers class androidx.constraintlayout.motion.widget.KeyAttributes {
  public <init>(...);
}

-keepclassmembers class androidx.constraintlayout.motion.widget.KeyPosition {
  public <init>(...);
}

-keepclassmembers class androidx.constraintlayout.motion.widget.KeyCycle {
  public <init>(...);
}

-keepclassmembers class androidx.constraintlayout.motion.widget.KeyTimeCycle {
  public <init>(...);
}

-keepclassmembers class androidx.constraintlayout.motion.widget.KeyTrigger {
  public <init>(...);
}
