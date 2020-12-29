package com.example.wanandroid.performance.net;

import android.content.Context;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.example.wanandroid.util.LogHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

/**
 * @author 52512
 * @date 2020/12/29
 * HttpDns 优化
 */
public class OkHttpDns implements Dns {
	private HttpDnsService dnsService;
	private static OkHttpDns instance = null;
	
	private OkHttpDns(Context context) {
		dnsService = HttpDns.getService(context, "161133");
		// 设置预解析的 IP 使用 Https 请求。
		dnsService.setHTTPSRequestEnabled(true);
		// 预先注册要使用到的域名，以便 SDK 提前解析，减少后续解析域名时请求的时延。
		ArrayList<String> hostList = new ArrayList<>(Arrays.asList("www.wanandroid.com"));
		dnsService.setPreResolveHosts(hostList);
	}
	
	public static OkHttpDns getIns(Context context) {
		if (instance == null) {
			synchronized (OkHttpDns.class) {
				if (instance == null) {
					instance = new OkHttpDns(context);
				}
			}
		}
		return instance;
	}
	
	@Override
	public List<InetAddress> lookup(String hostname) throws UnknownHostException {
		String ip = dnsService.getIpByHostAsync(hostname);
		LogHelper.d("httpDns: " + ip);
		if (ip != null) {
			List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
			return inetAddresses;
		}
		// 如果从阿里云 DNS 服务器获取不到 ip 地址，则走运营商域名解析的过程。
		return Dns.SYSTEM.lookup(hostname);
	}
}
