package com.smartlynx.azuretest;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;

import java.sql.Timestamp;

public class QueueTest {

    class Heartbeat {
        String type;
        String pid;
        Integer active;
        Integer park_state;
        Integer uptime;
        Integer event_count;
        Integer false_event_count;
        Integer received_message_count;
        Integer battery_voltage;
        Integer battery_voltage_after_prev_comm;
        Double signal_quality;
        Timestamp timestamp;

        public Heartbeat(String type, String pid, Integer active, Integer park_state, Integer uptime, Integer event_count, Integer false_event_count, Integer received_message_count, Integer battery_voltage, Integer battery_voltage_after_prev_comm, Double signal_quality, Timestamp timestamp) {
            this.type = type;
            this.pid = pid;
            this.active = active;
            this.park_state = park_state;
            this.uptime = uptime;
            this.event_count = event_count;
            this.false_event_count = false_event_count;
            this.received_message_count = received_message_count;
            this.battery_voltage = battery_voltage;
            this.battery_voltage_after_prev_comm = battery_voltage_after_prev_comm;
            this.signal_quality = signal_quality;
            this.timestamp = timestamp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public Integer getPark_state() {
            return park_state;
        }

        public void setPark_state(Integer park_state) {
            this.park_state = park_state;
        }

        public Integer getUptime() {
            return uptime;
        }

        public void setUptime(Integer uptime) {
            this.uptime = uptime;
        }

        public Integer getEvent_count() {
            return event_count;
        }

        public void setEvent_count(Integer event_count) {
            this.event_count = event_count;
        }

        public Integer getFalse_event_count() {
            return false_event_count;
        }

        public void setFalse_event_count(Integer false_event_count) {
            this.false_event_count = false_event_count;
        }

        public Integer getReceived_message_count() {
            return received_message_count;
        }

        public void setReceived_message_count(Integer received_message_count) {
            this.received_message_count = received_message_count;
        }

        public Integer getBattery_voltage() {
            return battery_voltage;
        }

        public void setBattery_voltage(Integer battery_voltage) {
            this.battery_voltage = battery_voltage;
        }

        public Integer getBattery_voltage_after_prev_comm() {
            return battery_voltage_after_prev_comm;
        }

        public void setBattery_voltage_after_prev_comm(Integer battery_voltage_after_prev_comm) {
            this.battery_voltage_after_prev_comm = battery_voltage_after_prev_comm;
        }

        public Double getSignal_quality() {
            return signal_quality;
        }

        public void setSignal_quality(Double signal_quality) {
            this.signal_quality = signal_quality;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }
    }

    class ParkingEvent {
        String type;
        String pid;
        Integer park_state;
        Integer uptime;
        Integer event_count;
        Integer sent_message_count;
        Integer battery_voltage;
        Integer battery_voltage_after_prev_comm;
        Double signal_quality;
        Timestamp timestamp;
        String event_type;

        public ParkingEvent(String type, String pid, Integer park_state, Integer uptime, Integer event_count, Integer sent_message_count, Integer battery_voltage, Integer battery_voltage_after_prev_comm, Double signal_quality, Timestamp timestamp, String event_type) {
            this.type = type;
            this.pid = pid;
            this.park_state = park_state;
            this.uptime = uptime;
            this.event_count = event_count;
            this.sent_message_count = sent_message_count;
            this.battery_voltage = battery_voltage;
            this.battery_voltage_after_prev_comm = battery_voltage_after_prev_comm;
            this.signal_quality = signal_quality;
            this.timestamp = timestamp;
            this.event_type = event_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Integer getPark_state() {
            return park_state;
        }

        public void setPark_state(Integer park_state) {
            this.park_state = park_state;
        }

        public Integer getUptime() {
            return uptime;
        }

        public void setUptime(Integer uptime) {
            this.uptime = uptime;
        }

        public Integer getEvent_count() {
            return event_count;
        }

        public void setEvent_count(Integer event_count) {
            this.event_count = event_count;
        }

        public Integer getSent_message_count() {
            return sent_message_count;
        }

        public void setSent_message_count(Integer sent_message_count) {
            this.sent_message_count = sent_message_count;
        }

        public Integer getBattery_voltage() {
            return battery_voltage;
        }

        public void setBattery_voltage(Integer battery_voltage) {
            this.battery_voltage = battery_voltage;
        }

        public Integer getBattery_voltage_after_prev_comm() {
            return battery_voltage_after_prev_comm;
        }

        public void setBattery_voltage_after_prev_comm(Integer battery_voltage_after_prev_comm) {
            this.battery_voltage_after_prev_comm = battery_voltage_after_prev_comm;
        }

        public Double getSignal_quality() {
            return signal_quality;
        }

        public void setSignal_quality(Double signal_quality) {
            this.signal_quality = signal_quality;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public String getEvent_type() {
            return event_type;
        }

        public void setEvent_type(String event_type) {
            this.event_type = event_type;
        }
    }

    static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        System.out.println("Processing message. Contents: " + message.getBody());
    }

}
