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

package at.o2xfs.operator.ui.swing;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import at.o2xfs.log.Logger;
import at.o2xfs.log.LoggerFactory;

public class SwingUtil {

	private final static Logger LOG = LoggerFactory.getLogger(SwingUtil.class);

	private static DocumentBuilder builder = null;

	private static Transformer transformer = null;

	static {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		} catch (Exception e) {
			LOG.error("static", "", e);
		}
	}

	private SwingUtil() {
		return;
	}

	public static String textToHTML(final String text) {
		if (text == null) {
			return null;
		}
		try {
			final Document document = builder.newDocument();
			final Element html = document.createElement("html");
			document.appendChild(html);
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new StringReader(text));
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (html.hasChildNodes()) {
						html.appendChild(document.createElement("br"));
					}
					html.appendChild(document.createTextNode(line));
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
			final StringWriter xml = new StringWriter();
			transformer.transform(new DOMSource(document),
					new StreamResult(xml));
			return xml.toString();
		} catch (Exception e) {
			final String method = "textToHTML(String)";
			LOG.error(method, "Error converting String: " + text, e);
		}
		return text;
	}
}