//
//  ViewController.swift
//  Http_Post_Test
//
//  Created by E on 2017/10/23.
//  Copyright © 2017 Secretapp. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBAction func postButton(_ sender: Any) {
        let parameters = ["coffeeSize": "Small"]
        
        guard let url = URL(string:"http://192.168.137.209:3000/brew") else {return}
        var request = URLRequest(url: url);
        request.httpMethod = "POST";
        request.addValue("application/json", forHTTPHeaderField: "Content-Type");
        
        guard let httpBody = try? JSONSerialization.data(withJSONObject: parameters, options: []) else {return}
        
        request.httpBody = httpBody;
        
        let session = URLSession.shared;
        session.dataTask(with: request) { (data, response, error) in
            if let response = response {
                print(response);
            }
            
            if let data = data {
                do {
                    let json = try JSONSerialization.jsonObject(with: data, options: [])
                    print(json);
                } catch {
                    print(error);
                }
            }
            
        }.resume()
        
        
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

