//
//  EAFirstViewController.m
//  EasyUIApp
//
//  Created by Carl on 2/13/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import "EABeaconsViewController.h"
#import "EALoggingManager.h"

//static NSString * const eaUUID = @"e37e4eff-a544-4810-aaf5-27968b579019"; kontakt
//static NSString * const eaUUID = @"B9407F30-F5F8-466E-AFF9-25556B57FE6D"; estimote
//static NSString * const eaUUID = @"8B856FBB-6421-A429-B54E-E75BFC412576"; peripheral motorola
//static NSString * const eaUUID = @"f7826da6-4fa2-4e98-8024-bc5b71e0893e"; kontakt latest

static NSString * const eaUUID = @"DF892040-A072-48F4-93B9-46DD5DEC3E54";

static const int SHAKE_MAX_COUNT = 5;

static NSString * const eaIdentifier = @"ranging";

static NSString * const eaIdentifier1 = @"monitor1";
static NSString * const eaIdentifier2 = @"monitor2";

static NSString * const eaCellIdentifier = @"key";

@interface EABeaconsViewController ()

@property (nonatomic, strong) CLLocationManager *locationManager;

@property (nonatomic, strong) CLBeaconRegion *beaconRegions;

//@property (nonatomic, strong) CLBeaconRegion *beaconRegionsDifferent;

@property (nonatomic, strong) CLBeaconRegion *beaconRegion1;
@property (nonatomic, strong) CLBeaconRegion *beaconRegion2;

@property (nonatomic, strong) CBPeripheralManager *peripheralManager;

@property (nonatomic, strong) NSArray *detectedBeacons;

@property (nonatomic, strong) NSString *inside;
@property (nonatomic, strong) NSString *outside;

@property (nonatomic, strong) NSString *rangingInside;
@property (nonatomic, strong) NSString *rangingOutside;

@property (nonatomic, strong) NSMutableDictionary *beacons;

@property (nonatomic) int shakeCount;

@end

@implementation EABeaconsViewController

- (void)startOberver {
//    [[NSNotificationCenter defaultCenter] addObserver:self
//            selector:@selector(handleAppLaunched) name:UIApplicationDidFinishLaunchingNotification object:nil];
//    
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleAppResigningActive) name:UIApplicationWillResignActiveNotification object:nil];
//    
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleAppBecomingActive) name:UIApplicationDidBecomeActiveNotification object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleAppEnteringBackground) name:UIApplicationDidEnterBackgroundNotification object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleAppEnteringForeground) name:UIApplicationWillEnterForegroundNotification object:nil];
    
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleAppClosing) name:UIApplicationWillTerminateNotification object:nil];
}

//+ (void)handleAppLaunched{
//    NSLog(@"I am handleAppLaunched.....");
//}

//+ (void)handleAppResigningActive{
//    NSLog(@"I am handleAppResigningActive.....");
//}
//
//+ (void)handleAppBecomingActive{
//    NSLog(@"I am handleAppBecomingActive.....");
//}

+ (void)handleAppEnteringBackground{
    NSLog(@"I am handleAppEnteringBackground.....");
}

+ (void)handleAppEnteringForeground{
    NSLog(@"I am handleAppEnteringForeground.....");
}

+ (void)handleAppClosing{
    NSLog(@"I am handleAppClosing.....");
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self startOberver];
    
    [self.advertisingSwitch addTarget:self
                               action:@selector(changeAdvertisingState:)
                     forControlEvents:UIControlEventValueChanged];
    [self.monitorSwitch addTarget:self
                           action:@selector(changeMonitoringState:)
                 forControlEvents:UIControlEventValueChanged];
    [self.rangingSwitch addTarget:self
                           action:@selector(changeRangingState:)
                 forControlEvents:UIControlEventValueChanged];
}

//create Beacon
- (void)createBeaconRegion
{
    if (self.beaconRegion1 && self.beaconRegion2){
        return;
    }
    
    NSUUID *proximityUUID = [[NSUUID alloc] initWithUUIDString:eaUUID];
    //NSUUID *proximityUUIDDifferent = [[NSUUID alloc] initWithUUIDString:eaUUIDDifferent];
    
    self.beaconRegions = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID identifier:eaIdentifier];
    
    //self.beaconRegionsDifferent = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUIDDifferent identifier:eaIdentifierDifferent];

    //self.beaconRegion1 = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID major:40564 minor:38384 identifier:eaIdentifier1];
    //self.beaconRegion2 = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID major:10465 minor:22872 identifier:eaIdentifier2];
    
    self.beaconRegion1 = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID major:0 minor:0 identifier:eaIdentifier1];
    self.beaconRegion2 = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID major:1 minor:1 identifier:eaIdentifier2];
    
    self.beacons = [[NSMutableDictionary alloc] init];
    [self.beacons setObject:self.beaconRegion1.identifier forKey:[NSString stringWithFormat:@"%@_%@_%@",self.beaconRegion1.proximityUUID.UUIDString,self.beaconRegion1.major,self.beaconRegion1.minor]];
    [self.beacons setObject:self.beaconRegion2.identifier forKey:[NSString stringWithFormat:@"%@_%@_%@",self.beaconRegion2.proximityUUID.UUIDString,self.beaconRegion2.major,self.beaconRegion2.minor]];
    
}

