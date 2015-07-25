package com.yan.wang.usbdrivedectector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;

import com.yan.wang.usbdrivedectector.dao.UsbDeviceInfo;
import com.yan.wang.usbdrivedectector.db.DataSourceConnection;
import com.yan.wang.usbdrivedectector.events.IUSBDriveListener;
import com.yan.wang.usbdrivedectector.events.USBStorageEvent;
import com.yan.wang.usbdrivedectector.process.CommandLineExecutor;

public class USBDeviceMain implements IUSBDriveListener {
	
	private static final Logger logger = Logger.getLogger(USBDeviceMain.class);
	
	public static void main(String[] args) {
		
		USBDeviceDetectorManager driveDetector = new USBDeviceDetectorManager();

		for (USBStorageDevice rmDevice : driveDetector.getRemovableDevices()) {

			logger.debug("USBStorageDevice : " + rmDevice.getDeviceName());
		}
		
        driveDetector.addDriveListener(new USBDeviceMain());
	}

	@Override
	public void usbDriveEvent(USBStorageEvent event) {
		logger.debug(event.getEventType());
		logger.debug(event);
		
		Connection connection = null;
        CommandLineExecutor commandExecutor1 = null;
        ResultSet resultSet = null;
        try {
        	
        	DataSourceConnection dataSourceConnection = new DataSourceConnection();
            connection = dataSourceConnection.getConnection();	
           
            
            commandExecutor1 = new CommandLineExecutor();
            commandExecutor1.executeCommand("df");

            String outputLine;
            int i = 0;
            while ((outputLine = commandExecutor1.readOutputLine()) != null) {
        	   logger.debug(outputLine);
        	   if (i > 0) {
        		   
        		   String[] partition = extractPartitionValue(outputLine);
        		   partition[2] = event.getEventType().name();
        		   
            	   
            	   String query = "select * from removable_devices where partition = '" + partition[0] + "';";
            	   Statement statement = connection.createStatement();
            	   resultSet = statement.executeQuery(query);
                   
                   while (resultSet.next()) {
                	   logger.debug(resultSet.getInt("id"));
                	   query = "update removable_devices set mount_path = '" + partition[1] + "', status = '"+ partition[2] + "' where id = "+ resultSet.getInt("id") + ";";
                	   Statement statement2 = connection.createStatement();
                	   statement2.execute(query);
                	   if (statement2 != null) {
                		   statement2.close();
                	   }
                   }
                   if (statement != null) {
                	   statement.close();
                   }
        	   }
        	   i++;
           }
          

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (SQLException e) {
        	logger.error(e.getMessage(), e);
		} finally {
            try {
                commandExecutor1.close();
                if (connection != null) {
                	connection.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } catch (SQLException e) {
            	logger.error(e.getMessage(), e);
			}
        }	
		
	}
	
	public String[] extractPartitionValue(String outputLine) {
		String[] partition = new String[3];
		
		String[] list = new String[outputLine.split(" ").length];
		list = outputLine.split(" ");
		String[] list2 = new String[6];
		int j = 0;
		for (int i = 0; i < list.length; i++) {
			if (!list[i].isEmpty()) {	
				list2[j] = list[i];
				j++;
			}
		}
		
		partition[0] = list[0];
		partition[1] = list2[5];

		logger.debug(partition[0]);
		logger.debug(partition[1]);
		
		return partition;
	} 
}
