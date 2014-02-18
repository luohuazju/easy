//
//  EAConfiguration.h
//  EasyUIApp
//
//  Created by Carl on 2/18/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface EAConfiguration : NSObject

@property(nonatomic, readonly) NSString *server;
@property(nonatomic, readonly) NSString *brand;
@property(nonatomic, readonly) NSString *localpointAppID;
@property(nonatomic, readonly) NSString *appID;
@property(nonatomic, readonly) int searchRadius;

- (NSString *) localpointServerRootURL;
- (NSString *) localpointEventServiceURL;

+ (EAConfiguration *)instance;

@end