//start monitoring
- (void)turnOnMonitoring
{
    NSLog(@"Turning on monitoring...");
    
    if (![CLLocationManager isRangingAvailable]) {
        NSLog(@"Couldn't turn on monitoring: Monitoring is not available.");
        self.monitorSwitch.on = NO;
        return;
    }
    
    if (self.locationManager.rangedRegions.count > 0) {
        NSLog(@"Didn't turn on monitoring: Monitoring already on.");
        return;
    }
    
    [self createBeaconRegion];
    
    //monitoring
    [self.locationManager startMonitoringForRegion:self.beaconRegion1];
    [self.locationManager startMonitoringForRegion:self.beaconRegion2];
    
    NSLog(@"Monitoring turned on for region: %@ ", self.beaconRegions);
}

//start ranging
- (void)turnOnRanging
{
    NSLog(@"Turning on Ranging...");
    
    if (![CLLocationManager isRangingAvailable]) {
        NSLog(@"Couldn't turn on Ranging: Ranging is not available.");
        self.rangingSwitch.on = NO;
        return;
    }
    
    if (self.locationManager.rangedRegions.count > 0) {
        NSLog(@"Didn't turn on ranging: Ranging already on.");
        return;
    }
    
    [self createBeaconRegion];
    
    //ranging
    [self.locationManager startRangingBeaconsInRegion:self.beaconRegions];
     NSLog(@"Ranging turned on for region: %@ ", self.beaconRegions);
    
    //[self.locationManager startRangingBeaconsInRegion:self.beaconRegionsDifferent];
    
    //NSLog(@"Ranging turned on for region: %@ ", self.beaconRegionsDifferent);
}


//ranging beacon
- (void)locationManager:(CLLocationManager *)manager
        didRangeBeacons:(NSArray *)beacons
               inRegion:(CLBeaconRegion *)region {
    
    if ([beacons count] == 0) {
        //outside event
        if(self.rangingInside != nil){
            self.rangingOutside = self.rangingInside;
            self.rangingInside = nil;
            self.shakeCount = 0;
        }
        
        self.shakeCount++;
        
        if(self.shakeCount == SHAKE_MAX_COUNT){
            //fire an exit event
            if([self.inside isEqualToString: self.rangingOutside ]){
                //set the current inside beacon to nil
                self.inside = nil;
                
                //equal
                UILocalNotification *notification_exit = [[UILocalNotification alloc] init];
                notification_exit.alertBody = [NSString stringWithFormat:@"R outside Region %@", self.rangingOutside];
                [[UIApplication sharedApplication] presentLocalNotificationNow:notification_exit];
            }else{
                //not equal
                //Do nothing
                UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
                notification_entry.alertBody = [NSString stringWithFormat:@"R Do nothing %@", self.rangingOutside];
                [[UIApplication sharedApplication] presentLocalNotificationNow:notification_entry];
            }
        }
    }else{
        //first object
        CLBeacon *nearest = [beacons firstObject];
        
        //CLBeaconRegion *castNearest = (CLBeaconRegion *)nearest;
        //NSLog(@"Final Magic Things: %@ ", castNearest);
        
        NSString *key = [NSString stringWithFormat:@"%@_%@_%@",nearest.proximityUUID.UUIDString,nearest.major,nearest.minor];
        
        NSString *identifier = [self.beacons objectForKey:key];
        //enter event
        if(self.rangingInside == nil){
            //first time
            self.rangingInside = identifier;
            self.shakeCount = 0;
        }
        if(self.rangingInside != nil && [self.rangingInside isEqualToString:identifier]){
            //same identifier
        }else{
            self.rangingInside = identifier;
            self.shakeCount = 0;
        }
        
        self.shakeCount++;
        
        if(self.shakeCount == SHAKE_MAX_COUNT){
            //fire an entry event
            //enter
            if(self.inside != nil){
                if( ![self.inside isEqualToString:self.rangingInside]){
                    //not empty
                    //fire exit old beacon if is not equal to current beacon
                    UILocalNotification *notification_exit = [[UILocalNotification alloc] init];
                    notification_exit.alertBody = [NSString stringWithFormat:@"RR outside Region %@", self.inside];
                    [[UIApplication sharedApplication] presentLocalNotificationNow:notification_exit];
                    
                    //fire enter beacon
                    UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
                    notification_entry.alertBody = [NSString stringWithFormat:@"RRR inside Region %@", self.rangingInside];
                    [[UIApplication sharedApplication] presentLocalNotificationNow:notification_entry];
                }
            }else{
                //fire enter beacon
                UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
                notification_entry.alertBody = [NSString stringWithFormat:@"RRRR inside Region %@", self.rangingInside];
                [[UIApplication sharedApplication] presentLocalNotificationNow:notification_entry];
            }
            self.inside = self.rangingInside;
        }
    }
    
    self.detectedBeacons = beacons;
    [self.beaconTableView reloadData];
}

