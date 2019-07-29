package com.pengheng.domain;


public class QrtzSimpleTriggers {

  private String schedName;
  private String triggerName;
  private String triggerGroup;
  private long repeatCount;
  private long repeatInterval;
  private long timesTriggered;


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


  public long getRepeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(long repeatCount) {
    this.repeatCount = repeatCount;
  }


  public long getRepeatInterval() {
    return repeatInterval;
  }

  public void setRepeatInterval(long repeatInterval) {
    this.repeatInterval = repeatInterval;
  }


  public long getTimesTriggered() {
    return timesTriggered;
  }

  public void setTimesTriggered(long timesTriggered) {
    this.timesTriggered = timesTriggered;
  }

}
