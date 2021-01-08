/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Image, StyleSheet, Text, View, TextInput, ScrollView, Button, DeviceEventEmitter, UIManager, findNodeHandle} from 'react-native';
import AlertModule from './custom_native/AlertModule';
import NavigationModule from './custom_native/NavigationModule';
import MyCustomImageView from './custom_native/MyCutomImageView';
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

	// 接收从Java传过来的事件
	handleOnChange = (event) => {
		console.log("JSLog", JSON.stringify(event.nativeEvent))
	}

	// 向Java发送事件
	hideIcon = () => {
		UIManager.dispatchViewManagerCommand(
			findNodeHandle(this.myCustomImageViewRef),
			UIManager.getViewManagerConfig('MyCustomImageView').Commands.changeIconInvisible,
			null,
		);
	}

	// 向Java发送事件
	showIcon = () => {
		UIManager.dispatchViewManagerCommand(
			findNodeHandle(this.myCustomImageViewRef),
			UIManager.getViewManagerConfig('MyCustomImageView').Commands.changeIconVisible,
			null,
		);
	}
	render() {
		return (
			<ScrollView >
				<View style={styles.container}>
					{/* <Text style={styles.welcome} onPress={this.handleHelloWorldPress}>Hello, world!</Text> */}
					{/* <Text style={styles.welcome} onPress={this.handleNavigateToMiniProgram}>小程序</Text> */}
					<Text style={styles.welcome} onPress={this.hideIcon}>隐藏 右上角图标</Text>
					<Text style={styles.welcome} onPress={this.showIcon}>展示 右上角图标</Text>
					<Image style={{height:100, width:100}} source={{uri:data}} />
					<TextInput style={{
						width:'100%', backgroundColor:'grey'}} />
					<MyCustomImageView ref={ref => this.myCustomImageViewRef = ref} style={{height:200, width:200}} src={data} onChange={this.handleOnChange}/>
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
