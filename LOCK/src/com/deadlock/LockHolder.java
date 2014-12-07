package com.deadlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LockHolder {

	// 单例
	private static LockHolder l = new LockHolder();

	/** 线程--锁 持有 map */
	private static HashMap<Thread, Lock> tlHolder = new HashMap<>();
	/** 锁--线程 持有 map */
	private HashMap<Lock, Thread> ltHolder = new HashMap<>();
	/** 线程--锁 争取 map */
	private HashMap<Thread, Lock> tlWant = new HashMap<>();
	/** 锁--线程 争取 map */
	private HashMap<Lock, Thread> ltWant = new HashMap<>();

	private LockHolder() {

	}

	/** 获取LockHolder */
	public static LockHolder getLockHolder() {
		return l;
	}

	/** 发生死锁，报异常 */
	public static void deadLock() {
		Collection<Lock> l = tlHolder.values();
		Iterator<Lock> it = l.iterator();
		while (it.hasNext()) {
			it.next().showLockMsg();
		}

		try {
			throw new DeadLockException("死锁\n");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * 绑定线程与锁
	 * 
	 * @param currentThread
	 *            当前线程
	 * @param lock
	 *            当前锁
	 */
	public synchronized void bind(Thread currentThread, Lock lock) {
		tlWant.remove(currentThread);
		ltWant.remove(lock);
		tlHolder.put(currentThread, lock);
		ltHolder.put(lock, currentThread);
	}

	/**
	 * 解除绑定
	 * 
	 * @param currentThread
	 *            当前线程
	 * 
	 * @param lock
	 *            当前锁
	 * */
	public synchronized void unbind(Thread currentThread, Lock lock) {
		tlHolder.remove(currentThread);
		ltHolder.remove(lock);
	}

	/**
	 * 判断是否发生死锁
	 * 
	 * @param currentThread
	 *            当前线程
	 * @param lock
	 *            所要争取的锁
	 * @return 是否会发生死锁，是返回true，否则返回false
	 */
	public synchronized boolean isDeadlock(Thread currentThread, Lock lock) {

		List<Thread> waiter = new ArrayList<Thread>();
		// 等待的线程
		waiter.add(currentThread);

		return isDeadlock(currentThread, lock, waiter);
	}

	private boolean isDeadlock(Thread currentThread, Lock lock,
			List<Thread> waiter) {

		// lock锁的持有人
		Thread t = ltHolder.get(lock);

		if (t == null)
			return false;
		if (waiter.contains(t))
			return true;

		// t在等待的锁
		Lock l = tlWant.get(t);

		if (l != null) {
			waiter.add(t);
			// 递归寻找队列最前端的线程，判断是否发生死锁
			return isDeadlock(t, l, waiter);
		} else {
			// 没等待的锁，不发生死锁
			return false;
		}
	}

	/**
	 * 争取锁
	 * 
	 * @param thread
	 *            争取锁的线程
	 * @param lock
	 *            所要争取的锁
	 */
	public synchronized void want(Thread thread, Lock lock) {
		tlWant.put(thread, lock);
		ltWant.put(lock, thread);
	}

	public HashMap<Thread, Lock> getThreadLockMap() {
		return tlHolder;
	}
}
