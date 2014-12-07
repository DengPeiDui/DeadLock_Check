package com.deadlock;

public class ReentrantLock extends WriteLock {

	/** ������ReentrantLock */
	private java.util.concurrent.locks.ReentrantLock lock;

	public ReentrantLock() {
		lock = new java.util.concurrent.locks.ReentrantLock();
	}

	@Override
	public void lock() {
		// ����ǰ��׼������
		preLock();
		// ����
		lock.lock();
		// ��ɼ���
		finishLock();
	}

	@Override
	public void unlock() {
		// �ͷ���
		lock.unlock();
		// ����ͷ���
		finishUnLock();
	}

}
