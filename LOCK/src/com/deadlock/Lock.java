package com.deadlock;

public interface Lock {

	/** 等待锁 */
	String WAIT = "a";
	/** 写 */
	String WRITE = "w";

	/** 上锁 */
	void lock();

	/** 释放锁 */
	void unlock();

	/** 显示锁的信息 */
	void showLockMsg();

}
