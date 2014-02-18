//
//  EADuration.m
//  EasyUIApp
//
//  Created by Carl on 2/18/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import "EADuration.h"

static const int SECONDS_PER_MINUTE = 60;
static const int SECONDS_PER_HOUR = 60 * SECONDS_PER_MINUTE;
static const int SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;
static const int SECONDS_PER_WEEK = 7 * SECONDS_PER_DAY;

@implementation EADuration

- (id)initWithSeconds: (int)seconds
{
    if (seconds < 0)
    {
        [NSException raise: @"illegal value for argument seconds"
                    format: @"seconds must be non-negative, but was %d", seconds];
    }
    if (self = [super init])
    {
        iSeconds = seconds;
    }
    return self;
}

- (id)initWithMinutes: (int)minutes
{
    if (minutes < 0)
    {
        [NSException raise: @"illegal value for argument minutes"
                    format: @"minutes must be non-negative, but was %d", minutes];
    }
    if (self = [super init])
    {
        iSeconds = minutes * SECONDS_PER_MINUTE;
    }
    return self;
}

- (id)initWithHours: (int)hours
{
    if (hours < 0)
    {
        [NSException raise: @"illegal value for argument hours"
                    format: @"hours must be non-negative, but was %d", hours];
    }
    if (self = [super init])
    {
        iSeconds = hours * SECONDS_PER_HOUR;
    }
    return self;
}

- (id)initWithDays: (int)days
{
    if (days < 0)
    {
        [NSException raise: @"illegal value for argument days"
                    format: @"days must be non-negative, but was %d", days];
    }
    if (self = [super init])
    {
        iSeconds = days * SECONDS_PER_DAY;
    }
    return self;
}

- (id)initWithWeeks: (int)weeks
{
    if (weeks < 0)
    {
        [NSException raise: @"illegal value for argument weeks"
                    format: @"weeks must be non-negative, but was %d", weeks];
    }
    if (self = [super init])
    {
        iSeconds = weeks * SECONDS_PER_WEEK;
    }
    return self;
}

- (id)init
{
    if (self = [super init])
    {
        iSeconds = 0;
    }
    return self;
}

- (int)seconds
{
    return iSeconds;
}

- (int)minutes
{
    return iSeconds / SECONDS_PER_MINUTE;
}

- (int)hours
{
    return iSeconds / SECONDS_PER_HOUR;
}

- (int)days
{
    return iSeconds / SECONDS_PER_DAY;
}

- (int)weeks
{
    return iSeconds / SECONDS_PER_WEEK;
}

- (NSString *)description
{
    // setup
    NSMutableString *str = [[NSMutableString alloc] init];
    int remainingSeconds = iSeconds;
    
    // weeks
    int weeks = (remainingSeconds / SECONDS_PER_WEEK);
    if (weeks > 0)
    {
        [str appendFormat: @"%dw ", weeks];
        remainingSeconds-= (weeks * SECONDS_PER_WEEK);
    }
    
    // days
    int days = (remainingSeconds / SECONDS_PER_DAY);
    if (days > 0)
    {
        [str appendFormat: @"%dd ", days];
        remainingSeconds-= (days * SECONDS_PER_DAY);
    }
    
    // hours
    int hours = (remainingSeconds / SECONDS_PER_HOUR);
    if (hours > 0)
    {
        [str appendFormat: @"%dh ", hours];
        remainingSeconds-= (hours * SECONDS_PER_HOUR);
    }
    
    // minutes
    int minutes = (remainingSeconds / SECONDS_PER_MINUTE);
    if (minutes > 0)
    {
        [str appendFormat: @"%dm ", minutes];
        remainingSeconds-= (minutes * SECONDS_PER_MINUTE);
    }
    
    // seconds
    if (remainingSeconds > 0)
    {
        [str appendFormat: @"%ds ", remainingSeconds];
    }
    
    // remove trailing space
    if ([str length] > 0)
    {
        [str deleteCharactersInRange: NSMakeRange([str length]-1, 1)];
    }
    
    return str;
}

@end
