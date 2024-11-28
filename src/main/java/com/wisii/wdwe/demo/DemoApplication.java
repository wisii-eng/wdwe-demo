package com.wisii.wdwe.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wisii.component.startUp.SystemUtil;
import com.wisii.font.util.FontConfigUtil;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        // 生成字体配置文件
        String classpath = SystemUtil.getClassesPath();
        System.out.println(classpath);
		if (classpath != null) {
			File userConfigFile = new File(classpath + "renderconfig.xml");
			if(!userConfigFile.exists()) {
				boolean configPDFFont = FontConfigUtil.configPDFFont(classpath +"/font", classpath);
				if(!configPDFFont) {
					System.out.println("renderconfig.xml生成失败！");
				}
			}
		}
		SpringApplication.run(DemoApplication.class, args);
    }

}
