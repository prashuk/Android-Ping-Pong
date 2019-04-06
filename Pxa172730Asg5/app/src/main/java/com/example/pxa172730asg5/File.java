//
//  File.java - java file for class File.
//  Project Name: PXA172730
//
//  Created by Prashuk Ajmera (PXA172730) on 4/1/2019.
//  Last Modified by Prashuk Ajmera (PXA172730) on 4/7/2019.
//  Copyright © 2019 Prashuk Ajmera. All rights reserved.
//

package com.example.pxa172730asg5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class File {

    static String fileName;
    static FileOutputStream fos;

    File(String fileName) {
        this.fileName = fileName;
        fos = null;
    }

    public void openFile() {

        try {
            fos = new FileOutputStream(fileName, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
