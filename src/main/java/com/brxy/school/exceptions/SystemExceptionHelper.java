package com.brxy.school.exceptions;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemExceptionHelper implements SystemExceptionListener {
	private static Set<SystemExceptionListener> listeners = new HashSet<SystemExceptionListener>();
	private ExecutorService executorService = Executors.newCachedThreadPool();

	private SystemExceptionHelper() {
		SystemException.setListener(this);
	}

	public synchronized static void addListener(SystemExceptionListener listener) {
		listeners.add(listener);
	}

	public synchronized static void removeListener(
			SystemExceptionListener listener) {
		listeners.remove(listener);
	}

	public void newExceptionOccure(SystemException occuredException) {
		executorService.submit(new newExceptionHandler(occuredException));
	}

	private static class newExceptionHandler implements Runnable {
		private SystemException processException;

		newExceptionHandler(SystemException occuredException) {
			this.processException = occuredException;
		}

		public void run() {
			if (!listeners.isEmpty()) {
				for (SystemExceptionListener listener : listeners) {
					listener.newExceptionOccure(processException);
				}
			}
		}
	}
}