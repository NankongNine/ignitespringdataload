package org.nankong.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static int bufSize =1024;

    public static void fileWriter(String content, String filePath){
        String filename = filePath;
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(new File(filename));
            FileChannel channel = fos.getChannel();
            ByteBuffer src = Charset.forName("utf8").encode(content);
            // 字节缓冲的容量和limit会随着数据长度变化，不是固定不变的
            System.out.println("初始化容量和limit：" + src.capacity() + ","
                    + src.limit());
            int length = 0;

            while ((length = channel.write(src)) != 0) {
                /*
                 * 注意，这里不需要clear，将缓冲中的数据写入到通道中后 第二次接着上一次的顺序往下读
                 */
                System.out.println("写入长度:" + length);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static List<String> fileReader(String filePath) throws IOException {
        List<String> contentLsit = new ArrayList<String>();
        String enterStr = "\n";
        File fin = new File(filePath);
        FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
        ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);

        try{
            byte[] bs = new byte[bufSize];

            int size = 0;
            StringBuffer strBuf = new StringBuffer("");
            //while((size = fcin.read(buffer)) != -1){
            while(fcin.read(rBuffer) != -1){
                int rSize = rBuffer.position();
                rBuffer.rewind();
                rBuffer.get(bs);
                rBuffer.clear();
                String tempString = new String(bs, 0, rSize);
                //System.out.print(tempString);
                //System.out.print("<200>");

                int fromIndex = 0;
                int endIndex = 0;
                while((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1){
                    String line = tempString.substring(fromIndex, endIndex);
                    line = new String(strBuf.toString() + line);

                    contentLsit.add(line);

                    strBuf.delete(0, strBuf.length());
                    fromIndex = endIndex + 1;
                }
                if(rSize > tempString.length()){
                    strBuf.append(tempString.substring(fromIndex, tempString.length()));
                }else{
                    strBuf.append(tempString.substring(fromIndex, rSize));
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contentLsit;
    }
    public static void main(String args[]) throws IOException {
        List<String> list = fileReader("/home/nankong/works/ignite/cust.dat");
        System.out.println(list.size());
    }
}
