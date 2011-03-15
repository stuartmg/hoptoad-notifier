# Java Hoptoad Notifier

Java Hoptoad Notifier is a small project to allow Java/Log4j projects to send errors to Hoptoad (or any other service that implements the Hoptoad API interface). It is currently being used to monitor all of our Java applications at BiddingForGood.

## Basic Setup

To capture errors, you will need to configure a log4j appender and then log your exceptions to it:

<pre><code>
<appender name="HOPTOAD" class="com.radialaspect.hoptoad.HoptoadAppender">
  
  <!-- optional url - specify this if you wish to use a service besides hoptoad
    <param name="url" value="" />
  -->

  <!-- enter your hoptoad api key  -->
	<param name="api_key" value="<your api key here>" />

  <!-- enter your hoptoad api key  -->
	<param name="env" value="< development|production >" />

	<!-- if your hoptoad account supports SSL, then set this to true -->
	<param name="ssl" value="true" />
	
	<!-- filter out any exceptions in the following packages -->
	<filter class="com.radialaspect.hoptoad.PackageFilter">
		<param name="packageNames" value="org.jboss, org.apache.catalina, org.apache.commons.fileupload" />
	</filter>

	<!-- for more generic exceptions, you can filter out specific exceptions my message -->
	<filter class="com.radialaspect.hoptoad.RegexFilter">
		<param name="className" value="org.apache.catalina.core.ApplicationDispatcher" />
		<param name="pattern" value="ServletException: Original SevletResponse or wrapped.*" />
	</filter>

</appender>
</code></pre>

If you are using a web application there is a servlet filter that you can include and the appender will automatically collect the request and session information associated with the exception. If the servlet filter is not installed, this information will not be available on hoptoad.

Add the following to your web.xml file:

<pre><code>
  <filter>
      <filter-name>HoptoadNotifierFilter</filter-name>
      <filter-class>com.radialaspect.hoptoad.servlet.HoptoadNotifierFilter</filter-class>
  </filter>
	<filter-mapping>
		<filter-name>HoptoadNotifierFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
</code></pre>
