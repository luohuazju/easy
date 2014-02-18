//
//  EADuration.h
//  EasyUIApp
//
//  Created by Carl on 2/18/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface EADuration : NSObject{
    @private
    int iSeconds;
}

- (id)initWithSeconds:(int)seconds;

- (id)initWithMinutes:(int)minutes;

- (id)initWithHours:(int)hours;

- (id)initWithDays:(int)days;

- (id)initWithWeeks:(int)weeks;

- (int)seconds;

- (int)minutes;

- (int)hours;

- (int)days;

- (int)weeks;

- (NSString *)description;

@end