//enter region
- (void)locationManager:(CLLocationManager *)manager
         didEnterRegion:(CLRegion *)region{
}

//exit region
- (void)locationManager:(CLLocationManager *)manager
          didExitRegion:(CLRegion *)region{
}

/**
 Action     inside   outside      fire event
 enter a    a                     enter a
 enter b    b        a            enter b, exit a
 exit  a    b        a
 enter a    a        b            enter a, exit b
 exit  b    a        b
 
 enter a    a                     enter a
 exit  a             a            exit  a
 
 **/

//determine State
- (void)locationManager:(CLLocationManager *)manager didDetermineState:(CLRegionState)state forRegion:(CLRegion *)region {

    
    [EALoggingManager logInternal:@"Processing download geofences operation."];
    
    if(state == CLRegionStateInside) {
        NSLog(@"what is the state:%@ ", @"entry");
        //enter
        if(self.inside != nil){
            if( ![self.inside isEqualToString:region.identifier]){
                //not empty
                //fire exit old beacon if is not equal to current beacon
                UILocalNotification *notification_exit = [[UILocalNotification alloc] init];
                notification_exit.alertBody = [NSString stringWithFormat:@"M outside Region %@", self.inside];
                [[UIApplication sharedApplication] presentLocalNotificationNow:notification_exit];
                
                //fire enter beacon
                UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
                notification_entry.alertBody = [NSString stringWithFormat:@"MM inside Region %@", region.identifier];
                [[UIApplication sharedApplication] presentLocalNotificationNow:notification_entry];
            }
        }else{
            //fire enter beacon
            UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
            notification_entry.alertBody = [NSString stringWithFormat:@"MMM inside Region %@", region.identifier];
            [[UIApplication sharedApplication] presentLocalNotificationNow:notification_entry];
        }
        self.inside = region.identifier;
    }
    else if(state == CLRegionStateOutside) {
        NSLog(@"what is the state:%@ ", @"exit");
        //exit
        if([self.inside isEqualToString: region.identifier ]){
            //set the current inside beacon to nil
            self.inside = nil;
            
            //equal
            UILocalNotification *notification_exit = [[UILocalNotification alloc] init];
            notification_exit.alertBody = [NSString stringWithFormat:@"MMMM outside Region %@", region.identifier];
            [[UIApplication sharedApplication] presentLocalNotificationNow:notification_exit];
        }else{
            //not equal
            //Do nothing
            UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
            notification_entry.alertBody = [NSString stringWithFormat:@"M Do nothing %@", region.identifier];
            [[UIApplication sharedApplication] presentLocalNotificationNow:notification_entry];
            
        }
    }
    else{
        UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
        notification_entry.alertBody = [NSString stringWithFormat:@"Unknow %@", region.identifier];
        [[UIApplication sharedApplication] presentLocalNotificationNow:notification_entry];
    }
}


- (void)startMonitoringForBeacons
{
    self.locationManager = [[CLLocationManager alloc] init];
    self.locationManager.delegate = self;
    self.locationManager.activityType = CLActivityTypeFitness;
    self.locationManager.distanceFilter = kCLDistanceFilterNone;
    self.locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    
    [self turnOnMonitoring];
}

- (void)startRangingForBeacons
{
    self.locationManager = [[CLLocationManager alloc] init];
    self.locationManager.delegate = self;
    self.locationManager.activityType = CLActivityTypeFitness;
    self.locationManager.distanceFilter = kCLDistanceFilterNone;
    self.locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    
    [self turnOnRanging];
}

- (void)stopMonitoringForBeacons
{
    
    [self.locationManager stopMonitoringForRegion:self.beaconRegion1];
    [self.locationManager stopMonitoringForRegion:self.beaconRegion2];
    
    self.detectedBeacons = nil;
    [self.beaconTableView reloadData];
    
    NSLog(@"Turned off monitoring.");
}
 
