//
//  EATimeMarker.m
//  EasyUIApp
//
//  Created by Carl on 2/18/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import "EATimeMarker.h"

@implementation EATimeMarker {
    @private
    EADuration *iLifetime;
    NSDate     *iSetPoint;
}

- (id)initWithLifetime: (EADuration *)lifetime
              setPoint: (NSDate *)setPoint
{
    if (!lifetime)
    {
        [NSException raise: @"illegal value for argument lifetime"
                    format: @"lifetime must be nil"];
    }
    if (self = [super init])
    {
        iLifetime = lifetime;
        iSetPoint = setPoint;
    }
    return self;
    
}

- (id)initClearedWithLifetime: (EADuration *)lifetime
{
    if (self = [self initWithLifetime: lifetime
                             setPoint: nil])
    {
        [self clear];
    }
    return self;
}


- (id)initUpdatedWithLifetime: (EADuration *)lifetime
{
    if (self = [self initWithLifetime: lifetime
                             setPoint: nil])
    {
        [self update];
    }
    return self;
}

- (void)clear
{
    iSetPoint = nil;
}

- (void)update
{
    iSetPoint = [NSDate date];
}

- (void)ping
{
    if (!iSetPoint)
    {
        iSetPoint = [NSDate date];
    }
}

- (BOOL)expired
{
    if (!iSetPoint)
    {
        return YES;
    }
    
    NSDate *now = [NSDate date];
    NSDate *end = [iSetPoint dateByAddingTimeInterval: [iLifetime seconds]];
    return ([end compare: now] == NSOrderedAscending);
}

- (BOOL)elapsed
{
    if (!iSetPoint)
    {
        return NO;
    }
    
    NSDate *now = [NSDate date];
    NSDate *end = [iSetPoint dateByAddingTimeInterval: [iLifetime seconds]];
    return ([end compare: now] == NSOrderedAscending);
}

- (NSString *)description
{
    NSMutableString *str = [[NSMutableString alloc] init];
    [str appendFormat: @"lifetime=%@; ", iLifetime];
    
    if (iSetPoint)
    {
        NSDate *now = [NSDate date];
        NSDateComponents *components = [[NSCalendar currentCalendar]
                                        components: NSSecondCalendarUnit
                                        fromDate: iSetPoint toDate: now options: 0];
        EADuration *age = [[EADuration alloc] initWithSeconds: [components second]];
        [str appendFormat: @"age=%@", age];
    }
    else
    {
        [str appendString: @"(cleared)"];
    }
    
    return str;
}

@end
