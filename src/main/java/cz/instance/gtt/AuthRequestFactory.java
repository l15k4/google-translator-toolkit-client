package cz.instance.gtt;

import java.io.IOException;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin.Response;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.xml.atom.AtomParser;

public class AuthRequestFactory {

	public final String AUTH_TOKEN_TYPE = "gtrans";
	public final String ACCOUNT_TYPE = "GOOGLE";
	public final String API_NAME = "Google-Translator-Toolkit-client/1.0";
	public final String GDATA_VERSION = "1";
	public String clientId;
	public String passwd;
	public String appName;
	public String authHeader;
	public HttpTransport transport;
	public HttpRequestFactory requestFactory;
	
	public AuthRequestFactory(String email, String password, String applicationName) {
		this.clientId = email;
		this.passwd = password;
		this.appName = applicationName;
	}

	public GttClient getClient() {
		requestFactory = requestFactory != null ? requestFactory : createRequestFactory();
		GenericRequestExecutor regExecutor = new GenericRequestExecutor(requestFactory);
		EntryBuilder ebuilder = new EntryBuilder();
		return new GttClient(regExecutor, ebuilder);
	}

	private Response authenticate() {
		ClientLogin clientLogin = new ClientLogin();
		clientLogin.transport = getTransport();
		clientLogin.authTokenType = AUTH_TOKEN_TYPE;
		clientLogin.accountType = ACCOUNT_TYPE;
		clientLogin.applicationName = appName;
		clientLogin.username = clientId;
		clientLogin.password = passwd;
		Response response;
		try {
			response = clientLogin.authenticate();
		} catch (Exception e) {
			throw new Error("Authentication failed", e);
		}
		return response;
	}

	private String getAuthToken() {
		authHeader = authHeader != null ? authHeader : authenticate().getAuthorizationHeaderValue();
		return authHeader;
	}

	private String refreshToken() {
		authHeader = authenticate().getAuthorizationHeaderValue();
		return authHeader;
	}

	private HttpTransport getTransport() {
		transport = transport != null ? transport : new ApacheHttpTransport();
		return transport;
	}
	
	private HttpRequestFactory createRequestFactory() {
		final RefreshTokenHandler handler = new RefreshTokenHandler();
		return getTransport().createRequestFactory(new HttpRequestInitializer() {
			public void initialize(HttpRequest request) {
				new RequestDefaults().initialize(request);
				request.setUnsuccessfulResponseHandler(new HttpUnsuccessfulResponseHandler() {
					public boolean handleResponse(HttpRequest request, HttpResponse response, boolean retrySupported) throws IOException {
						return handler.handleResponse(request, response, retrySupported);
					}
				});
			}
		});
	}
	
	class RequestDefaults implements HttpRequestInitializer {
		public void initialize(HttpRequest request) {
			GoogleHeaders headers = new GoogleHeaders();
			headers.setAuthorization(getAuthToken());
			headers.setApplicationName(API_NAME);
			headers.gdataVersion = GDATA_VERSION;
			request.setConnectTimeout(0);
			request.setReadTimeout(0);
			request.addParser(new AtomParser(RequestCallback.GTT_DICTIONARY));
			request.setHeaders(headers);
		}
	}

	class RefreshTokenHandler implements HttpUnsuccessfulResponseHandler {
		public boolean handleResponse(HttpRequest request, HttpResponse response, boolean retrySupported) throws IOException {
			if (response.getStatusCode() == 401) {
				refreshToken();
				return true;
			}
			return false;
		}
	}
}
