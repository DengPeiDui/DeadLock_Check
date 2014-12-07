package com.deadlock;

public abstract class WriteLock extends BaseLock {

	private LockHolder holder = LockHolder.getLockHolder();

	/** 上锁前的准备工作 */
	public void preLock() {
		// 争取锁
		holder.want(Thread.currentThread(), this);
		// 将争取锁的有关信息压入栈
		addMsg(Lock.WAIT, Thread.currentThread());
		// 判断是否死锁
		if (holder.isDeadlock(Thread.currentThread(), this)) {
			// 发生死锁
			// System.out.println("死锁！！！");
			daedLock();
		}
	}

	/** 上锁完成 */
	public void finishLock() {
		// 上锁成功，将该锁与当前线程绑定
		holder.bind(Thread.currentThread(), this);
		// 上锁成功，将锁的有关信息从栈弹出
		removeMsg(Thread.currentThread());
		// 上锁成功，将锁的有关信息压入栈
		addMsg(Lock.WRITE, Thread.currentThread());
	}

	/** 释放锁完成 */
	public void finishUnLock() {
		// 解除绑定
		holder.unbind(Thread.currentThread(), this);
		// 将锁有关的信息弹出
		removeMsg(Thread.currentThread());
	}

}
