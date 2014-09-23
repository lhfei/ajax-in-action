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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 0.1
 *
 * @author Hefei Li
 *
 * @since Sep 23, 2014
 */
public class GetAndPostExample extends HttpServlet {
	private static final long serialVersionUID = 4825568862643303858L;
	private static final Logger log = LoggerFactory.getLogger(GetAndPostExample.class);

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response, String method)
			throws ServletException, IOException {

		// Set content type of the response to text/xml
		response.setContentType("text/xml");

		// Get the user's input
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String birthday = request.getParameter("birthday");

		// Create the response text
		String responseText = "Hello " + firstName + " " + lastName
				+ ". Your birthday is " + birthday + "." + " [Method: "
				+ method + "]";
		
		log.info("Response text info: {}", responseText);

		// Write the response back to the browser
		PrintWriter out = response.getWriter();
		out.println(responseText);

		// Close the writer
		out.close();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Process the request in method processRequest
		processRequest(request, response, "GET");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Process the request in method processRequest
		processRequest(request, response, "POST");
	}
}
