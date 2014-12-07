package com.test;

//import java.util.concurrent.locks.ReentrantLock;

import com.deadlock.ReentrantLock;

public class MainTest {

	public static void main(String[] args) {

		final ReentrantLock lock1 = new ReentrantLock();

		final ReentrantLock lock2 = new ReentrantLock();
		
		final ReentrantLock lock3 = new ReentrantLock();

		// lock1.lock();
		// lock2.lock();

		new Thread(new Runnable() {
			@Override
			public void run() {
				lock1.lock();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// lock1.unLock();
				lock2.lock();
				System.out.println("1");
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				lock2.lock();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock3.lock();
				lock2.unlock();
				System.out.println("2");
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				lock3.lock();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock1.lock();
				lock3.unlock();
				System.out.println("3");
			}
		}).start();

	}

}
