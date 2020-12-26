/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './src/App';
import {name as appName} from './app.json';
import TestJavaToJavascript from './TestJavaToJavascript';

const BatchedBridge  = require("react-native/Libraries/BatchedBridge/BatchedBridge");

// const TestJavaToJavascript = {
//     runApplication (key) {
//         console.log("TestJavaToJavascript" + key);
//     },
// }

// BatchedBridge.registerCallableModule('TestJavaToJavascript', TestJavaToJavascript);


AppRegistry.registerComponent(appName, () => App);
