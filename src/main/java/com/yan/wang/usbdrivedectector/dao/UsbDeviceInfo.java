package com.yan.wang.usbdrivedectector.dao;

public class UsbDeviceInfo {
	
	private Integer id;
	private String partition;
	private String label;
	private String uuid;
	private String mountPath;
	private String status;
	
	
	public String getPartition() {
		return partition;
	}
	public void setPartition(String partition) {
		this.partition = partition;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMountPath() {
		return mountPath;
	}
	public void setMountPath(String mountPath) {
		this.mountPath = mountPath;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
