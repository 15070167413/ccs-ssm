package study.controller;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import study.dto.Result;
import study.dto.User;
import study.service.UserService;
import study.util.ExcelUtil;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ExcelController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExcelUtil excelUtil;

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);
    /*
    * 把数据库数据拷贝到excel表里
    * */
    @RequestMapping("/copy")
    public String copyToExcel(){
        List<User> list=userService.findUsers();
        excelUtil.toExcelUtil(list);
        if(excelUtil.toExcelUtil(list))
            return "ok";
        return "fail";
    }



    /*
    * 将excel表的数据更新到数据库中
    * */
    @RequestMapping("/copyToDatabase")
    public String copyToDatabase(){
        String Path="C:/Users/ccs/Desktop/my.xls";
        excelUtil.ExcelToDatabaseUtil(Path);
        return "redirect:/list";
    }



/*
实现页面的跳转
*
**/
    @RequestMapping("/chooseFile")
    public String chooseFile(){
        return "fileUpload";
    }





    //通过页面选择文件实现选择文件进行数据存储
    /*
    * 流程：先获取MultipartFile
    * 然后构建需要存放文件的路径
    * 使用   file.transferTo(f);
    * 把MultipartFile file 转换成 File f文件
    * */
    @ResponseBody
    @RequestMapping(value = "/copyToDatabasebyfile",method = RequestMethod.POST)
    public Result copyToDatabase2(@RequestParam("myfile") MultipartFile file) throws IOException {
        Result result=new Result();
        String deposeFilesDir = "C:\\Users\\ccs\\Desktop\\服务器文件\\";
        // 判断文件手否有内容
        if (file.isEmpty()) {
            result.setState(false);
            result.setMsg("该文件无任何内容!!!，请重新选择！");
        }
        // 获取附件原名(有的浏览器如chrome获取到的是最基本的含 后缀的文件名,如myImage.png)
        // 获取附件原名(有的浏览器如ie获取到的是含整个路径的含后缀的文件名，如C:\\Users\\images\\myImage.png)
        String fileName = file.getOriginalFilename();


        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        int index = fileName.lastIndexOf("\\");
            if (index > 0) {
            fileName = fileName.substring(index + 1);
        }
        // 判断单个文件大于1M
        long fileSize = file.getSize();
        if (fileSize > 1024 * 1024) {

            result.setState(false);
            result.setMsg("文件大小为(单位字节):" + fileSize+"该文件大于1M");
        }
        // 当文件有后缀名时
        if (fileName.indexOf(".") >= 0) {
            // split()中放正则表达式; 转义字符"\\."代表 "."
            String[] fileNameSplitArray = fileName.split("\\.");
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileNameSplitArray[0] + (int) (Math.random() * 10) + "." + fileNameSplitArray[1];

        if(fileNameSplitArray[1]!="xls"){
            result.setState(false);
            result.setMsg("选择的文件不是excel文件，请重新选择！");
        }
        }
        // 当文件无后缀名时(如C盘下的hosts文件就没有后缀名)
        if (fileName.indexOf(".") < 0) {
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileName + (int) (Math.random() * 10);
        }


        // 根据文件的全路径名字(含路径、后缀),new一个File对象dest
        File f = new File(deposeFilesDir + fileName);
        // 如果该文件的上级文件夹不存在，则创建该文件的上级文件夹及其祖辈级文件夹;
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
            file.transferTo(f);
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
      //  System.out.println("文件的全路径名字(含路径、后缀)>>>>>>>" + deposeFilesDir + fileName);

       if(!f.exists()){
            System.out.println(f.getName());
           result.setState(false);
           result.setMsg("文件上传失败");
        }
        else {
           result.setState(true);
           result.setMsg("导入成功！");
        }
        return result;
    }
/*
* 具体实现将数据导出到excel表中*/

    /**
     *
     * @param response
     * @return
     */
    @RequestMapping("/toexcle")
    public String toExcel(HttpServletResponse response){
        boolean f=true;
        ServletOutputStream outputStream = null;
        HSSFWorkbook workbook = null;
        try{
            // 创建 excel 文件
            workbook=new HSSFWorkbook();
            // 创建一个标签页
            HSSFSheet sheet=workbook.createSheet("学生信息");
            //设置列宽
            sheet.setColumnWidth(0,3000);
            sheet.setColumnWidth(1,3000);
            sheet.setColumnWidth(2,3000);
            //创建格式
            HSSFCellStyle style=workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);//水平居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

            HSSFFont font=workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short)12);
            font.setFontName("宋体");
            style.setFont(font);
            //创建标题行
            HSSFRow titleRow =sheet.createRow(0);
            HSSFCell cell0=titleRow.createCell(0);
            cell0.setCellValue("姓名");
            cell0.setCellStyle(style);


            HSSFCell cell1=titleRow.createCell(1);
            cell1.setCellValue("年龄");
            cell1.setCellStyle(style);

            HSSFCell cell2=titleRow.createCell(2);
            cell2.setCellValue("城市");
            cell2.setCellStyle(style);
            //查询数据
            List<User> list=userService.findUsers();
            //封装数据
            for(int i=0;i<list.size();i++){
                titleRow=sheet.createRow(i+1);
                titleRow.setRowStyle(style);
                titleRow.createCell(0).setCellValue(list.get(i).getName());
                titleRow.createCell(1).setCellValue(list.get(i).getAge());
                titleRow.createCell(2).setCellValue(list.get(i).getCity());
            }
            String Filename="学生信息.xls";
            String outPath="C:/Users/ccs/Desktop/my.xls";
            FileOutputStream fos = new FileOutputStream(outPath);
            workbook.write(fos);
            fos.close();

        }catch (Exception e){
            logger.error("excel导出失败");
            f=false;
            e.printStackTrace();

        }

        if(f){
            return "ok";
        }
        return "fail";

    }


    /*
     * 具体实现将excel表数据导出到list中*/
    @RequestMapping("/excelTo")
    public String excleTo(Model model){
        List<User> list =new LinkedList<User>();

        String inPath="C:/Users/ccs/Desktop/my.xls";
        File file=new File(inPath);
        try {
            HSSFWorkbook workbook= new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet=workbook.getSheetAt(0);
            Object value = null;
            HSSFRow row = null;
            HSSFCell cell = null;
            for(int i=sheet.getFirstRowNum()+2;i<=sheet.getLastRowNum();i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                User user =new User();
                cell = row.getCell(0);
                user.setName(cell.getStringCellValue());
                cell = row.getCell(1);
                user.setAge((int)cell.getNumericCellValue());
                cell = row.getCell(2);
                user.setCity(cell.getStringCellValue());
                list.add(user);
            }
        } catch (Exception e) {
            logger.error("好像出问题了！");
        }
        //System.out.println(list.toString());

        model.addAttribute("list",list);
        return "user1";


    }

}
