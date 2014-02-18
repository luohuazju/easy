//
//  EAConfiguration.m
//  EasyUIApp
//
//  Created by Carl on 2/18/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import "EAConfiguration.h"

static NSString *const SERVER_KEY = @"LPServer";
static NSString *const BRAND_KEY = @"LPBrand";
static NSString *const APPID_KEY = @"LPAppID";
static NSString *const SEARCH_RADIUS_KEY = @"LPSearchRadius";

static const NSInteger DEFAULT_SEARCH_RADIUS = 50000;

@implementation EAConfiguration

@synthesize server;
@synthesize brand;
@synthesize localpointAppID;
@synthesize appID;
@synthesize searchRadius;

+ (EAConfiguration *)instance {
    // use of dispatch_once recommended by apple -- ARC reasons, etc.
    static EAConfiguration *configInstance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        configInstance = [[super alloc] init];
    });
    return configInstance;
}

- (id) init{
    self = [super init];
    
    NSBundle *bundle = [NSBundle mainBundle];
    
    server = [bundle objectForInfoDictionaryKey:SERVER_KEY];
    brand = [bundle objectForInfoDictionaryKey:BRAND_KEY];
    appID = [bundle objectForInfoDictionaryKey:APPID_KEY];
    id searchRadiusObj = [bundle objectForInfoDictionaryKey:SEARCH_RADIUS_KEY];
    searchRadius = (searchRadiusObj == Nil) ? DEFAULT_SEARCH_RADIUS : [searchRadiusObj integerValue];
    
    // TODO React to missing required values appropriately
    
    return self;
}

//Examples of root URLs
//https://cabelas.api.digby.com            //production
//https://cabelas.api.sandbox.digby.com
//https://qabrand.api.stage.digby.com
//https://playground.api.dev.digby.com
- (NSString *) localpointServerRootURL {
    NSString *serverName;
    if ([server isEqualToString:@"api"])
        serverName = @"";
    else
        serverName = [NSString stringWithFormat:@".%@", server];
    //Construct Localpoint server root URL
    return [NSString stringWithFormat:@"https://%@.api%@.digby.com", brand, serverName];
}

//Examples of the event service URLs
//<LP-root-URL>/api/brands/cabelas/events            //production
//<LP-root-URL>/api/brands/cabelas/events
//<LP-root-URL>/api/brands/qabrand/events
//<LP-root-URL>/api/brands/playground/events
- (NSString *) localpointEventServiceURL {
    return [NSString stringWithFormat:@"%@/api/brands/%@/events", [self localpointServerRootURL], brand];
    
    //For local test - keep it here
    //return @"http://playground.api.local.digby.com:8080/api/brands/playground/events";
}

@end
