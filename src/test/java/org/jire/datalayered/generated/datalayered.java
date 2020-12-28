package org.jire.datalayered.generated;

import org.jire.datalayered.AbstractDatabase;

public final class datalayered extends AbstractDatabase {
	
	public datalayered() {
		super("datalayered");
	}
	
	public final datalayered_members members = new datalayered_members(this);
	
	@Override
	public void init() {
		members.init();
	}
	
}
