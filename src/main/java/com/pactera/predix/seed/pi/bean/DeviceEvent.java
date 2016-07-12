package com.pactera.predix.seed.pi.bean;

import java.io.Serializable;
import java.util.List;

public class DeviceEvent implements Serializable {

	private static final long serialVersionUID = 2713813472423894462L;

	private String deviceId;

	private List<EventValue> eventValues;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public List<EventValue> getEventValues() {
		return eventValues;
	}

	public void setEventValues(List<EventValue> eventValues) {
		this.eventValues = eventValues;
	}
}
