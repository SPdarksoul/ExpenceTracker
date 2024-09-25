# Preserve line number information for debugging stack traces
# Uncomment to use:
# -keepattributes SourceFile,LineNumberTable

# Hide the original source file name if line numbers are kept
# Uncomment to use:
# -renamesourcefileattribute SourceFile

-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }