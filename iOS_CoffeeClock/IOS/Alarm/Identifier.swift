//
//  Identifier.swift
//  CoffeeAlarm
//
//  Created by E on 2017/10/25.
//  Copyright © 2017 CoffeeAlarm. All rights reserved.
//

import Foundation

struct Id {
//    Used in segue transitions between views
    static let stopIdentifier = "Alarm-ios-swift-stop"
    static let snoozeIdentifier = "Alarm-ios-swift-snooze"
    static let addSegueIdentifier = "addSegue"
    static let editSegueIdentifier = "editSegue"
    static let saveSegueIdentifier = "saveEditSegue"
    static let soundSegueIdentifier = "soundSegue"
    static let labelSegueIdentifier = "labelEditSegue"
    static let weekdaysSegueIdentifier = "weekdaysSegue"
    static let settingIdentifier = "setting"
    static let musicIdentifier = "musicIdentifier"
    static let alarmCellIdentifier = "alarmCell"
    
    static let labelUnwindIdentifier = "labelUnwindSegue"
    static let soundUnwindIdentifier = "soundUnwindSegue"
    static let weekdaysUnwindIdentifier = "weekdaysUnwindSegue"
}
