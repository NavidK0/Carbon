/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package sun.jvmstat.monitor.event;

import java.util.List;

import sun.jvmstat.monitor.MonitoredVm;

/**
 * Provides a description of a change in status of the instrumentation exported by the MonitoredVm.
 *
 * @author Brian Doherty
 * @since 1.5
 */
@SuppressWarnings("serial")
public class MonitorStatusChangeEvent extends VmEvent {

	/**
	 * List of instrumentation objects inserted since the last event. Elements of this list will always be of type Monitor.
	 */
	protected List<?> inserted;

	/**
	 * List of instrumentation objects removed since the last event. Elements of this list will always be of type Monitor.
	 */
	protected List<?> removed;

	/**
	 * Construct a new MonitorStatusChangeEvent.
	 *
	 * @param vm
	 *            the MonitoredVm source of the event.
	 * @param inserted
	 *            the list of instrumentation objects inserted since the last event.
	 * @param removed
	 *            the list of instrumentation objects removed since the last event.
	 */
	public MonitorStatusChangeEvent(MonitoredVm vm, List<?> inserted, List<?> removed) {
		super(vm);
		this.inserted = inserted;
		this.removed = removed;
	}

	/**
	 * Return the list of instrumentation objects that were inserted since the last event notification.
	 *
	 * @return List - a List of Monitor objects that were inserted into the instrumentation exported by the MonitoredHost. If no new instrumentation was inserted, an emply List is returned.
	 */
	public List<?> getInserted() {
		return inserted;
	}

	/**
	 * Return the set of instrumentation objects that were removed since the last event notification.
	 *
	 * @return List - a List of Monitor objects that were removed from the instrumentation exported by the MonitoredHost. If no instrumentation was removed, an emply List is returned.
	 */
	public List<?> getRemoved() {
		return removed;
	}
}
