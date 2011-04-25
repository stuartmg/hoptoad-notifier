Java Hoptoad Notifier
=====================

Java Hoptoad Notifier is a small project to allow Java/Log4j projects to send errors to Hoptoad (or any other service that implements the Hoptoad API interface). It is currently being used to monitor all of our Java applications at [BiddingForGood](http://www.biddingforgood.com).

Basic Setup
-----------

To capture errors, you will need to configure a log4j appender and then log your exceptions to it:

<pre><code>
&lt;appender name="HOPTOAD" class="com.radialaspect.hoptoad.HoptoadAppender">
  
  &lt;!-- optional url - specify this if you wish to use a service besides hoptoad
    &lt;param name="url" value="" />
  -->

  &lt;!-- fluxtracker url - uncomment this line to use FluxTracker instead of hoptoad
    &lt;param name="url" value="www.fluxtracker.com" />
  -->

  &lt;!-- enter your hoptoad api key  -->
	&lt;param name="api_key" value="&lt;your api key here>" />

  &lt;!-- enter your hoptoad api key  -->
	&lt;param name="env" value="&lt; development|production >" />

	&lt;!-- if your hoptoad account supports SSL, then set this to true -->
	&lt;param name="ssl" value="true" />
	
	&lt;!-- filter out any exceptions in the following packages -->
	&lt;filter class="com.radialaspect.hoptoad.PackageFilter">
		&lt;param name="packageNames" value="org.jboss, org.apache.catalina, org.apache.commons.fileupload" />
	&lt;/filter>

	&lt;!-- for more generic exceptions, you can filter out specific exceptions my message -->
	&lt;filter class="com.radialaspect.hoptoad.RegexFilter">
		&lt;param name="className" value="org.apache.catalina.core.ApplicationDispatcher" />
		&lt;param name="pattern" value="ServletException: Original SevletResponse or wrapped.*" />
	&lt;/filter>

&lt;/appender>
</code></pre>

If you are using a web application there is a servlet filter that you can include and the appender will automatically collect the request and session information associated with the exception. If the servlet filter is not installed, this information will not be available on hoptoad.

Add the following to your web.xml file:

<pre><code>
  &lt;filter>
    &lt;filter-name>HoptoadNotifierFilter&lt;/filter-name>
    &lt;filter-class>com.radialaspect.hoptoad.servlet.HoptoadNotifierFilter&lt;/filter-class>
  &lt;/filter>
  &lt;filter-mapping>
  	&lt;filter-name>HoptoadNotifierFilter&lt;/filter-name>
  	&lt;url-pattern>/*&lt;/url-pattern>
  &lt;/filter-mapping>
</code></pre>

FluxTracker
-----------

[FluxTracker](http://www.fluxtracker.com) is a great new service that combines the error logging capabilities of hoptoad with a lighthouse style issue tracker. I highly recommend it!

To use [FluxTracker](http://www.fluxtracker.com) instead of hoptoad, you can add a "url" parameter to your configuration that points at "www.fluxtracker.com".

Be sure to use your [FluxTracker API Key](https://www.fluxtracker.com/pages/api/tokens) and set the "ssl" parameter to true if you do this!


3rd Party Libraries
-------------------

This project depends on the [XStream library](http://xstream.codehaus.org/) to generate the XML to send to Hoptoad.