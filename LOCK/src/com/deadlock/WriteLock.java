package com.deadlock;

public abstract class WriteLock extends BaseLock {

	private LockHolder holder = LockHolder.getLockHolder();

	/** ����ǰ��׼������ */
	public void preLock() {
		// ��ȡ��
		holder.want(Thread.currentThread(), this);
		// ����ȡ�����й���Ϣѹ��ջ
		addMsg(Lock.WAIT, Thread.currentThread());
		// �ж��Ƿ�����
		if (holder.isDeadlock(Thread.currentThread(), this)) {
			// ��������
			// System.out.println("����������");
			daedLock();
		}
	}

	/** ������� */
	public void finishLock() {
		// �����ɹ����������뵱ǰ�̰߳�
		holder.bind(Thread.currentThread(), this);
		// �����ɹ����������й���Ϣ��ջ����
		removeMsg(Thread.currentThread());
		// �����ɹ����������й���Ϣѹ��ջ
		addMsg(Lock.WRITE, Thread.currentThread());
	}

	/** �ͷ������ */
	public void finishUnLock() {
		// �����
		holder.unbind(Thread.currentThread(), this);
		// �����йص���Ϣ����
		removeMsg(Thread.currentThread());
	}

}
