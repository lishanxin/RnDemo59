/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';

AppRegistry.registerComponent(appName, () => App);

/** __attribute__ 报错
 
static BOOL RCTParseUnused(const char **input)
{
  return RCTReadString(input, "__unused") || 
          RCTReadString(input, "__attribute__((__unused__))") ||
         RCTReadString(input, "__attribute__((unused))");
}

 */