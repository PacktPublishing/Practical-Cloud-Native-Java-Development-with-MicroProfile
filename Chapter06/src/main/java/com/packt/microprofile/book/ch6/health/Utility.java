package com.packt.microprofile.book.ch6.health;

import java.lang.management.ManagementFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;

public class Utility {

    public static Double getMemUsage() {
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

        CompositeData compositeData;
        try {
            compositeData = (CompositeData) mbeanServer.getAttribute(new ObjectName("java.lang:type=Memory"),
                    "HeapMemoryUsage");
            Number heapUsed = (Number) compositeData.get("used");
            compositeData = (CompositeData) mbeanServer.getAttribute(new ObjectName("java.lang:type=Memory"),
                    "HeapMemoryUsage");
            Number heapMax = (Number) compositeData.get("max");
            return heapUsed.doubleValue() / heapMax.doubleValue();
        } catch (InstanceNotFoundException | AttributeNotFoundException | MalformedObjectNameException
                | ReflectionException | MBeanException exception) {
            exception.printStackTrace();
            System.err.println(
                    "Encountered an error while retrieving Mbeans. Will default to a memory usage value of 0.5");
            return 0.5;
        }
    }

    public static Double getCpuUsage() {
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();

        Number cpuLoad;
        try {
            cpuLoad = (Number) mbeanServer.getAttribute(new ObjectName("java.lang:type=OperatingSystem"),
                    "ProcessCpuLoad");
            return cpuLoad.doubleValue();
        } catch (InstanceNotFoundException | AttributeNotFoundException | MalformedObjectNameException
                | ReflectionException | MBeanException e) {
            e.printStackTrace();
            System.err
                    .println("Encountered an error while retrieving Mbeans. Will default to a CPU usage value of 0.5");
            return 0.5;
        }

    }
}
