package com.anlv.prevention.assistant.app.factory;

import android.annotation.SuppressLint;

import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * <pre>
 *     author : tianwei
 *     e-mail : tianwei@anlv365.com
 *     time   : 2019/12/10
 *     desc   : 证书校验。
 * </pre>
 */
public class SSLSocketFactoryImpl extends SSLSocketFactory {

    private SSLContext sslContext = SSLContext.getInstance("TLS");
    private TrustManager trustManager = null;


    public SSLContext getSSLContext() {
        return sslContext;
    }

    public X509TrustManager getTrustManager() {
        return (X509TrustManager) trustManager;
    }

    @SuppressLint("TrustAllX509TrustManager")
    public SSLSocketFactoryImpl(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException {
        trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        sslContext.init(null, new TrustManager[]{trustManager}, null);
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) {
        return null;
    }

    @Override
    public Socket createSocket(String host, int port) {
        return null;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) {
        return null;
    }

    @Override
    public Socket createSocket(InetAddress host, int port) {
        return null;
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) {
        return null;
    }
}
