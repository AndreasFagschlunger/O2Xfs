/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.operator.task.xfs;

import java.util.List;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;
import at.o2xfs.operator.task.AbstractBackCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.task.TaskCommand;
import at.o2xfs.operator.ui.content.table.Table;
import at.o2xfs.operator.ui.content.text.Label;
import at.o2xfs.xfs.service.XfsService;
import at.o2xfs.xfs.service.XfsServiceManager;

public abstract class XfsServiceTask<T extends XfsService> extends Task {

	private static final Logger LOG = LoggerFactory.getLogger(XfsServiceTask.class);

	protected final XfsServiceManager serviceManager;

	protected T service = null;

	private boolean cancelled = false;

	public XfsServiceTask() {
		this(null);
	}

	public XfsServiceTask(T service) {
		serviceManager = XfsServiceManager.getInstance();
		this.service = service;
	}

	@Override
	final protected void doExecute() {
		final String method = "doExecute()";
		if (service != null) {
			execute();
		} else {
			List<T> services = serviceManager.getServices(getServiceClass());
			if (services.isEmpty()) {
				showError("ServiceNotFound");
				return;
			} else if (services.size() == 1) {
				service = services.get(0);
				execute();
			} else {
				createTable(services);
				getCommands().setBackCommand(new AbstractBackCommand() {

					@Override
					public void execute() {
						cancel();
					}
				});
				synchronized (this) {
					while (!cancelled && service == null) {
						try {
							if (LOG.isInfoEnabled()) {
								LOG.info(method, "Waiting for user ...");
							}
							wait();
						} catch (InterruptedException e) {
							LOG.error(method, "Interrupted", e);
							return;
						}
					}
				}
				if (service != null) {
					execute();
				}
			}
		}
	}

	private void createTable(List<T> services) {
		Table table = new Table(getClass(), "Service");
		for (final T service : services) {
			table.addRowWithCommand(new TaskCommand() {

				@Override
				public Label getLabel() {
					return new Label(service.getLogicalName());
				}

				@Override
				public void execute() {
					selectService(service);
				}
			}, service);
		}
		getContent().setUIElement(table);
	}

	private void selectService(T service) {
		synchronized (this) {
			this.service = service;
			notifyAll();
		}
	}

	private void cancel() {
		synchronized (this) {
			cancelled = true;
			notifyAll();
		}
		taskManager.closeTask();
	}

	abstract protected Class<T> getServiceClass();

	abstract protected void execute();
}