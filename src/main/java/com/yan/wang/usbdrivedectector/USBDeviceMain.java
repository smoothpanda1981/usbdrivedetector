package com.yan.wang.usbdrivedectector;

import com.yan.wang.usbdrivedectector.events.IUSBDriveListener;
import com.yan.wang.usbdrivedectector.events.USBStorageEvent;

public class USBDeviceMain implements IUSBDriveListener {
	
	public static void main(String[] args) {
		
		USBDeviceDetectorManager driveDetector = new USBDeviceDetectorManager();

		for (USBStorageDevice rmDevice : driveDetector.getRemovableDevices()) {

			System.out.println(rmDevice);
		}
        
		
		
        driveDetector.addDriveListener(new USBDeviceMain());
	}

	@Override
	public void usbDriveEvent(USBStorageEvent event) {
		System.out.println(event);
	}
}