- (void)stopRangingForBeacons
{
    if (self.locationManager.rangedRegions.count == 0) {
        NSLog(@"Didn't turn off ranging: Ranging already off.");
        return;
    }
    
    [self.locationManager stopRangingBeaconsInRegion:self.beaconRegions];
    //[self.locationManager stopRangingBeaconsInRegion:self.beaconRegionsDifferent];
    
    self.detectedBeacons = nil;
    [self.beaconTableView reloadData];
    
    NSLog(@"Turned off Ranging.");
}


- (void)changeMonitoringState:sender
{
    UISwitch *theSwitch = (UISwitch *)sender;
    if (theSwitch.on) {
        self.inside = nil;
        self.outside = nil;
        
        self.rangingInside = nil;
        self.rangingOutside = nil;
        
        self.shakeCount = 0;
        
        [self startMonitoringForBeacons];
    } else {
        [self stopMonitoringForBeacons];
    }
}

- (void)changeRangingState:sender
{
    UISwitch *theSwitch = (UISwitch *)sender;
    if (theSwitch.on) {
        self.inside = nil;
        self.outside = nil;
        
        self.rangingInside = nil;
        self.rangingOutside = nil;
        
        self.shakeCount = 0;
        
        [self startRangingForBeacons];
    } else {
        [self stopRangingForBeacons];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

- (void)turnOnAdvertising
{
    if (self.peripheralManager.state != 5) {
        NSLog(@"Peripheral manager is off.");
        self.advertisingSwitch.on = NO;
        return;
    }
    
    time_t t;
    srand((unsigned) time(&t));
    CLBeaconRegion *region = [[CLBeaconRegion alloc] initWithProximityUUID:self.beaconRegions.proximityUUID
                                                                     major:rand()
                                                                     minor:rand()
                                                                identifier:self.beaconRegions.identifier];
    NSDictionary *beaconPeripheralData = [region peripheralDataWithMeasuredPower:nil];
    [self.peripheralManager startAdvertising:beaconPeripheralData];
}

- (void)changeAdvertisingState:sender
{
    UISwitch *theSwitch = (UISwitch *)sender;
    if (theSwitch.on) {
        [self startAdvertisingBeacon];
    } else {
        [self stopAdvertisingBeacon];
    }
}

- (void)startAdvertisingBeacon
{
    NSLog(@"Turning on advertising...");
    
    [self createBeaconRegion];
    
    if (!self.peripheralManager)
        self.peripheralManager = [[CBPeripheralManager alloc] initWithDelegate:self queue:nil options:nil];
    
    [self turnOnAdvertising];
}

- (void)stopAdvertisingBeacon
{
    [self.peripheralManager stopAdvertising];
    
    NSLog(@"Turned off advertising.");
}

- (void)peripheralManagerDidStartAdvertising:(CBPeripheralManager *)peripheralManager error:(NSError *)error
{
    if (error) {
        NSLog(@"Couldn't turn on advertising: %@", error);
        self.advertisingSwitch.on = NO;
        return;
    }
    
    if (peripheralManager.isAdvertising) {
        NSLog(@"Turned on advertising.");
        self.advertisingSwitch.on = YES;
    }
}

- (void)peripheralManagerDidUpdateState:(CBPeripheralManager *)peripheralManager
{
    if (peripheralManager.state != 5) {
        NSLog(@"Peripheral manager is off.");
        self.advertisingSwitch.on = NO;
        return;
    }
    
    NSLog(@"Peripheral manager is on.");
    [self turnOnAdvertising];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    CLBeacon *beacon = self.detectedBeacons[indexPath.row];
    
    UITableViewCell *defaultCell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle
                                                          reuseIdentifier:eaCellIdentifier];
    
    defaultCell.textLabel.text = beacon.proximityUUID.UUIDString;
    
    NSString *proximityString;
    switch (beacon.proximity) {
        case CLProximityNear:
            proximityString = @"Near";
            break;
        case CLProximityImmediate:
            proximityString = @"Immediate";
            break;
        case CLProximityFar:
            proximityString = @"Far";
            break;
        case CLProximityUnknown:
        default:
            proximityString = @"Unknown";
            break;
    }
    defaultCell.detailTextLabel.text = [NSString stringWithFormat:@"%@, %@ • %@ • %f • %li",
                                        beacon.major.stringValue, beacon.minor.stringValue, proximityString, beacon.accuracy, (long)beacon.rssi];
    defaultCell.detailTextLabel.textColor = [UIColor grayColor];
    
    return defaultCell;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.detectedBeacons.count;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    return @"Detected beacons";
}

@end
