/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.tools.attach;

import com.sun.tools.attach.spi.AttachProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.IOException;

public abstract class VirtualMachine {

	private AttachProvider provider;
	private String id;
	private volatile int hash; // 0 => not computed

	protected VirtualMachine(AttachProvider provider, String id) {
		if (provider == null) {
			throw new NullPointerException("provider cannot be null");
		}
		if (id == null) {
			throw new NullPointerException("id cannot be null");
		}
		this.provider = provider;
		this.id = id;
	}

	public static List<VirtualMachineDescriptor> list() {
		ArrayList<VirtualMachineDescriptor> l = new ArrayList<VirtualMachineDescriptor>();
		List<AttachProvider> providers = AttachProvider.providers();
		for (AttachProvider provider : providers) {
			l.addAll(provider.listVirtualMachines());
		}
		return l;
	}

	public static VirtualMachine attach(String id) throws AttachNotSupportedException, IOException {
		if (id == null) {
			throw new NullPointerException("id cannot be null");
		}
		List<AttachProvider> providers = AttachProvider.providers();
		if (providers.size() == 0) {
			throw new AttachNotSupportedException("no providers installed");
		}
		AttachNotSupportedException lastExc = null;
		for (AttachProvider provider : providers) {
			try {
				return provider.attachVirtualMachine(id);
			} catch (AttachNotSupportedException x) {
				lastExc = x;
			}
		}
		throw lastExc;
	}

	public static VirtualMachine attach(VirtualMachineDescriptor vmd) throws AttachNotSupportedException, IOException {
		return vmd.provider().attachVirtualMachine(vmd);
	}

	public abstract void detach() throws IOException;

	public final AttachProvider provider() {
		return provider;
	}

	public final String id() {
		return id;
	}

	public abstract void loadAgentLibrary(String agentLibrary, String options) throws AgentLoadException, AgentInitializationException, IOException;

	public void loadAgentLibrary(String agentLibrary) throws AgentLoadException, AgentInitializationException, IOException {
		loadAgentLibrary(agentLibrary, null);
	}

	public abstract void loadAgentPath(String agentPath, String options) throws AgentLoadException, AgentInitializationException, IOException;

	public void loadAgentPath(String agentPath) throws AgentLoadException, AgentInitializationException, IOException {
		loadAgentPath(agentPath, null);
	}

	public abstract void loadAgent(String agent, String options) throws AgentLoadException, AgentInitializationException, IOException;

	public void loadAgent(String agent) throws AgentLoadException, AgentInitializationException, IOException {
		loadAgent(agent, null);
	}

	public abstract Properties getSystemProperties() throws IOException;

	public abstract Properties getAgentProperties() throws IOException;

	public int hashCode() {
		if (hash != 0) {
			return hash;
		}
		hash = provider.hashCode() * 127 + id.hashCode();
		return hash;
	}

	public boolean equals(Object ob) {
		if (ob == this)
			return true;
		if (!(ob instanceof VirtualMachine))
			return false;
		VirtualMachine other = (VirtualMachine) ob;
		if (other.provider() != this.provider()) {
			return false;
		}
		if (!other.id().equals(this.id())) {
			return false;
		}
		return true;
	}

	public String toString() {
		return provider.toString() + ": " + id;
	}

}
