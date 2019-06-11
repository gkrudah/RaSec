package com.tipw.global;

import com.tipw.coap.resource.ObserveResource;

public class DeviceInfo {

	/*
	 * Data Structure
	 * 
	 * 1. Device Information 2. Event Control
	 * 
	 * 
	 */

	// 1. Device Information
	String deviceID;
	String mode; // pull, push
	String state; // Current Device State on, off
	boolean camera;
	boolean buzzer;
	String event; // When an event is requested
	boolean isEvent;

	ObserveResource resource;

	public DeviceInfo(String deviceID, String state, String mode) {
		this.deviceID = deviceID;
		this.state = state;
		this.mode = mode;
	}

	public ObserveResource getResource() { return resource; }

	public void setResource (ObserveResource resource) { this.resource = resource; }

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean getCamera() {
		return camera;
	}

	public void setCamera(boolean camera) {
		this.camera = camera;
	}

	public boolean getBuzzer() {
		return buzzer;
	}

	public void setBuzzer(boolean buzzer) {
		this.buzzer = buzzer;
	}

	public boolean getIsEvent() {
		return isEvent;
	}

	public void setIsEvent(boolean isEvent) {
		this.isEvent = isEvent;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	// observe
	/*
	 * 2. Event Control
	 */

	public boolean setSensorState(int type, boolean state) {
		if (type == 0)
			this.setCamera(state);
		else if (type == 1)
			this.setBuzzer(state);

		if (this.mode.equals("push")) {
			if (this.resource == null) { return false; }
			else { this.resource.changed(); }
		}

		return true;
	}

	public boolean ControlEvent(String newState) {
		this.event = newState;
		if (this.mode.equals("pull")) {
			this.isEvent = true;
		}
		else {
			if (this.resource == null) { return false; }
			else { this.resource.changed(); }
		}
		return true;
	}

}
