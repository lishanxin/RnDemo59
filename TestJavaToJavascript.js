const BatchedBridge  = require("react-native/Libraries/BatchedBridge/BatchedBridge");

const TestJavaToJavascript = {
    testMethod (key) {
        alert("TestJavaToJavascript" + key);
    },
}

BatchedBridge.registerCallableModule('TestJavaToJavascript', TestJavaToJavascript);


module.exports = TestJavaToJavascript;