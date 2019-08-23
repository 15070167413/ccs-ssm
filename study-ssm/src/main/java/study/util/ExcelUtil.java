package study.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import study.controller.UserController;
import study.dto.User;
import study.service.UserService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

@Component
public class ExcelUtil {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);



   /*
     其他一些运用
     // 创建超链接
    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
    link.setAddress("http://www.baidu.com");
    cell = row.createCell(1);
    cell.setCellValue("百度");
    cell.setHyperlink(link);// 设定单元格的链接


     style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("h:mm:ss")); //设置时间数据的格式
    style1.setWrapText(true);// 自动换行
*/



/*
* 实现excel 表格数据导入到数据库
*传入参数是文件地址
* */
    public void ExcelToDatabaseUtil(String Path){
        List<User> list =new LinkedList<User>();

        File file=new File(Path);
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
                System.out.println(user.getName());
                User temp=userService.findUserByName(user.getName());
                System.out.println(temp);
                if(temp==null) {
                    userService.add(user);

                }
                else {
                   userService.edit(user);
                }
            }
        } catch (Exception e) {
            logger.error("存入数据好像出问题了！");
        }






    }


    /*
     * 实现excel 表格数据导入到数据库
     *传入参数是File文件
     * */

    public void ExcelToDatabaseUtil2( File file) throws Exception {
        List<User> list =new LinkedList<User>();
        if(!file.exists()){
            System.out.println("文件不存在");
        }
        else{
            System.out.println(file.getName());
        }
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
                System.out.println(user.getName());
                User temp=userService.findUserByName(user.getName());
                System.out.println(temp);
                if(temp==null) {
                    userService.add(user);

                }
                else {
                    userService.edit(user);
                }
            }
        } catch (Exception e) {
            logger.error("存入数据好像出问题了！");
            throw e;
        }

    }


    /*
     * 将list数据导入到excel 表格
     *传入参数是list
     * */
    public boolean toExcelUtil(List<User> list) {
        list.get(1);
        boolean f = true;
        HSSFWorkbook workbook = null;
        try {
            // 创建 excel 文件
            workbook = new HSSFWorkbook();
            // 创建一个标签页
            HSSFSheet sheet = workbook.createSheet("学生信息");
            //设置列宽
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            sheet.setColumnWidth(2, 3000);
            //创建格式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);//水平居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

            HSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);
            font.setFontName("宋体");
            style.setFont(font);

            //给标题设置格式
            HSSFCellStyle titlestyle = workbook.createCellStyle();
            titlestyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
            titlestyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

            HSSFFont titlefont = workbook.createFont();
            titlefont.setBold(true);
            titlefont.setFontHeightInPoints((short) 25);
            titlefont.setFontName("宋体");
           titlefont.setColor(Font.COLOR_NORMAL);
            titlestyle.setFont(font);


            HSSFRow row=sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
            // 设置单元格内容
            cell.setCellValue("学生信息表");
            cell.setCellStyle(titlestyle);


            //创建第一行
            HSSFRow titleRow = sheet.createRow(1);
            HSSFCell cell0 = titleRow.createCell(0);
            cell0.setCellValue("姓名");
            cell0.setCellStyle(style);

            HSSFCell cell1 = titleRow.createCell(1);
            cell1.setCellValue("年龄");
            cell1.setCellStyle(style);

            HSSFCell cell2 = titleRow.createCell(2);
            cell2.setCellValue("城市");
            cell2.setCellStyle(style);
            //封装数据
            for (int i = 2; i < list.size(); i++) {
                titleRow = sheet.createRow(i );
                titleRow.createCell(0).setCellValue(list.get(i).getName());

                titleRow.createCell(1).setCellValue(list.get(i).getAge());

                titleRow.createCell(2).setCellValue(list.get(i).getCity());

            }
            String Filename = "学生信息.xls";
            String outPath = "C:/Users/ccs/Desktop/my.xls";
            FileOutputStream fos = new FileOutputStream(outPath);
            workbook.write(fos);
            fos.close();

        } catch (Exception e) {
            f=false;
            logger.error("备份到excel出现问题");
            e.printStackTrace();

             }
        logger.info("备份成功了");


        return f;


    }

}

