package com.deadlock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseLock implements Lock {

	/** ������Ϣ */
	private HashMap<Thread, String> map = new HashMap<Thread, String>();
	private List<String> msgList = new LinkedList<String>();

	/** �������� */
	public void daedLock() {
		LockHolder.deadLock();
	}

	/** ��ʾ������Ϣ */
	public void showLockMsg() {
		String msg = "";
		for (int i = 0; i < msgList.size(); i++) {
			msg += msgList.get(i);
		}
		System.err.println(msg + "\n");
	}

	/**
	 * �����Ϣ
	 * 
	 * @param msgKind
	 *            ��Ϣ����
	 * @param who
	 *            ��Ӧ���߳�
	 */
	public synchronized void addMsg(String msgKind, Thread who) {
		String msg = buildMsg(msgKind, who);
		map.put(who, msg);
		msgList.add(msg);
	}

	/**
	 * �Ƴ��߳��ϵ���Ϣ
	 * 
	 * @param who
	 *            �߳�
	 */
	public synchronized void removeMsg(Thread who) {
		msgList.remove(map.remove(who));
	}

	/**
	 * ���ɵ�ǰ������Ϣ
	 * 
	 * @param msg
	 *            �������� {@link Lock.WAIT},{@link Lock.WRITE}
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
