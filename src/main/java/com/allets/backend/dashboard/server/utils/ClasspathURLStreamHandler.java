package com.allets.backend.dashboard.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * 현재 쓰레드에서 사용하는 클래스로더 와 URL 객체를 생성한다.
 */
public class ClasspathURLStreamHandler extends URLStreamHandler {

	/** The Constant logger. */
	private static final Logger LOG = LoggerFactory
			.getLogger(ClasspathURLStreamHandler.class);

	// ~ Instance fields. ~~~~~~~~~~~~~~
	/**
	 * 클래스 패스 prefix.
	 */
	public static final String CLASSPATH_PREFIX = "classpath";

	/** 클래스로더. */
	private ClassLoader classLoader;

	// ~ Constructors. ~~~~~~~~~~~~~~~~~
	/**
	 * 기본 생성자.
	 */
	public ClasspathURLStreamHandler() {
		LOG.info("ClasspathURLStreamHandler");
		this.classLoader = Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 생성자.
	 * 
	 * @param loader
	 *            the loader
	 */
	public ClasspathURLStreamHandler(final ClassLoader loader) {
		if (classLoader == null) {
			this.classLoader = Thread.currentThread().getContextClassLoader();
		} else {
			this.classLoader = loader;
		}
	}

	// ~ Implementation Method. ~~~~~~~~
	// ~ Self Methods. ~~~~~~~~~~~~~~~~~

	/**
	 * 현재 클래스 로더를 리턴 한다.
	 * 
	 * @return the class loader
	 */
	public final ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * 해당 URL의 커넥션을 생성 한다.
	 * 
	 * @param url
	 *            the url
	 * @return URLConnection
	 * @throws java.io.IOException
	 *             the IO exception
	 */
	protected final URLConnection openConnection(final URL url) throws IOException {
		String protocol = url.getProtocol();
		if (CLASSPATH_PREFIX.equals(protocol)) {
			String path = url.getPath();
			while (path.startsWith("/")) {
				path = path.substring(1);
			}
			
			URL resUrl = getClassLoader().getResource(path);
			if (resUrl == null) {
				throw new IllegalArgumentException("Classpath resource: "
						+ path + " not found.");
			}
			return resUrl.openConnection();
		} else {
			// Use default JDK url impl.
			try {
				return new URL(url.getPath()).openConnection();
			} catch (MalformedURLException e) {
				// Try again with simple File path location.
				File file = new File(url.getPath());
				return file.toURI().toURL().openConnection();
			}
		}
	}

	/**
	 * URL 오브젝트를 생성 한다.
	 * 
	 * @param url
	 *            the url
	 * @return the uRL
	 */
	public static URL createURL(final String url) {
		return createURL(url, ClasspathURLStreamHandler.class.getClassLoader());
	}

	/**
	 * URL 오브젝트를 생성 한다.
	 * 
	 * @param url
	 *            the url
	 * @param classLoader
	 *            the class loader
	 * @return the uRL
	 */
	public static URL createURL(final String url, final ClassLoader classLoader) {
		URL context = null;
		URL urlObj = null;
		try {
			urlObj = new URL(context, url, new ClasspathURLStreamHandler(
					classLoader));
		} catch (MalformedURLException e) {
			// Retry with file:// protocol.
			try {
				urlObj = new File(url).toURI().toURL();
			} catch (MalformedURLException e2) {
				throw new IllegalArgumentException("Failed to parse " + url, e); 
			}
		}
		return urlObj;
	}
	// ~ Getter and Setter. ~~~~~~~~~~~~
}
