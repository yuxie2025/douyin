package com.yuxie.demo.sy;

import com.blankj.utilcode.util.FileIOUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

	public static String post(String url, String params) {
		return post(url, params, "");
	}

	/**
	 * post 请求
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String params, String token) {

		String str = "";
		URL realurl = null;
		InputStream in = null;
		HttpURLConnection conn = null;
		PrintWriter pw = null;
		try {
			realurl = new URL(url);
			conn = (HttpURLConnection) realurl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Linux; Android 7.0; MI 5s Plus Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/64.0.3282.137 Mobile Safari/537.36;android_app");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			conn.setRequestProperty("X-Requested-With", "com.taimukeji.taimu");
			if (token != "") {
				conn.setRequestProperty("token", token);
			}
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(5 * 1000);
			conn.setRequestMethod("POST");
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(params.getBytes("UTF-8"));
			out.flush();
			out.close();

			// 获取响应码要在，获取流之后
			int responseCode = conn.getResponseCode();
			if (responseCode == 200 || responseCode == 201) {
				in = conn.getInputStream();
				str = streamToString(in);

				return str;
			}
			if (responseCode == 503) {
				str = "异常，服务不可用!";
				return str;
			}

		} catch (IOException e) {
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
			if (pw != null) {
				pw.close();
				pw = null;
			}
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return str;
	}

	/**
	 * get请求
	 *
	 * @param uri
	 * @return
	 */
	public static String getDataNet(String uri, String token) {
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			// 2)根据url,获得HttpURLConnection 连接工具
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("contentType", "utf-8");
			if (token != "") {
				conn.setRequestProperty("token", token);
			}
			// 3)很据连接工具，获得响应码
			int responseCode = conn.getResponseCode();
			InputStream inStream = conn.getInputStream();
			// 4)响应码正确，即可获取、流、文件长度...
			if (responseCode == 200) {
				result = streamToString(inStream);
			}
		} catch (MalformedURLException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return result;
	}

	public static String putDataNet(String uri) {
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			// 2)根据url,获得HttpURLConnection 连接工具
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setUseCaches(false);
			// 3)很据连接工具，获得响应码
			int responseCode = conn.getResponseCode();
			InputStream inStream = conn.getInputStream();
			// 4)响应码正确，即可获取、流、文件长度...
			if (responseCode == 200) {
				result = streamToString(inStream);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return result;
	}

	public static String delete(String uri) {
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			// 2)根据url,获得HttpURLConnection 连接工具
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setUseCaches(false);
			// 3)很据连接工具，获得响应码
			int responseCode = conn.getResponseCode();
			InputStream inStream = conn.getInputStream();
			// 4)响应码正确，即可获取、流、文件长度...
			if (responseCode == 200) {
				result = streamToString(inStream);
			} else {
				System.out.println("responseCode:" + responseCode);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return result;
	}

	/**
	 * 流转换为字符串
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String streamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(in,
				"UTF-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			out.append(line);
		}
		br.close();
		return out + "";
	}

	public static boolean downPicture(String uri, String filePath) {

		boolean result = false;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(uri);
			// 2)根据url,获得HttpURLConnection 连接工具
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "okhttp/3.8.1");
			conn.setRequestProperty("contentType", "utf-8");
			// 3)很据连接工具，获得响应码
			int responseCode = conn.getResponseCode();
			InputStream inStream = conn.getInputStream();
			// 4)响应码正确，即可获取、流、文件长度
			if (responseCode == 200) {
				result = FileIOUtils.writeFileFromIS(filePath, inStream);
			}
			inStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return result;
	}
}