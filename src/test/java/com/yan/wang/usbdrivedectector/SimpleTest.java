package com.yan.wang.usbdrivedectector;

import com.yan.wang.usbdrivedectector.USBDeviceDetectorManager;
import com.yan.wang.usbdrivedectector.USBStorageDevice;
import com.yan.wang.usbdrivedectector.events.IUSBDriveListener;
import com.yan.wang.usbdrivedectector.events.USBStorageEvent;

/**
 *
 * @author ywang
 */
public class SimpleTest implements IUSBDriveListener{
    public static void main(String[] args) {
		USBDeviceDetectorManager driveDetector = new USBDeviceDetectorManager();

		for (USBStorageDevice rmDevice : driveDetector.getRemovableDevices()) {

			System.out.println(rmDevice);
		}
        
        SimpleTest sTest = new SimpleTest();
        
        driveDetector.addDriveListener(sTest);
	}
    
    private SimpleTest () {
        
    }

    @Override
    public void usbDriveEvent(USBStorageEvent event) {
        System.out.println(event);
    }
}
