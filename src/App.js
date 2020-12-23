/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Image, StyleSheet, Text, View, TextInput, ScrollView, Button, DeviceEventEmitter} from 'react-native';
import AlertModule from './custom_native/AlertModule';
import NavigationModule from './custom_native/NavigationModule';
const data = 'https://p3.ssl.qhimgs1.com/sdr/_240_/t017a90c496eb0c4593.jpg';
export default class App extends Component{

	componentDidMount(){
		DeviceEventEmitter.addListener('testLsxEvent', (msg) => {
			console.log(JSON.stringify(msg));
		});
	}

	handleHelloWorldPress = () => {
		this.alertCallback();
	}

	alertPromise = () => {
		AlertModule.alertPromise('wo shi lishanxin').then((msg) => {console.log(msg);}).catch((error) => console.log(error));
	}

	alertCallback = () => {
		AlertModule.alertCallback('wo shi lishanxin', (msg) => {console.log(msg);});
	}

	handleNavigateToMiniProgram = () => {
		NavigationModule.navigateToMiniProgram('http://tesst.duochang.cc/ota-file/QDBLD/1608706244419.zip');
	}


	render() {
		return (
			<ScrollView >
				<View style={styles.container}>
					<Text style={styles.welcome} onPress={this.handleHelloWorldPress}>Hello, world!</Text>
					<Text style={styles.welcome} onPress={this.handleNavigateToMiniProgram}>小程序</Text>
					<Image style={{height:100, width:100}} source={{uri:data}} />
					<TextInput style={{
						width:'100%', backgroundColor:'grey'}} />
				</View>
			</ScrollView>
		);
	}
}

const styles = StyleSheet.create({
	container: {
		// flex: 1,
		// justifyContent: 'center',
		alignItems: 'center',
		backgroundColor: '#F5FCFF',
	},
	welcome: {
		fontSize: 20,
		textAlign: 'center',
		margin: 10,
	},
	instructions: {
		textAlign: 'center',
		color: '#333333',
		marginBottom: 5,
	},
});
