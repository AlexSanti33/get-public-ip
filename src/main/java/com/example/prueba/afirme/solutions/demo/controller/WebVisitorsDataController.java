package com.example.prueba.afirme.solutions.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/")
public class WebVisitorsDataController {

	   private final String LOCALHOST_IPV4 = "127.0.0.1";
	    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
	    
	    private static final String[] IP_HEADER_CANDIDATES = {
	            "X-Forwarded-For",
	            "Proxy-Client-IP",
	            "WL-Proxy-Client-IP",
	            "HTTP_X_FORWARDED_FOR",
	            "HTTP_X_FORWARDED",
	            "HTTP_X_CLUSTER_CLIENT_IP",
	            "HTTP_CLIENT_IP",
	            "HTTP_FORWARDED_FOR",
	            "HTTP_FORWARDED",
	            "HTTP_VIA",
	            "X-Real-IP",
	            "REMOTE_ADDR" };
	    
		@GetMapping("/ip-cliente")
		public String getIpCliente(HttpServletRequest request) {
			
			return getClientIp(request);
		}
		  public String getClientIp(HttpServletRequest request) {
			  Enumeration<String> he =request.getHeaderNames();
			  
			  he.asIterator().forEachRemaining(x->System.out.println(x.indexOf(1)));

			  for (String header : IP_HEADER_CANDIDATES) {
			        String ip = request.getHeader(header);
			        System.out.println("ip: " +ip);
			        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			            return ip;
			        }
			    }
			  
		        String ipAddress = request.getHeader("X-Forwarded-For");
		 
		        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
		            ipAddress = request.getHeader("Proxy-Client-IP");
		        }
		 
		        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
		            ipAddress = request.getHeader("X-Real-IP");
		        }
		 
		        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
		            ipAddress = request.getRemoteAddr();
		            if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
		                try {
		                    InetAddress inetAddress = InetAddress.getLocalHost();
		                    ipAddress = inetAddress.getHostAddress();
		                } catch (UnknownHostException e) {
		                    e.printStackTrace();
		                }
		            }
		        }
		 
		        if (!StringUtils.isEmpty(ipAddress)
		                && ipAddress.length() > 15
		                && ipAddress.indexOf(",") > 0) {
		            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		        }
		        return ipAddress;
		       }
}
