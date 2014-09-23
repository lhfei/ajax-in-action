/*
 * Copyright 2010-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.lhfei.ajax.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Sep 23, 2014
 */
public class PostingXMLExample extends HttpServlet {
	private static final long serialVersionUID = 7049818248261604973L;
	
	private static final Logger log = LoggerFactory.getLogger(PostingXMLExample.class);

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String xml = readXMLFromRequestBody(request);
		log.info("The xml you post: " +xml);
		
		Document xmlDoc = null;
		try {
			xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new ByteArrayInputStream(xml.getBytes()));
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException: " + e.getMessage(), e);
			
		} catch (SAXException e) {
			log.error("SAXException: " + e.getMessage(), e);
		}

		/*
		 * Note how the Java implementation of the W3C DOM has the same methods
		 * as the JavaScript implementation, such as getElementsByTagName and
		 * getNodeValue.
		 */
		NodeList selectedPetTypes = xmlDoc.getElementsByTagName("type");
		String type = null;
		String responseText = "Selected Pets: ";
		for (int i = 0; i < selectedPetTypes.getLength(); i++) {
			type = selectedPetTypes.item(i).getFirstChild().getNodeValue();
			responseText = responseText + " " + type;
		}
		
		response.setContentType("text/xml");
		response.getWriter().print(responseText);
	}

	private String readXMLFromRequestBody(HttpServletRequest request) {
		StringBuffer xml = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				xml.append(line);
			}
		} catch (Exception e) {
			log.error("Error reading XML: " + e.getMessage(), e);
		}
		return xml.toString();
	}
}
