//
//  EAFirstViewController.m
//  EasyUIApp
//
//  Created by Carl on 2/13/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import "EABeaconsViewController.h"
#import "EALoggingManager.h"

//static NSString * const eaUUID = @"e37e4eff-a544-4810-aaf5-27968b579019";
//static NSString * const eaUUID = @"B9407F30-F5F8-466E-AFF9-25556B57FE6D";

static NSString * const eaUUID = @"B9407F30-F5F8-466E-AFF9-25556B57FE6D";

//static NSString * const eaIdentifier = @"kontakt";
//static NSString * const eaIdentifier1 = @"kontakt1";
//static NSString * const eaIdentifier2 = @"kontakt2";

//static NSString * const eaIdentifier = @"estimote";
//static NSString * const eaIdentifier1 = @"estimote1";
//static NSString * const eaIdentifier2 = @"estimote2";

static NSString * const eaIdentifier = @"estimote";
static NSString * const eaIdentifier1 = @"estimote1";
static NSString * const eaIdentifier2 = @"estimote2";

static NSString * const eaCellIdentifier = @"key";

@interface EABeaconsViewController ()

@property (nonatomic, strong) CLLocationManager *locationManager;

@property (nonatomic, strong) CLBeaconRegion *beaconRegions;
@property (nonatomic, strong) CLBeaconRegion *beaconRegion1;
@property (nonatomic, strong) CLBeaconRegion *beaconRegion2;

@property (nonatomic, strong) CBPeripheralManager *peripheralManager;

@property (nonatomic, strong) NSArray *detectedBeacons;

@property (nonatomic, strong) NSString *inside;
@property (nonatomic, strong) NSString *outside;


@end

@implementation EABeaconsViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self.advertisingSwitch addTarget:self
                               action:@selector(changeAdvertisingState:)
                     forControlEvents:UIControlEventValueChanged];
    [self.monitorSwitch addTarget:self
                           action:@selector(changeMonitoringState:)
                 forControlEvents:UIControlEventValueChanged];
}

//create Beacon
- (void)createBeaconRegion
{
    if (self.beaconRegion1 && self.beaconRegion2){
        return;
    }
    
    NSUUID *proximityUUID = [[NSUUID alloc] initWithUUIDString:eaUUID];
    
    self.beaconRegions = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID identifier:eaIdentifier];

    self.beaconRegion1 = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID major:40564 minor:38384 identifier:eaIdentifier1];
    self.beaconRegion2 = [[CLBeaconRegion alloc] initWithProximityUUID:proximityUUID major:10465 minor:22872 identifier:eaIdentifier2];
    
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
    
    //ranging
    [self.locationManager startRangingBeaconsInRegion:self.beaconRegions];
    
    //monitoring
    [self.locationManager startMonitoringForRegion:self.beaconRegion1];
    [self.locationManager startMonitoringForRegion:self.beaconRegion2];
    
    NSLog(@"Monitoring turned on for region: %@ ", self.beaconRegions);
}



//monitoring beacon
- (void)locationManager:(CLLocationManager *)manager
        didRangeBeacons:(NSArray *)beacons
               inRegion:(CLBeaconRegion *)region {
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
        //enter
        if( self.inside != nil && ![self.inside isEqualToString:region.identifier]){
            //not empty
            //fire exit old beacon if is not equal to current beacon
            UILocalNotification *notification_exit = [[UILocalNotification alloc] init];
            notification_exit.alertBody = [NSString stringWithFormat:@"XX outside Region %@", self.inside];
            [[UIApplication sharedApplication] presentLocalNotificationNow:notification_exit];
        }
        
        self.inside = region.identifier;
        
        //fire enter beacon
        UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
        notification_entry.alertBody = [NSString stringWithFormat:@"XXX inside Region %@", region.identifier];
        [[UIApplication sharedApplication] presentLocalNotificationNow:notification_entry];
    }
    else if(state == CLRegionStateOutside) {
        //exit
        if([self.inside isEqualToString: region.identifier ]){
            //set the current inside beacon to nil
            self.inside = nil;
            
            //equal
            UILocalNotification *notification_exit = [[UILocalNotification alloc] init];
            notification_exit.alertBody = [NSString stringWithFormat:@"XXXX outside Region %@", region.identifier];
            [[UIApplication sharedApplication] presentLocalNotificationNow:notification_exit];
        }else{
            //not equal
            //Do nothing
            UILocalNotification *notification_entry = [[UILocalNotification alloc] init];
            notification_entry.alertBody = [NSString stringWithFormat:@"Do nothing %@", region.identifier];
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

- (void)stopMonitoringForBeacons
{
    if (self.locationManager.rangedRegions.count == 0) {
        NSLog(@"Didn't turn off monitoring: Monitoring already off.");
        return;
    }
    
    [self.locationManager stopRangingBeaconsInRegion:self.beaconRegions];
    
    [self.locationManager stopMonitoringForRegion:self.beaconRegion1];
    [self.locationManager stopMonitoringForRegion:self.beaconRegion2];
    
    self.detectedBeacons = nil;
    [self.beaconTableView reloadData];
    
    NSLog(@"Turned off monitoring.");
}

- (void)locationManager:(CLLocationManager *)manager didChangeAuthorizationStatus:(CLAuthorizationStatus)status
{
    if (![CLLocationManager locationServicesEnabled]) {
        NSLog(@"Couldn't turn on monitoring: Location services are not enabled.");
        self.monitorSwitch.on = NO;
        return;
    }
    
    if ([CLLocationManager authorizationStatus] != kCLAuthorizationStatusAuthorized) {
        NSLog(@"Couldn't turn on monitoring: Location services not authorised.");
        self.monitorSwitch.on = NO;
        return;
    }
    
    self.monitorSwitch.on = YES;
}

- (void)changeMonitoringState:sender
{
    UISwitch *theSwitch = (UISwitch *)sender;
    if (theSwitch.on) {
        [self startMonitoringForBeacons];
    } else {
        [self stopMonitoringForBeacons];
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
