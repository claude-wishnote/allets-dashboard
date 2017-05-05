package com.allets.backend.dashboard.server.utils;

import java.io.File;

/**
 * 다운로드 전용 Value Object.
 * 
 * <p>
 * 
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 3. 24
 */
public class DownloadObject {

	/*
	 * ---------------------------------------------------------------------
	 * Instance fields.
	 * ---------------------------------------------------------------------
	 */
	
	/* 다운로드 파일 이름 (확장자 포함) 예) a.txt*/
	String displayFileName;
	
	/* 다운로드 대상 파일*/
	File downloadFile;

	public String getDisplayFileName() {
		return displayFileName;
	}

	public void setDisplayFileName(String displayFileName) {
		this.displayFileName = displayFileName;
	}

	public File getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(File downloadFile) {
		this.downloadFile = downloadFile;
	}
	

}
