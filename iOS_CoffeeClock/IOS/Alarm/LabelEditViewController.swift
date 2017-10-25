//
//  labelEditViewController.swift
//  CoffeeAlarm
//
//  Created by E on 2017/10/25.
//  Copyright © 2017 CoffeeAlarm. All rights reserved.
//

import UIKit

class LabelEditViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    @IBOutlet weak var pickerView: UIPickerView!
    
    @IBOutlet weak var labelTextField: UITextField!
    var label: String!
    
    var coffee = ["None", "Small", "Medium", "Large"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
//        labelTextField.becomeFirstResponder()
        // Do any additional setup after loading the view.
//        self.labelTextField.delegate = self
        self.pickerView.delegate = self;
        self.pickerView.dataSource = self;
        
//        labelTextField.text = label
        
        //defined in UITextInputTraits protocol
//        labelTextField.returnKeyType = UIReturnKeyType.done
//        labelTextField.enablesReturnKeyAutomatically = true
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
//    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
//        label = textField.text!
//        performSegue(withIdentifier: Id.labelUnwindIdentifier, sender: self)
//        //This method can be used when no state passing is needed
//        //navigationController?.popViewController(animated: true)
//        return false
//    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1;
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return coffee.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return coffee[row];
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        label = coffee[row];
        performSegue(withIdentifier: Id.labelUnwindIdentifier, sender: self)
        //This method can be used when no state passing is needed
        //navigationController?.popViewController(animated: true)
    }

}
