package de.ude.openlap.xapi.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.springframework.stereotype.Service;

@Service("getXapiStatments")
public class XapiStatementsImp implements XapiStatements {

	@Override
	public String getStatementsByQuery(String query) {
		// TODO Auto-generated method stub
		String statements = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		int statusCode;

		String param1Before = "limit=1200";
		try {
			String param1After = URLEncoder.encode(param1Before, "UTF-8");
			System.out.println(param1After);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = "http://192.168.1.104:8081/data/xAPI/statements/?" + param1Before;
		HttpGet request = new HttpGet(url);
		request.addHeader("content-type", "application/json");
		request.addHeader("Authorization", "Basic NWNhN2Y3MDI0YjZlMjJlOTk4MTk4NGYxOGYwMGYyNGMzMTM4Y2I5ZjowZjRlMWE0OTdjODRhYjJlZjVjYjE4NTUyZTIzN2RjZmI4MTM2NDU2");
		request.addHeader("X-Experience-API-Version", "1.0.0");

		try {
			response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedInputStream bis = new BufferedInputStream(instream);
				ByteArrayBuffer baf = new ByteArrayBuffer(50);
				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
				statements = new String(baf.toByteArray());

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statements;
	}

}
