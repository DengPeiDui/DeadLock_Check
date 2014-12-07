package com.deadlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LockHolder {

	// ����
	private static LockHolder l = new LockHolder();

	/** �߳�--�� ���� map */
	private static HashMap<Thread, Lock> tlHolder = new HashMap<>();
	/** ��--�߳� ���� map */
	private HashMap<Lock, Thread> ltHolder = new HashMap<>();
	/** �߳�--�� ��ȡ map */
	private HashMap<Thread, Lock> tlWant = new HashMap<>();
	/** ��--�߳� ��ȡ map */
	private HashMap<Lock, Thread> ltWant = new HashMap<>();

	private LockHolder() {

	}

	/** ��ȡLockHolder */
	public static LockHolder getLockHolder() {
		return l;
	}

	/** �������������쳣 */
	public static void deadLock() {
		Collection<Lock> l = tlHolder.values();
		Iterator<Lock> it = l.iterator();
		while (it.hasNext()) {
			it.next().showLockMsg();
		}

		try {
			throw new DeadLockException("����\n");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * ���߳�����
	 * 
	 * @param currentThread
	 *            ��ǰ�߳�
	 * @param lock
	 *            ��ǰ��
	 */
	public synchronized void bind(Thread currentThread, Lock lock) {
		tlWant.remove(currentThread);
		ltWant.remove(lock);
		tlHolder.put(currentThread, lock);
		ltHolder.put(lock, currentThread);
	}

	/**
	 * �����
	 * 
	 * @param currentThread
	 *            ��ǰ�߳�
	 * 
	 * @param lock
	 *            ��ǰ��
	 * */
	public synchronized void unbind(Thread currentThread, Lock lock) {
		tlHolder.remove(currentThread);
		ltHolder.remove(lock);
	}

	/**
	 * �ж��Ƿ�������
	 * 
	 * @param currentThread
	 *            ��ǰ�߳�
	 * @param lock
	 *            ��Ҫ��ȡ����
	 * @return �Ƿ�ᷢ���������Ƿ���true�����򷵻�false
	 */
	public synchronized boolean isDeadlock(Thread currentThread, Lock lock) {

		List<Thread> waiter = new ArrayList<Thread>();
		// �ȴ����߳�
		waiter.add(currentThread);

		return isDeadlock(currentThread, lock, waiter);
	}

	private boolean isDeadlock(Thread currentThread, Lock lock,
			List<Thread> waiter) {

		// lock���ĳ�����
		Thread t = ltHolder.get(lock);

		if (t == null)
			return false;
		if (waiter.contains(t))
			return true;

		// t�ڵȴ�����
		Lock l = tlWant.get(t);

		if (l != null) {
			waiter.add(t);
			// �ݹ�Ѱ�Ҷ�����ǰ�˵��̣߳��ж��Ƿ�������
			return isDeadlock(t, l, waiter);
		} else {
			// û�ȴ�����������������
			return false;
		}
	}

	/**
	 * ��ȡ��
	 * 
	 * @param thread
	 *            ��ȡ�����߳�
	 * @param lock
	 *            ��Ҫ��ȡ����
	 */
	public synchronized void want(Thread thread, Lock lock) {
		tlWant.put(thread, lock);
		ltWant.put(lock, thread);
	}

	public HashMap<Thread, Lock> getThreadLockMap() {
		return tlHolder;
	}
}
