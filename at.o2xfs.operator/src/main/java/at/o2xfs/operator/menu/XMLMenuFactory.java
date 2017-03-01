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

package at.o2xfs.operator.menu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import at.o2xfs.operator.task.ExecuteTaskCommand;
import at.o2xfs.operator.task.Task;
import at.o2xfs.operator.task.TaskCommand;

public class XMLMenuFactory {

	private MenuTask menuTask;

	public List<TaskCommand> loadMenu(MenuTask menuTask, InputStream inStream) {
		List<TaskCommand> result = null;
		this.menuTask = menuTask;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(inStream);
			result = processMenuCommand(
					document.getElementsByTagName("Menu").item(0))
					.getChildren();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private MenuCommand processMenuCommand(Node node) {
		String label = null;
		List<TaskCommand> commands = new ArrayList<TaskCommand>();
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node child = childNodes.item(i);
			if ("Label".equalsIgnoreCase(child.getNodeName())) {
				label = child.getTextContent();
			} else if ("MenuCommand".equalsIgnoreCase(child.getNodeName())) {
				commands.add(processMenuCommand(child));
			} else if ("TaskCommand".equals(child.getNodeName())) {
				commands.add(processTaskCommand(child));
			}
		}
		MenuCommand menuCommand = new MenuCommand(menuTask, label, commands);
		return menuCommand;
	}

	private TaskCommand processTaskCommand(Node node) {
		Task task = null;
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node child = childNodes.item(i);
			if ("Class".equals(child.getNodeName())) {
				task = createTask(child.getTextContent());
			}
		}
		return new ExecuteTaskCommand(menuTask.getTaskManager(), task);
	}

	private Task createTask(String className) {
		Task result = null;
		try {
			Class<Task> taskClass = (Class<Task>) Class.forName(className);
			result = taskClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
}