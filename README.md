
# react-native-multi-view-pager

## Getting started

`$ npm install react-native-multi-view-pager --save`

### Mostly automatic installation

`$ react-native link react-native-multi-view-pager`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.react.multiviewpager.RNMultiViewPagerPackage;` to the imports at the top of the file
  - Add `new RNMultiViewPagerPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-multi-view-pager'
  	project(':react-native-multi-view-pager').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-multi-view-pager/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-multi-view-pager')
  	```


## Usage
```javascript
import MultiViewPager from 'react-native-multi-view-pager';

// TODO: What do with the module?
MultiViewPager;
```
  