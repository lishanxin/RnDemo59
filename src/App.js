/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Image, StyleSheet, Text, View, TextInput, ScrollView, Button} from 'react-native';
import AlertModule from './custom_native/AlertModule';
const data = 'https://p3.ssl.qhimgs1.com/sdr/_240_/t017a90c496eb0c4593.jpg';
export default class App extends Component{
	handleHelloWorldPress = () => {
		this.alertCallback();
	}

	alertPromise = () => {
		AlertModule.alertPromise('wo shi lishanxin').then((msg) => {console.log(msg);}).catch((error) => console.log(error));
	}

	alertCallback = () => {
		AlertModule.alertCallback('wo shi lishanxin', (msg) => {console.log(msg);});
	}

	render() {
		return (
			<ScrollView >
				<View style={styles.container}>
					<Text style={styles.welcome} onPress={this.handleHelloWorldPress}>Hello, world!</Text>
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