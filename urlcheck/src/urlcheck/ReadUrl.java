package urlcheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Servlet implementation class ReadUrl
 */
public class ReadUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadUrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String data="";
		try {
			data= getText("URL_TO_CHECK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().write(data);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	public static String getText(String ) throws Exception {
		
		
HttpGet httpget = new HttpGet(url);
        
        
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
        
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
 //               .setProxy(new HttpHost("proxy", 8080))
                .build();
        
        httpget.setConfig(requestConfig);
        
        
        HttpClient httpClient = getHttpClientWithoutSslValidation_UsingHttpClient_4_5_2();
        
        CloseableHttpResponse execute = (CloseableHttpResponse)httpClient.execute(httpget);
        String text="";
        
        try {
           
        	text= EntityUtils.toString(execute.getEntity());
        } finally {
        	execute.close();
        }
		
        return text;
    }
	
	
	 public static HttpClient getHttpClientWithoutSslValidation_UsingHttpClient_4_5_2() {
	        try {
	            SSLContextBuilder builder = new SSLContextBuilder();
	            builder.loadTrustMaterial(null, new TrustStrategy() {
	                @Override
	                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	                    return true;
	                }
	            });
	            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), new NoopHostnameVerifier());
	            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build(); 
	            return httpclient;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }


}
