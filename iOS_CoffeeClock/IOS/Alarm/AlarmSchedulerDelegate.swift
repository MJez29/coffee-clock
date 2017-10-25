//
//  AlarmSchedulerDelegate.swift
//  CoffeeAlarm
//
//  Created by E on 2017/10/25.
//  Copyright © 2017 CoffeeAlarm. All rights reserved.
//

import Foundation

protocol AlarmSchedulerDelegate {
    func setNotificationWithDate(_ date: Date, onWeekdaysForNotify:[Int], snoozeEnabled: Bool, onSnooze:Bool, soundName: String, index: Int)
    //helper
    func setNotificationForSnooze(snoozeMinute: Int, soundName: String, index: Int)
    func setupNotificationSettings()
    func reSchedule()
    func checkNotification()
}

