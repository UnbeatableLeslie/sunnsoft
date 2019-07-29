package com.pengheng.domain;


public class QrtzSchedulerState {

  private String schedName;
  private String instanceName;
  private long lastCheckinTime;
  private long checkinInterval;


  public String getSchedName() {
    return schedName;
  }

  public void setSchedName(String schedName) {
    this.schedName = schedName;
  }


  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }


  public long getLastCheckinTime() {
    return lastCheckinTime;
  }

  public void setLastCheckinTime(long lastCheckinTime) {
    this.lastCheckinTime = lastCheckinTime;
  }


  public long getCheckinInterval() {
    return checkinInterval;
  }

  public void setCheckinInterval(long checkinInterval) {
    this.checkinInterval = checkinInterval;
  }

}
