package org.jire.datalayered.test;

import org.jire.datalayered.test.generated.datalayered;
import org.jire.datalayered.test.generated.datalayered_members;

public final class TestJava {
	
	public static void main(String[] args) {
		datalayered datalayered = new datalayered();
		datalayered.init();
		
		datalayered_members members = datalayered.members;
		System.out.println("bro " + members.getSize() + " / " + members.getMapSize());
		System.out.println("default UID " + members.uid.get(members.getDefaultKey()));
		
		long member1 = members.newKey();
		System.out.println("member1=" + member1);
		
		members.uid.set(member1, 1337);
		
		long member1UID = members.uid.get(member1);
		System.out.println("member1UID=" + member1UID);
	}
	
}
