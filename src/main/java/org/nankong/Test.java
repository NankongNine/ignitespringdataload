package org.nankong;

public class Test {
    public static  void main(String args[]){
        run();
    }
    private static  void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
