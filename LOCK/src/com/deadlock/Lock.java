package com.deadlock;

public interface Lock {

	/** �ȴ��� */
	String WAIT = "a";
	/** д */
	String WRITE = "w";

	/** ���� */
	void lock();

	/** �ͷ��� */
	void unlock();

	/** ��ʾ������Ϣ */
	void showLockMsg();

}
