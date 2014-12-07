package com.deadlock;

import java.util.HashMap;
import java.util.Iterator;

public class DeadLockException extends Exception {

	public DeadLockException(String error) {
		super(error);
	}

	@Override
	public String getMessage() {
		LockHolder holder = LockHolder.getLockHolder();
		String resulr = super.getMessage() + "\n";

		HashMap<Thread, Lock> tl = holder.getThreadLockMap();
		Iterator<Thread> iter = tl.keySet().iterator();
		while (iter.hasNext()) {
			Thread key = iter.next();
			Lock value = tl.get(key);
		}

		return resulr;
	}
}
