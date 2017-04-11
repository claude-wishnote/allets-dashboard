package com.allets.backend.data.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;


public class DownloadView extends AbstractView {

	private static final Logger log = LoggerFactory
            .getLogger(DownloadView.class);
	
	
	public void DownloadView(){
        super.setContentType("application/octet-stream");
    }

	/*
	 * ---------------------------------------------------------------------
	 * public & interface method.
	 * ---------------------------------------------------------------------
	 */

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        
//		File file = (File)model.get("file");
		
		DownloadObject downloadObject = (DownloadObject)model.get("file");
		String displayFileName = downloadObject.getDisplayFileName();
		File file = (File)downloadObject.getDownloadFile();
  
        response.setContentType(getContentType());
        response.setContentLength((int)file.length());
 
        String userAgent = request.getHeader("User-Agent");
 
        if (userAgent.contains("Trident")||(userAgent.indexOf("MSIE")>-1)) {
            log.info("user-agent:IE under 10 : {}", userAgent);
            displayFileName=URLEncoder.encode(displayFileName, "UTF-8").replaceAll("\\+", "%20");
        } else if(userAgent.contains("Chrome")) {
        	log.info("user-agent:Chrome : {}", userAgent);
        	displayFileName = new String(displayFileName.getBytes("UTF-8"), "ISO-8859-1");
        } else if(userAgent.contains("Opera")) {
        	log.info("user-agent:Opera : {}", userAgent);
        	displayFileName = new String(displayFileName.getBytes("UTF-8"), "ISO-8859-1");
        } else if(userAgent.contains("Firefox")) {
        	log.info("user-agent:Firefox : {}", userAgent);
        	displayFileName = new String(displayFileName.getBytes("UTF-8"), "ISO-8859-1");
        }
         
        response.setHeader("Content-Disposition", "attachment; filename=\"" + displayFileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
 
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;
  
        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            if(fis != null){
                try{
                    fis.close();
                }catch(Exception e){}
            }
        }
        out.flush();
    }
	
	/*
	 * ---------------------------------------------------------------------
	 * private method.
	 * ---------------------------------------------------------------------
	 */

}
