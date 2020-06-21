# react-native-wol

<p align="center">React native module for  sending a Wake-On-Lan magic packets. <b>Currently works on Android only!</b><a href="https://reactnative.dev/">React Native.</a></p>

## Getting started

`$ npm install react-native-wol --save`

### Mostly automatic installation

`$ react-native link react-native-wol`

### Manually Link Android

- In `android/setting.gradle`

```gradle
...
include ':react-native-wol'
project(':react-native-wol').projectDir = new File(settingsDir, '../react-native-wol/android')
```

- In `android/app/build.gradle`

```gradle
...
dependencies {
    ...
    compile project(':react-native-wol')
}
```

- In `MainApplication.java`

```java

import android.app.Application;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactPackage;
...
import com.reactlibrary.WolPackage; // <------ Add this!
...

public class MainActivity extends Activity implements ReactApplication {
...
    @Override
    protected List<ReactPackage> getPackages() {
      ...
      packages.add(new WolPackage());
      ...
    }
}
```

## Usage
```javascript
import Wol from 'react-native-wol';

Wol.send("192.168.0.255", "20:17:42:67:DD:18", (res, msg) =>  (res, msg) => {
    console.log(res, msg)
})
```
