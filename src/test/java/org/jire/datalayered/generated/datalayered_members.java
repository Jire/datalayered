package org.jire.datalayered.generated;

import org.jetbrains.annotations.NotNull;
import org.jire.datalayered.AbstractTable;
import org.jire.datalayered.Database;

public final class datalayered_members extends AbstractTable {
	
	public final datalayered_members_uid uid = new datalayered_members_uid(this);
	public final datalayered_members_points points = new datalayered_members_points(this);
	
	public datalayered_members(@NotNull Database database) {
		super(database, "members", 1_000_000);
	}
	
	@Override
	public void initColumns() {
		uid.init();
		points.init();
	}
	
}
