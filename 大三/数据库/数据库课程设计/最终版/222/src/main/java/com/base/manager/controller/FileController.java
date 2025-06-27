package com.base.manager.controller;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("file")
public class FileController {

    @RequestMapping(value = "/upload")
    @ResponseBody
    public Object upload(@RequestParam(value="file",required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> map=new HashMap<>();
        // 文件名
        String fileName = file.getOriginalFilename();
        // 在file文件夹中创建名为fileName的文件
        assert fileName != null;
        int split = fileName.lastIndexOf(".");
        // 文件后缀，用于判断上传的文件是否是合法的
        String suffix = fileName.substring(split+1,fileName.length());
        //判断文件类型，因为我这边是图片，所以只设置三种合法格式
        String url = "";String newurl="";
        if("jpg".equals(suffix) || "jpeg".equals(suffix) || "png".equals(suffix)) {
            // 正确的类型，保存文件
            try {
                ApplicationHome ah = new ApplicationHome(getClass());
                File filepath = ah.getSource();
                File path = new File(filepath.getPath());
                String uuid = UUID.randomUUID().toString();
                File upload = new File(path.getAbsolutePath(), "/static/images/"+"/"+uuid);
                if(!upload.exists()) {
                    upload.mkdirs();
                }
                File savedFile = new File(upload + "/" + fileName);
                file.transferTo(savedFile);
                url = savedFile.getAbsolutePath();
                int i = url.lastIndexOf("images");
                newurl = url.substring(i-1,url.length());
                newurl = newurl.replaceAll("\\\\","/");
                map.put("url",newurl);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }else {
            //错误的类型，返回错误提示
            return map.put("msg","只能上传jpg/png/jpeg文件，且不超过500kb");
        }
        return map;
    }
}
