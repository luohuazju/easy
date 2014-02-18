//
//  EATimeMarker.h
//  EasyUIApp
//
//  Created by Carl on 2/18/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EADuration.h"

@interface EATimeMarker : NSObject{

}

- (id)initClearedWithLifetime: (EADuration *)lifetime;

- (id)initUpdatedWithLifetime: (EADuration *)lifetime;

- (id)initWithLifetime: (EADuration *)lifetime setPoint: (NSDate *)setPoint;

- (void)clear;

- (void)update;

- (void)ping;

- (BOOL)expired;

- (BOOL)elapsed;

- (NSString *)description;

@end
