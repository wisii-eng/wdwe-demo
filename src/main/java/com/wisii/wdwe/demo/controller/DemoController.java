package com.wisii.wdwe.demo.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisii.component.setting.WisiiBean;
import com.wisii.fov.apps.MimeConstants;
import com.wisii.fov.util.WDWEUtil;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public void demo(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String path =  getOutPdfFilePath();
        path = path + "/hello.pdf";
        System.out.println(path);
        String serverURL = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + serverURL + "/";
        WisiiBean bean = new WisiiBean();
        try {
            bean.setOutputMode(MimeConstants.MIME_PDF);
            File outfile = new File(path);
            bean.setOutputfilename(outfile.getAbsolutePath());
            bean.setXsl("<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet xmlns:fo=\"http://www.w3.org/1999/XSL/Format\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:wisii=\"http://www.wisii.com/wisii\" version=\"1.0\"><xsl:output method=\"xml\" encoding=\"UTF-8\" indent=\"no\" version=\"1.0\"/><xsl:template match=\"/\"><fo:root><fo:layout-master-set><fo:simple-page-master master-name=\"946347538\" page-height=\"29.7cm\" page-width=\"21cm\" margin-top=\"15mm\" margin-bottom=\"17.5mm\" margin-left=\"21.7mm\" margin-right=\"21.7mm\"><fo:region-body margin-top=\"10.4mm\" margin-bottom=\"7.9mm\" margin-left=\"10mm\" margin-right=\"10mm\" overflow=\"hidden\" writing-mode=\"lr-tb\"></fo:region-body></fo:simple-page-master></fo:layout-master-set><fo:page-sequence master-reference=\"946347538\" font-family=\"'宋体'\" font-size=\"12pt\" white-space-treatment=\"preserve\" white-space-collapse=\"false\"><fo:flow flow-name=\"xsl-region-body\"><fo:block white-space-treatment=\"preserve\" color=\"rgb(0,0,0,255,0)\" line-height=\"14.4pt\" white-space-collapse=\"false\"><fo:inline font-size=\"16pt\">你好，</fo:inline><xsl:variable name=\"content3\" select=\"root/name\"/><fo:inline font-size=\"16pt\"><xsl:value-of select=\"$content3\"/></fo:inline></fo:block></fo:flow></fo:page-sequence></fo:root></xsl:template></xsl:stylesheet>");
            bean.setXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><name>WiseDoc WebEngine</name></root>");
            WDWEUtil.renderTo(bean);
            response.getWriter().write("<script>window.location ='"+basePath+"hello.pdf'</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String getOutPdfFilePath() {
    	String path =  this.getClass().getResource("/").getPath();
    	if(path.indexOf("jar") > 0) {
    		try {
    			path = ResourceUtils.getURL("").getPath();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    	}
    	File s = new File(path + "static");
    	if(!s.exists()){
    		s.mkdir();
    	}
    	return s.getAbsolutePath();
    }
}
