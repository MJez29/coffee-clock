//
//  AlarmApplicationDelegate.swift
//  CoffeeAlarm
//
//  Created by E on 2017/10/25.
//  Copyright © 2017 CoffeeAlarm. All rights reserved.
//
import Foundation

protocol AlarmApplicationDelegate {
    func playSound(_ soundName: String, coffeeSize: String)
}
