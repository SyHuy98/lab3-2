package eiu.cloud;

import java.io.IOException;

import java.io.OutputStream;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

import software.amazon.awssdk.core.ResponseInputStream;

import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.s3.S3Client;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@WebServlet(urlPatterns = { "/image/*" })

public class image extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/jpeg");

		String pathInfo = req.getPathInfo();

		String[] pathParts = pathInfo.split("/");

		String key = pathParts[1];

		String bucketName = "cloudcomp0601";

		AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create("AKIA6PYUN434I65R237A",

				"yPlFGSzzNHyUvhEdlh8CbF8fYPYNRDIud4ePHjR3");

		AwsCredentialsProvider awsCredentialsProvider;

		awsCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

		S3Client s3Client = S3Client.builder().credentialsProvider(awsCredentialsProvider).region(Region.AP_SOUTHEAST_1).build();

		GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(key).build();

		ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);

		OutputStream outputStream = resp.getOutputStream();

		byte[] buffer = new byte[4096];

		int bytesRead = -1;

		while ((bytesRead = response.read(buffer)) != -1) {

			outputStream.write(buffer, 0, bytesRead);

		}

		outputStream.flush();

		response.close();

		outputStream.close();

	}
}

/*
 * function showImageClickHandler() { button.addEventListener('click',
 * 
 * function() {
 * 
 * const xhr = new XMLHttpRequest();
 * 
 * xhr.onload =
 * 
 * function() { let reader = new FileReader(); reader.onloadend =
 * 
 * function() { if (xhr.status == 302) { alert("Unauthorized : " +
 * xhr.responseText); } else if (xhr.status == 200) { var outputImg =
 * document.getElementById(<img-elem-id>); outputImg.src = reader.result;
 * document.body.appendChild(outputImg); } else { alert(xhr.status + " : " +
 * xhr.responseText); } }reader.readAsDataURL(xhr.response); };
 * 
 * xhr.open("GET",<url>+/image/" +
 * key);xhr.responseType='blob';xhr.send(null);},false)}
 */