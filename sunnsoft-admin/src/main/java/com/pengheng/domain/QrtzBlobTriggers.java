package com.pengheng.domain;


public class QrtzBlobTriggers {

  private String schedName;
  private String triggerName;
  private String triggerGroup;
  private String blobData;


  public String getSchedName() {
    return schedName;
  }

  public void setSchedName(String schedName) {
    this.schedName = schedName;
  }


  public String getTriggerName() {
    return triggerName;
  }

  public void setTriggerName(String triggerName) {
    this.triggerName = triggerName;
  }


  public String getTriggerGroup() {
    return triggerGroup;
  }

  public void setTriggerGroup(String triggerGroup) {
    this.triggerGroup = triggerGroup;
  }


  public String getBlobData() {
    return blobData;
  }

  public void setBlobData(String blobData) {
    this.blobData = blobData;
  }

}
