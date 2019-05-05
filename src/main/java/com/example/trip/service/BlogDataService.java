package com.example.trip.service;

import com.example.trip.bean.Blog;
import com.example.trip.dao.BlogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import java.io.*;

@Service
public class BlogDataService {


    // 目标文件夹
    public final static String des = "C:\\Users\\kidna\\Desktop\\trip\\src\\main\\resources\\static\\img";
    @Autowired
    BlogDao blogDao;
    public HashMap<String, String> findBlogsByUserName(String userName){
        List<Blog> resultList=blogDao.findBlogsByUserName(userName);
        HashMap<String,String> resultMap=new HashMap<>();
        for (int i = 0; i < resultList.size(); i++) {
            resultMap.put(resultList.get(i).getDate(),resultList.get(i).getTitle());
        }
        return resultMap;

    }

    public Blog findBlogByDateAndUserName(String userName,String date){
        Blog result=blogDao.findBlogByDateAndUserName(userName,date);
        return result;

    }

    public String[] divideContent(String content){
        return content.split("\\+\\*");

    }

    public void deleteBlog(String date){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        blogDao.deleteBlog(userDetails.getUsername(),date);

    }

    public boolean addBlog(String title,String userName,String date,String content){
        return blogDao.addBlog(title,userName,date,content);

    }

    public List<Blog> search(String searchContent){
        return blogDao.findSearchedBlogs(searchContent);
    }

    public Blog findBlogById(Integer id){
        return blogDao.findBlogById(id);
    }

    public String uploadImage(String content){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String[] contents=this.divideContent(content);
        for (int i=0;i<contents.length;i++) {

            if (contents[i].startsWith("D:\\")){
                String[] pictureName=contents[i].split("\\\\");
                try {
                    File f=new File(des+ File.separator+userDetails.getUsername());
                    if (!f.exists()){
                        f.mkdir();
                    }
                    copyFile(new File(contents[i]),new File(des+"\\"+userDetails.getUsername()+
                            File.separator+pictureName[pictureName.length-1]));
                } catch (IOException e) {
                    System.out.println("复制文件出错！！");
                }

                String replacement="img\\"+userDetails.getUsername()+File.separator+pictureName[pictureName.length-1];
                content=content.replace(contents[i],replacement);


            }

        }
        return content;


    }




    /**
     * 复制文件
     */

        // 复制文件
        public void copyFile(File sourceFile,File targetFile)
                throws IOException{
            // 新建文件输入流并对它进行缓冲
            FileInputStream input = new FileInputStream(sourceFile);
            BufferedInputStream inBuff=new BufferedInputStream(input);

            // 新建文件输出流并对它进行缓冲
            FileOutputStream output = new FileOutputStream(targetFile);
            BufferedOutputStream outBuff=new BufferedOutputStream(output);

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len =inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();

            //关闭流
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();
        }

}
