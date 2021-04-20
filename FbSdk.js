import React, { Component } from 'react';
import { View,Text } from 'react-native';
import { LoginButton, AccessToken } from 'react-native-fbsdk';

import { LoginManager } from "react-native-fbsdk";

// ...


export default class Login extends Component {
    loginClick = () => {

        // Attempt a login using the Facebook login dialog asking for default permissions.
        LoginManager.logInWithPermissions(["public_profile"]).then(
            function(result) {
            if (result.isCancelled) {
                console.log("Login cancelled");
            } else {
                console.log(
                "Login success with permissions: " +
                    result.grantedPermissions.toString()
                );
            }
            },
            function(error) {
            console.log("Login fail with error: " + error);
            }
        );
    }

  render() {
          return (
      <View style={{marginTop:200}}>
          <Text onPress={this.loginClick}>Login</Text>
				<LoginButton
          permissions={['email,public_profile']}
          onLoginFinished={
            (error, result) => {
              if (error) {
                console.log("login has error: " + result.error);
              } else if (result.isCancelled) {
                console.log("login is cancelled.");
              } else {
                console.log("login is succeed.");
                AccessToken.getCurrentAccessToken().then(
                  (data) => {
                    console.log(data.accessToken.toString())

                    // 获取用户信息
                    // curl -i -X GET  "https://graph.facebook.com/126407302859177?fields=picture,name,id,email,gender,permissions&access_token=EAAE2y46DTRQBAEdTZCVVcIYKAs1mpXc2QfuuUe2FqrkWKmsQstelAZBZBsdV3NVM5JSdVuhtSQQQwRXzHtIE3dpXK4ZBc6KdBAKcqSTMZADRT68uHLv8CvxjLii7Ktj0EyCkvFxFMeiy0LINBIfqbtIbwYP6MmfEG9h3lnxOa4vVZBtPn1zay2VPtZC9aUlj2VkPViVapAZCer24JWZBsPeuU"
                  }
                
                )
              }
            }
          }
          onLogoutFinished={() => console.log("logout.")}/>
      </View>
    );
  }
};