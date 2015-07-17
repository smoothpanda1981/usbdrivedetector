package com.yan.wang.usbdrivedectector;

import org.apache.log4j.Logger;

import com.yan.wang.usbdrivedectector.events.IUSBDriveListener;
import com.yan.wang.usbdrivedectector.events.USBStorageEvent;

public class USBDeviceMain implements IUSBDriveListener {
	
	private static final Logger logger = Logger.getLogger(USBDeviceMain.class);
	
	public static void main(String[] args) {
		
		USBDeviceDetectorManager driveDetector = new USBDeviceDetectorManager();

		for (USBStorageDevice rmDevice : driveDetector.getRemovableDevices()) {

			logger.info(rmDevice);
		}
		
        driveDetector.addDriveListener(new USBDeviceMain());
	}

	@Override
	public void usbDriveEvent(USBStorageEvent event) {
		logger.info(event);
	}
}
