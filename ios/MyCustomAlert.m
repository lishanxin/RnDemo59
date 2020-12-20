//
//  CustomAlertModule.m
//  RNDemo59
//
//  Created by mac on 2020/12/20.
//  Copyright © 2020 Facebook. All rights reserved.
//
#import "AppDelegate.h"
#import "MyCustomAlert.h"
#import <React/RCTConvert.h>
#import <React/RCTEventDispatcher.h>

@implementation MyCustomAlert

RCT_EXPORT_MODULE();


RCT_EXPORT_METHOD(alertPromise:(NSString *)message
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
       dispatch_async(dispatch_get_main_queue(), ^{
         
         UIAlertController *alertController = [UIAlertController alertControllerWithTitle:nil message:message preferredStyle:UIAlertControllerStyleAlert];
         
      
         UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction *action){reject(@"cancel", @"", nil);}];
         UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"好的" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action){resolve(@"ok");}];
         
         [alertController addAction:cancelAction];
         [alertController addAction:okAction];
         
         AppDelegate *delegate = (AppDelegate *)([UIApplication sharedApplication].delegate);
         
         [delegate.window.rootViewController presentViewController:alertController animated:YES completion:nil];
       });
}

RCT_EXPORT_METHOD(alertCallback:(NSString *)message callback:(RCTResponseSenderBlock)callback)
{
   dispatch_async(dispatch_get_main_queue(), ^{
      
      UIAlertController *alertController = [UIAlertController alertControllerWithTitle:nil message:message preferredStyle:UIAlertControllerStyleAlert];
      
   
      UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction *action){callback(@[@"cancel"]);}];
      UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"好的" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action){callback(@[@"ok"]);}];
      
      [alertController addAction:cancelAction];
      [alertController addAction:okAction];
      
      AppDelegate *delegate = (AppDelegate *)([UIApplication sharedApplication].delegate);
      
      [delegate.window.rootViewController presentViewController:alertController animated:YES completion:nil];
    });
}

@end
