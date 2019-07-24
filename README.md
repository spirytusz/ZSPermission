# ZSPermission
一个经过简单封装的运行时动态申请权限库，适用于Android 6.0及其以上系统，以供复用

# 用法
## step 1:
在Module中的build.gradle添加:
```
allprojects {
        ...
        maven { url 'https://jitpack.io' }
        ...
    }
}
```

## step 2:
在app中的build.gradle添加:
```
dependencies {
    ...
    implementation 'com.github.zkw012300:ZSPermission:v3.0'
    ...
}
```

## step 3:
在AndroidManifest.xml的manifest标签中，添加需要申请的权限:
```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="your package">
    ...
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.CAMERA"/>
    ...
</manifest>
```

## step 4:
申请权限，加入回调
```
class MainActivity : AppCompatActivity() {
    ...
    fun requestPermission(permissions: Array<out String>) {
        PermissionFragment.getInstance(this).requestPermission(permissions) { isGranted, deniedPermissions, isNeverAsk ->
            when {
                isGranted -> Toast.makeText(MainActivity@ this, "Granted.", Toast.LENGTH_SHORT).show()
                isNeverAsk -> tip()
                else -> deniedPermissions.forEach {
                    Log.e(TAG, "not grant: $it")
                }
            }
        }
    }
    ...
}
```

# Demo
[这里](https://github.com/zkw012300/ZSPermission/blob/master/app/src/main/java/com/zspirytus/mylibrarytest/MainActivity.kt)
