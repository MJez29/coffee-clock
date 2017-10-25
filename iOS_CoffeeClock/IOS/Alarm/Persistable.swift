//
//  Persistable.swift
//  CoffeeAlarm
//
//  Created by E on 2017/10/25.
//  Copyright © 2017 CoffeeAlarm. All rights reserved.
//

import Foundation

protocol Persistable{
    var ud: UserDefaults {get}
    var persistKey : String {get}
    func persist()
    func unpersist()
}
