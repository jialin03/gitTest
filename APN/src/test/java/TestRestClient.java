import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.altomni.apn.model.AuthTokenInfo;
import com.altomni.apn.model.User;

public class TestRestClient {
	private static final String username = "yisu";
	private static final String password = "yisu123";
	private static final String REST_SERVICE_URI = "http://localhost:8080/APN";
	private static final String AUTH_SERVER_URI = REST_SERVICE_URI + "/oauth/token";
	private static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=" + username + "&password="
			+ password;
	private static final String QPM_ACCESS_TOKEN = "?access_token=";
	private static AuthTokenInfo tokenInfo = null;

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	private static HttpHeaders getHeadersWithClientCredentials() {
		String plainClientCredentials = "my-trusted-client:secret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}

	@SuppressWarnings({ "unchecked" })
	private static AuthTokenInfo sendTokenRequest() {
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<Object> response = null;
		try {
			response = restTemplate.exchange(AUTH_SERVER_URI + QPM_PASSWORD_GRANT, HttpMethod.POST, request,
					Object.class);
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
		}
		AuthTokenInfo tokenInfo = null;

		if (null != response) {
			if (response.getStatusCode().is2xxSuccessful()) {
				LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();

				if (map != null) {
					tokenInfo = new AuthTokenInfo();
					tokenInfo.setAccess_token((String) map.get("access_token"));
					tokenInfo.setToken_type((String) map.get("token_type"));
					tokenInfo.setRefresh_token((String) map.get("refresh_token"));
					tokenInfo.setExpires_in((int) map.get("expires_in"));
					tokenInfo.setScope((String) map.get("scope"));
					System.out.println("Token-info:" + tokenInfo);
				} else {
					System.out.println("No user found----------");

				}
			} else {
				System.err.println(response.getBody());
			}
		}
		return tokenInfo;
	}

	private static void listAllUsers() {
		Assert.notNull(tokenInfo, "Authenticate first......");

		System.out.println("\nTesting listAllUsers API-----------");
		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		@SuppressWarnings("rawtypes")
		ResponseEntity<List> response = null;
		try {
			response = restTemplate.exchange(
					REST_SERVICE_URI + "/user/" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), HttpMethod.GET,
					request, List.class);
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
		}
		if (null != response) {
			if (response.getStatusCode().is2xxSuccessful()) {
				@SuppressWarnings("unchecked")
				List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>) response.getBody();

				if (usersMap != null) {
					for (LinkedHashMap<String, Object> map : usersMap) {
						System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age="
								+ map.get("age") + ", Salary=" + String.format("%1$,.2f", map.get("salary")));
						;
					}
				} else {
					System.out.println("No user found----------");
				}
			} else {
				System.err.println(response.getBody());
			}
		}
	}

	private static void getUser(String id) {
		Assert.notNull(tokenInfo, "Authenticate first......");
		System.out.println("\nTesting getUser API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<User> response = null;
		try {
			response = restTemplate.exchange(
					REST_SERVICE_URI + "/user/" + id + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), HttpMethod.GET,
					request, User.class);
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
		}
		if (null != response) {
			if (response.getStatusCode().is2xxSuccessful()) {
				User user = response.getBody();
				System.out.println("User :" + user.toString());
			} else {
				System.err.println(response.getBody());
			}
		}
	}

	private static void createUser(User user) {
		Assert.notNull(tokenInfo, "Authenticate first......");
		System.out.println("\nTesting create User API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
		URI uri = null;
		try {
			uri = restTemplate.postForLocation(
					REST_SERVICE_URI + "/user/" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), request, User.class);
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
		}
		if (null != uri) {
			System.out.println("Created User Id : " + uri.toASCIIString());
		}
	}

	private static void updateUser(User user) {
		Assert.notNull(tokenInfo, "Authenticate first......");
		System.out.println("\nTesting update User API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
		ResponseEntity<User> response = null;
		try {
			response = restTemplate.exchange(
					REST_SERVICE_URI + "/user/" + user.getId() + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
					HttpMethod.PUT, request, User.class);
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
		}
		if (null != response) {
			if (response.getStatusCode().is2xxSuccessful()) {
				System.out.println("Updated Response :" + response.getBody());
			} else {
				System.err.println(response.getBody());
			}
		}
	}

	private static void mergeUser(User user) {
		Assert.notNull(tokenInfo, "Authenticate first......");
		System.out.println("\nTesting merge User API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		restTemplate.setRequestFactory(requestFactory);
		HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
		ResponseEntity<User> response = null;
		try {
			response = restTemplate.exchange(
					REST_SERVICE_URI + "/user/" + user.getId() + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
					HttpMethod.PATCH, request, User.class);
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
		}
		if (null != response) {
			if (response.getStatusCode().is2xxSuccessful()) {
				System.out.println("Merged Response :" + response.getBody());
			} else {
				System.err.println(response.getBody());
			}
		}
	}

	private static void deleteUser(String id) {
		Assert.notNull(tokenInfo, "Authenticate first......");
		System.out.println("\nTesting delete User API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<User> response = null;
		try {
			response = restTemplate.exchange(
					REST_SERVICE_URI + "/user/" + id + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(),
					HttpMethod.DELETE, request, User.class);
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
		}
		if (null != response) {
			if (response.getStatusCode().is2xxSuccessful()) {
				System.out.println("Deleted User with ID 3");
			} else {
				System.err.println(response.getBody());
			}
		}
	}

	private static void deleteAllUsers() {
		Assert.notNull(tokenInfo, "Authenticate first......");
		System.out.println("\nTesting all delete Users API----------");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		ResponseEntity<User> response = null;
		try {
			response = restTemplate.exchange(
					REST_SERVICE_URI + "/user/" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), HttpMethod.DELETE,
					request, User.class);
		} catch (RestClientException e) {
			System.err.println(e.getMessage());
		}
		if (null != response) {
			if (response.getStatusCode().is2xxSuccessful()) {
				System.out.println("Deleted All Users");
			} else {
				System.err.println(response.getBody());
			}
		}
	}

	public static void main(String args[]) {
		tokenInfo = sendTokenRequest();

		String id = UUID.randomUUID().toString();

		User user = new User(id, "Albert", 27, 150000.00);
		createUser(user);

		listAllUsers();

		getUser(id);

		user.setAge(30);
		user.setSalary(200000.00);
		updateUser(user);
		listAllUsers();

		user.setName(null);
		user.setAge(50);
		user.setSalary(null);
		mergeUser(user);
		listAllUsers();

		deleteUser(id);
		listAllUsers();

		id = UUID.randomUUID().toString();
		user = new User(id, "glen", 40, 300000.00);
		createUser(user);
		listAllUsers();
		deleteAllUsers();
		listAllUsers();
	}
}