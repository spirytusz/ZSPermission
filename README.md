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
    implementation 'com.github.zkw012300:ZSPermission:v1.0'
    ...
}
```

## step 3:
在AndroidManifest.xml的manifest标签中，添加需要申请的权限:
```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="your package">
    ...
    <uses-permission android:name="android.permission.CAMERA"/>
    ...
</manifest>
```

## step 4:
在需要申请权限的Activity 或Fragment中实现接口ZSPermission.OnPermissionListener
```
public class MainActivity extends AppCompatActivity implements ZSPermission.OnPermissionListener {
    ...
    @Override
    public void onGranted() {
        Toast.makeText(this, "successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDenied() {
        Toast.makeText(this, "denied by user", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNeverAsk() {
        Toast.makeText(this, "denied by user and never ask", Toast.LENGTH_SHORT).show();
    }
    ...
}
```

## step 5:
申请权限，也可以使用[PermissionGroup]()中的常量来一次申请指定权限组的全部权限:
```
ZSPermission.getInstance()
            .at(this)
            .requestCode(REQUEST_CAMERA)
            .permissions(PermissionGroup.CAMERA_GROUP)
            .listenBy(this)
            .request();
```

## step 6:
覆盖Activity的onRequestPermissionsResult()方法
```
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ZSPermission.getInstance().onRequestPermissionResult(requestCode, permissions, grantResults);
    }
```

# Demo
[这里](https://github.com/zkw012300/ZSPermission/blob/master/app/src/main/java/com/zspirytus/mylibrarytest/MainActivity.java)
