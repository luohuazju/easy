//
//  EALoggingManager.h
//  EasyUIApp
//
//  Created by Carl on 2/14/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol EALoggingDelegate <NSObject>
    - (void)log:(NSString*)msg;
@end

@interface EALoggingManager : NSObject {
    @private
    __unsafe_unretained id<EALoggingDelegate> internalLogger;
}

@property (assign) id<EALoggingDelegate> internalLogger;

+ (EALoggingManager *) instance;

+ (void) logInternal:(NSString *)format, ...;

+ (void) logExternal:(NSString *)format, ...;

+ (NSString *) logFilePath;

@end


