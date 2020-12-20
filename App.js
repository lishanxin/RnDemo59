/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Image, StyleSheet, Text, View, TextInput, ScrollView, Button} from 'react-native';
const data = 'https://p3.ssl.qhimgs1.com/sdr/_240_/t017a90c496eb0c4593.jpg';
export default class App extends Component{
	render() {
		return (
			<ScrollView >
				<View style={styles.container}>
					<Text style={styles.welcome}>Hello, world!</Text>
					<Image style={{height:100, width:100}} source={{uri:data}} />
					<TextInput style={{
						width:'100%', backgroundColor:'grey'}} />
				</View>
				<View style={styles.container} >
					<Text style={styles.welcome}>Text2</Text>
					<Image style={{height:100, width:100}} source={{uri:data}} />
				</View>
				<View style={styles.container}  >
					<Text style={styles.welcome}>Text3</Text>
					<Image style={{height:100, width:100}} source={{uri:data}} />
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
