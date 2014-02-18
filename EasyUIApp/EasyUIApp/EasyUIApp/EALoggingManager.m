//
//  EALoggingManager.m
//  EasyUIApp
//
//  Created by Carl on 2/14/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import "EALoggingManager.h"
#import "EATimeMarker.h"
#import "EADuration.h"
#import "EAConfiguration.h"

static NSFileHandle * logFile;

@implementation EALoggingManager {
    @private
    EATimeMarker *logTimeMarker;
}

@synthesize internalLogger;

+ (EALoggingManager *) instance {
    static EALoggingManager *INSTANCE;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        INSTANCE = [[super alloc] init];
    });
    return INSTANCE;
}

+ (void) logInternal:(NSString *)format, ... {
    va_list args;
    va_start(args, format);
    NSString *message = [[NSString alloc] initWithFormat:format arguments:args];
    va_end(args);
    [[EALoggingManager instance].internalLogger log:message];
    message = [NSString stringWithFormat:@"INT :: %@", message];
    [[EALoggingManager instance] writeToFile:message];
}

/** Relies on the LPLoggingDelegate registered with LPLocalpointService
 */
+ (void) logExternal:(NSString *)format, ... {
    va_list args;
    va_start(args, format);
    NSString *message = [[NSString alloc] initWithFormat:format arguments:args];
    va_end(args);
    //[[LPLocalpointService instance].logDelegate log:message];
    // If internal logger not being used then don't write to log file
    //if ([EALoggingManager instance].internalLogger == Nil)
    //    return;
    message = [NSString stringWithFormat:@"EXT :: %@", message];
    [[EALoggingManager instance] writeToFile:message];
}

+ (NSString *) logFilePath {
    return [[EALoggingManager instance] logFilePath];
}

- (id) init {
    self = [super init];
    if (!self) return Nil;
    
    [self setupLogFile];
    EADuration *dur = [[EADuration alloc] initWithDays:1];
    logTimeMarker = [[EATimeMarker alloc] initWithLifetime:dur setPoint:[self logFileCreationDate]];
    
    return self;
}

/** Creates the log file if it doesn't already exist and sets the pointer to the end of the file
 */
- (void) setupLogFile {
    NSString *filePath = [self logFilePath];
    NSFileManager *fileManager = [NSFileManager defaultManager];
    if (![fileManager fileExistsAtPath:filePath])
        [fileManager createFileAtPath:filePath
                             contents:nil
                           attributes:nil];
    logFile = [NSFileHandle fileHandleForWritingAtPath:filePath];
    [logFile seekToEndOfFile];
}

/** Gets the file path as a string to be able to interact with the file for other purposes
 */
- (NSString *) logFilePath {
    EAConfiguration *config = [EAConfiguration instance];
    NSString *logFileName = [NSString stringWithFormat:@"localpoint_%@.log", [config brand]];
    
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    return [documentsDirectory stringByAppendingPathComponent:logFileName];
}

/** Gets the log file size in bytes
 */
- (int) logFileSize {
    NSError *attributesError = nil;
    NSDictionary *fileAttributes = [[NSFileManager defaultManager] attributesOfItemAtPath:[self logFilePath] error:&attributesError];
    
    return [fileAttributes fileSize];
}

/** Is the file larger than 1MB?
 */
- (BOOL) isLogFileTooBig {
    int threshold = 1024 * 1000;    // 1MB?
    return [self logFileSize] >= threshold;
}

/** Is the file too old as determined by the time marker
 */
- (BOOL) isLogFileTooOld {
    return [logTimeMarker elapsed];
}

/** Gets the log file creation date
 */
- (NSDate *) logFileCreationDate {
    NSError *attributesError = nil;
    NSDictionary *fileAttributes = [[NSFileManager defaultManager] attributesOfItemAtPath:[self logFilePath] error:&attributesError];
    
    return [fileAttributes fileCreationDate];
}

/** Prepends the current timestamp and logs the message to a log file
 */
- (void) writeToFile:(NSString *)message {
    //    if ([self isLogFileTooBig] || [self isLogFileTooOld]) {
    // Why worry about how old the file is? Just worry about size.
    @synchronized(self) {
        if ([self isLogFileTooBig]) {
            [self resetLogFile];
        }
        NSDateFormatter *frmt = [[NSDateFormatter alloc] init];
        [frmt setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss.SSS"];
        NSString *datePrepend = [frmt stringFromDate:[NSDate date]];
        message = [NSString stringWithFormat:@"%@  %@", datePrepend, message];
        [logFile writeData:[[message stringByAppendingString:@"\n"] dataUsingEncoding:NSUTF8StringEncoding]];
        [logFile synchronizeFile];
    }
}

/** Resets the log file by removing the current backup, renaming the current file to backup and creating a new log file
 */
- (void) resetLogFile {
    // Get paths to deal with
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSString *oldPath = [self logFilePath];
    NSString *newPath = [oldPath stringByReplacingOccurrencesOfString:@".log" withString:@"1.log"];
    // Close writes to file
    [logFile closeFile];
    // Remove old file
    [[NSFileManager defaultManager] removeItemAtPath:newPath error:Nil];
    // Rename current file
    [fileManager moveItemAtPath:oldPath toPath:newPath error:Nil];
    // Create new file
    [self setupLogFile];
}

@end
