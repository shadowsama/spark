package com.shadow.jvm.concurent;

public class DirtyRead {

    private String username = "shadow";
    private String password = "123";

    public synchronized void setValue(String username, String password){
        this.username = username;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.password = password;

        System.out.println("setValue最终结果：username = " + username + " , password = " + password);
    }

    /**
     *  synchronized 防止脏读，set和get都加对象锁，表明一个对象只能同时执行一个获得的锁放的方法
     */
    public void getValue(){
        System.out.println("getValue方法得到：username = " + this.username + " , password = " + this.password);
    }


    public static void main(String[] args) throws Exception{

        final DirtyRead dr = new DirtyRead();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                dr.setValue("z3", "456");
            }
        });
        t1.start();
        Thread.sleep(1000);

        dr.getValue();
    }



}

