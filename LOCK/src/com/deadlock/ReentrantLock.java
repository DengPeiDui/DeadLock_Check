package com.deadlock;

public class ReentrantLock extends WriteLock {

	/** 真正的ReentrantLock */
	private java.util.concurrent.locks.ReentrantLock lock;

	public ReentrantLock() {
		lock = new java.util.concurrent.locks.ReentrantLock();
	}

	@Override
	public void lock() {
		// 加锁前的准备工作
		preLock();
		// 加锁
		lock.lock();
		// 完成加锁
		finishLock();
	}

	@Override
	public void unlock() {
		// 释放锁
		lock.unlock();
		// 完成释放锁
		finishUnLock();
	}

}
