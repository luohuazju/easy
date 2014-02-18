//
//  EAFirstViewController.h
//  EasyUIApp
//
//  Created by Carl on 2/13/14.
//  Copyright (c) 2014 Carl. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import <CoreBluetooth/CoreBluetooth.h>

@interface EABeaconsViewController : UIViewController <CLLocationManagerDelegate, CBPeripheralManagerDelegate, UITableViewDataSource, UITableViewDelegate>

@property (nonatomic,weak) IBOutlet UISwitch *advertisingSwitch;
@property (nonatomic,weak) IBOutlet UISwitch *monitorSwitch;
@property (nonatomic,weak) IBOutlet UISwitch *rangingSwitch;
@property (nonatomic,weak) IBOutlet UITableView *beaconTableView;

@end
