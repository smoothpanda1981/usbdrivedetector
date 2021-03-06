/*
 * Copyright 2014 ywang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yan.wang.usbdrivedectector.detectors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.yan.wang.usbdrivedectector.USBStorageDevice;
import com.yan.wang.usbdrivedectector.process.CommandLineExecutor;

/**
 *
 * @author ywang
 */
public class WindowsStorageDeviceDetector extends AbstractStorageDeviceDetector {

    private static final Logger logger = Logger
            .getLogger(WindowsStorageDeviceDetector.class);

    /**
     * wmic logicaldisk where drivetype=2 get description,deviceid,volumename
     */
    private static final String windowsDetectUSBCommand = "wmic logicaldisk where drivetype=2 get deviceid";

    private final CommandLineExecutor commandExecutor;

    public WindowsStorageDeviceDetector() {
        commandExecutor = new CommandLineExecutor();
    }

    @Override
    public List<USBStorageDevice> getRemovableDevices() {
        ArrayList<USBStorageDevice> listDevices = new ArrayList<USBStorageDevice>();

        try {
            commandExecutor.executeCommand(windowsDetectUSBCommand);

            String outputLine;
            while ((outputLine = commandExecutor.readOutputLine()) != null) {

                if (!outputLine.isEmpty() && !"DeviceID".equals(outputLine)) {
                    addUSBDevice(listDevices, outputLine + File.separatorChar);
                }
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                commandExecutor.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return listDevices;
    }

    /**
     * Returns the list of the removable devices actually connected to the computer. <br/>
     * This method was effectively tested on:
     * <ul>
     * <li>Windows 7 (English)</li>
     * </ul>
     *
     * @deprecated replaced by {@link #getWindowsRemovableDevicesCommand()}
     *
     * @return the list of removable devices
     */
//    @SuppressWarnings("unused")
//    private ArrayList<USBStorageDevice> getWindowsRemovableDevicesList() {
//
//        /**
//         * TODO: How to put this working in all languages?
//         */
//        String fileSystemDesc = "Removable Disk";
//
//        ArrayList<USBStorageDevice> listDevices = new ArrayList<USBStorageDevice>();
//
//        File[] roots = File.listRoots();
//
//        if (roots == null) {
//            // TODO: raise an error?
//            return listDevices;
//        }
//
//        for (File root : roots) {
//            if (root.canRead() && root.canWrite() && fsView.isDrive(root)
//                    && !fsView.isFloppyDrive(root)) {
//
//                if (fileSystemDesc.equalsIgnoreCase(fsView
//                        .getSystemTypeDescription(root))) {
//                    USBStorageDevice device = new USBStorageDevice(root,
//                            fsView.getSystemDisplayName(root));
//                    listDevices.add(device);
//                }
//
//                System.out.println(fsView.getSystemDisplayName(root) + " - "
//                        + fsView.getSystemTypeDescription(root));
//
//                /*
//                 * FileSystemView.getSystemTypeDescription();
//                 * 
//                 * Windows (8): Windows (7): "Removable Disk" Windows (XP):
//                 * Linux (Ubuntu): OSX (10.7):
//                 */
//            }
//        }
//
//        return listDevices;
//    }
}
