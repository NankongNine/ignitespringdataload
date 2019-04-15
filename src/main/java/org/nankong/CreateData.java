package org.nankong;

import org.nankong.data.model.Customer;
import org.nankong.data.model.OrgInfo;
import org.nankong.file.FileUtils;

import java.util.*;

public class CreateData {
    private static List<String> orgCodeList = new ArrayList<String>();
//    public static final String CUST_FILE_PATH = "E:/cust.dat";
//    public static final String CUST_FILE_PATH = "/home/nankong/works/ignite/cust.dat";
//    public static final String ORG_FILE_PATH = "E:/org.dat";
//    public static final String ORG_FILE_PATH = "/home/nankong/works/ignite/org.dat";

    public static void main(String args[]){
        String custFilePath = args[0];
        int custFileCount=Integer.parseInt(args[1]);
        String orgFilePath = args[2];
        int orgFileCount=Integer.parseInt(args[3]);
        initOrgList(orgFilePath,orgFileCount);
        StringBuffer strBuf = new StringBuffer();
        for(int i = 1 ;i<=custFileCount;i++){
            Customer cust = genCustomer(String.format("%010d",i));
            strBuf.append(cust.toP9String());
            strBuf.append("\n");
        }
        FileUtils.fileWriter(strBuf.toString(),custFilePath);

    }

    private static void initOrgList(String filePath,int fileCount) {
        Set<String> orgSet = new HashSet<String>();
        StringBuffer strBuf = new StringBuffer();
        for(int i = 0;i<fileCount;i++){
            String orgCode = getRandomIntString(9);
            if(orgSet.contains(orgCode))
                continue;
            orgSet.add(orgCode);
            orgCodeList.add(orgCode);
            OrgInfo orgInfo = genOrgInfo(orgCode);
            strBuf.append(orgInfo.toP9String());
            strBuf.append("\n");
        }
        FileUtils.fileWriter(strBuf.toString(),filePath);
        System.out.println("初始化机构共"+orgCodeList.size()+"条");
    }

    private static OrgInfo genOrgInfo(String orgCode) {
        Random random = new Random();
        return new OrgInfo(getRandomString(10),orgCode,getRandomString(25),randomDate(),random.nextInt(2)+1);
    }

    private static Customer genCustomer(String custId){
        Random random=new Random();
        int orgCodeNum = random.nextInt(orgCodeList.size());
        return new Customer(custId,getRandomString(10),randomDate(),random.nextInt(2),getRandomIntString(16),random.nextInt(2),"CN000",random.nextFloat(),orgCodeList.get(orgCodeNum));

    }

    public static String getRandomString(int length){
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="zxcvbnmlkjhgfdsaqwertyuiop";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(26);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    public static String getRandomIntString(int length){
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str="1234567890";
        //由Random生成随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //长度为几就循环几次
        for(int i=0; i<length; ++i){
            //产生0-61的数字
            int number=random.nextInt(10);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    public static String randomDate(){
        Random rndYear=new Random();
        int year=rndYear.nextInt(18)+2000;  //生成[2000,2017]的整数；年
        Random rndMonth=new Random();
        int month=rndMonth.nextInt(12)+1;   //生成[1,12]的整数；月
        Random rndDay=new Random();
        int Day=rndDay.nextInt(30)+1;       //生成[1,30)的整数；日
        return year+"-"+String.format("%02d",month)+"-"+String.format("%02d",Day);
    }
}
