package com.deadlock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseLock implements Lock {

	/** 管理消息 */
	private HashMap<Thread, String> map = new HashMap<Thread, String>();
	private List<String> msgList = new LinkedList<String>();

	/** 发生死锁 */
	public void daedLock() {
		LockHolder.deadLock();
	}

	/** 显示锁的信息 */
	public void showLockMsg() {
		String msg = "";
		for (int i = 0; i < msgList.size(); i++) {
			msg += msgList.get(i);
		}
		System.err.println(msg + "\n");
	}

	/**
	 * 添加消息
	 * 
	 * @param msgKind
	 *            消息类型
	 * @param who
	 *            对应的线程
	 */
	public synchronized void addMsg(String msgKind, Thread who) {
		String msg = buildMsg(msgKind, who);
		map.put(who, msg);
		msgList.add(msg);
	}

	/**
	 * 移除线程上的消息
	 * 
	 * @param who
	 *            线程
	 */
	public synchronized void removeMsg(Thread who) {
		msgList.remove(map.remove(who));
	}

	/**
	 * 生成当前操作信息
	 * 
	 * @param msg
	 *            操作类型 {@link Lock.WAIT},{@link Lock.WRITE}
	 */
	private synchronized String buildMsg(String msg, Thread currentThread) {
		String result = null;

		switch (msg) {
		case Lock.WAIT: {
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			result = new String(currentThread.getName() + "  wait  "
					+ hashCode() + "  in  " + stacks[4].getClassName()
					+ "  line:  " + stacks[4].getLineNumber() + "\n");
			break;
		}
		case Lock.WRITE: {
			result = new String(currentThread.getName() + "  write  "
					+ hashCode() + "\n");
			break;
		}
		}

		return result;
	}

}
