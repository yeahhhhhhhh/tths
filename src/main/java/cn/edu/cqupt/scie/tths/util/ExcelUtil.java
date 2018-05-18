package cn.edu.cqupt.scie.tths.util;

import cn.edu.cqupt.scie.tths.model.UserModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by why on 2017/4/13.
 */
public class ExcelUtil {
    public static List<UserModel> readExcel(MultipartFile file){
        List<UserModel> userModelList = new ArrayList<UserModel>();
        try {
            if(file == null){
                return null;
            }
            String fileName1 = file.getOriginalFilename();
            String fileType = fileName1.substring(fileName1.lastIndexOf(".")+1,fileName1.length());
            System.out.println(fileName1.substring(fileName1.lastIndexOf(".")+1,fileName1.length()));
            Workbook wb = null;
            if(fileType.equalsIgnoreCase("xls")){
                wb = new HSSFWorkbook(file.getInputStream());
            }else if(fileType.equalsIgnoreCase("xlsx")){
                wb = new XSSFWorkbook(file.getInputStream());
            }else {
                return null;
            }
            /** 得到第一个shell */
            Sheet sheet = wb.getSheetAt(0);
            /** 得到Excel的行数 */
            int totalRows = sheet.getPhysicalNumberOfRows();
            System.out.println("totalRows:"+totalRows);
            for(int i = 1; i<=totalRows; i++){
                Row row = sheet.getRow(i);
                if(row == null){
                    continue;
                }
                String name = row.getCell(0).getStringCellValue()+"";
                String email = row.getCell(1).getStringCellValue()+"";
                String py_name = ChineseToEnglish.getPingYin(name);
                UserModel userModel = new UserModel();
                userModel.setUsername(name);
                userModel.setEmail(email);
                userModel.setPyName(py_name.toLowerCase());
                userModelList.add(userModel);
                System.out.println(name);
                System.out.println(py_name);
            }
            return userModelList;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}
