/*
 * Copyright (C) 2011 by Stuart Garner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.radialaspect.hoptoad.notifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import com.radialaspect.hoptoad.schema.Notice;
import com.thoughtworks.xstream.XStream;

public class DefaultHoptoadNotifier implements HoptoadNotifier {
    private HoptoadConnection connection;

    public DefaultHoptoadNotifier(final String url, final boolean secure) {
        setConnection(new HoptoadConnection(url, secure));
    }

    public HoptoadConnection getConnection() {
        return connection;
    }

    public void setConnection(final HoptoadConnection connection) {
        this.connection = connection;
    }

    @Override
    public void setSecure(final boolean secure) {
        getConnection().setSecure(secure);
    }

    @Override
    public String notify(final Notice notice) {
        HttpURLConnection connection = null;

        try {
            connection = open();
            sendNotice(connection, notice);
            final String response = getResponse(connection);

            checkStatus(connection, response);

            return response;
        } catch (final HoptoadException e) {
            throw e;
        } catch (final Exception e) {
            throw new HoptoadException(e);
        } finally {
            cleanup(connection);
        }
    }

    private HttpURLConnection open() throws IOException, MalformedURLException {
        final HttpURLConnection connection = getConnection().connect();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-type", "text/xml");
        connection.setRequestProperty("Accept", "text/xml, application/xml");
        connection.setRequestMethod("POST");

        return connection;
    }

    private void sendNotice(final HttpURLConnection connection, final Notice notice) throws IOException {
        final XStream stream = new XStream();
        stream.autodetectAnnotations(true);

        final String noticeXML = stream.toXML(notice);

        final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(noticeXML);
        writer.flush();
        writer.close();
    }

    private void checkStatus(final HttpURLConnection connection, final String response) throws IOException {
        final int statusCode = connection.getResponseCode();

        if (statusCode == HttpURLConnection.HTTP_FORBIDDEN) {
            throw new SSLNotSupportedException(statusCode, response);
        }

        if (statusCode != HttpURLConnection.HTTP_OK) {
            throw new HoptoadException(statusCode, response);
        }
    }

    private String getResponse(final HttpURLConnection connection) throws IOException {
        final StringBuilder response = new StringBuilder(2048);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        } finally {
            reader.close();
        }
    }

    private void cleanup(final HttpURLConnection connection) {
        try {
            connection.disconnect();
        } catch (final Exception e) {
            // ignore exception
        }
    }
}
