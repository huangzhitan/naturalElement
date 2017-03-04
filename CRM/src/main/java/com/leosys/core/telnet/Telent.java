/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.leosys.core.telnet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import org.apache.commons.net.telnet.TelnetClient;

/**
 * @author 郑常亮 <zcl@leosys.com>
 * @Copyright Copyright (C) leosys  http://www.leosys.com
 * @date 2015-4-27 13:08:29
 */
public class Telent {
    
    Object lock = new Object();  
    TelnetClient telnet = null;  
    String hostname;  
    int hostport = 23;  
    String user;  
    String password;  
    private InputStream in;  
    private PrintStream out;  
    private static final String ORIG_CODEC = "ISO8859-1";  
    private static final String TRANSLATE_CODEC = "GBK";  
  
    public Telent(String hostname, int hostport, String user,  
            String password) throws SocketException, IOException {  
        super();  
        this.hostname = hostname;  
        this.hostport = hostport;  
        this.user = user;  
        this.password = password;  
  
        telnet = new TelnetClient("VT100");// VT100 VT52 VT220 VTNT ANSI  
        telnet.connect(hostname, hostport);  
        in = telnet.getInputStream();  
        out = new PrintStream(telnet.getOutputStream());  
  
        readUntil("login: ");  
        write(user);  
        write("\n");  
        readUntil("Password: ");  
        write(password);  
        write("\n");  
    }  
  
    public void doJob() {  
        // restartTerminal();  
        counter();  
    }  
  
    private void restartTerminal() {  
        try {  
            readUntil(">");  
           // write("telnet 0.0.7.74\n");  
            readUntil("login: ");  
            write("dd\n", 500);  
            readToEnd();  
                
            write("dff\n", 200);  
            readToEnd();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
  
        }  
    }  
  
    private void counter() {  
        try {  
            readUntil(">");  
          //  write("telnet 4.3.4.4\n");  
            readUntil("login: ");  
            write("dd\n", 1000);  
            readToEnd();  
  
            write("\r\n", 200);  
            readToEnd();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
  
        }  
    }  
  
    public void readToEnd() {  
        ReadThread readThread = new ReadThread();  
        readThread.start();  
        try {  
            readThread.join();  
        } catch (Exception e) {  
        }  
        readThread = null;  
    }  
  
    public void readUntil(String str) {  
        char last = str.charAt(str.length() - 1);  
        String[] ss;  
        try {  
            StringBuffer sb = new StringBuffer();  
            char c;  
            int code = -1;  
            boolean ansiControl = false;  
            boolean start = true;  
            while ((code = (in.read())) != -1) {  
                c = (char) code;  
                if (c == '\033') {//vt100控制码都是以\033开头的。  
                    ansiControl = true;  
                    int code2 = in.read();  
                    char cc = (char) code2;  
                    if (cc == '[' || cc == '(') {  
                    }  
                }  
                if (!ansiControl) {  
                    if (c == '\r') {  
                        String olds = new String(sb.toString().getBytes(  
                                ORIG_CODEC), TRANSLATE_CODEC);  
                        System.out.println(olds);  
                        if (sb.lastIndexOf(str) != -1) {  
                            break;  
                        }  
                        sb.delete(0, sb.length());  
                    } else if (c == '\n')  
                        ;  
                    else  
                        sb.append(c);  
                    if (sb.lastIndexOf(str) != -1) {  
                        break;  
                    }  
                }  
  
                if (ansiControl) {  
                    if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')  
                            || c == '"') {  
                        ansiControl = false;  
                    }  
                }  
            }  
            System.out.println(new String(sb.toString().getBytes(ORIG_CODEC),  
                    TRANSLATE_CODEC));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void write(String s) {  
        try {  
            out.write(s.getBytes());  
            out.flush();  
            System.out.println(s);  
        } catch (Exception e) {  
        }  
    }  
  
    public void write(String s, int sleep) {  
        write(s);  
        try {  
            Thread.sleep(sleep);  
        } catch (Exception e) {  
        }  
    }  
  
    /** 
     * 完成之后必须关闭 
     */  
    public void close() {  
        if (out != null)  
            out.close();  
        if (in != null)  
            try {  
                in.close();  
            } catch (IOException e1) {  
            }  
        if (telnet != null)  
            try {  
                telnet.disconnect();  
            } catch (IOException e) {  
            }  
    }  
  
    /** 
     * 读取主线程，负责管理子线程。防止读取时不动了，这时就抛弃读取子线程 
     * @author chruan 
     * 
     */  
    class ReadThread extends Thread {  
        public void run() {  
            synchronized (lock) {//只能一个读取  
                SubReadThread sub = new SubReadThread();  
                sub.start();  
                int last = sub.count;  
                while (true) {  
                    try {  
                        Thread.sleep(100);  
                    } catch (InterruptedException e) {  
                    }  
                    if (last == sub.count) {  
                        sub.stop();  
                        break;  
                    } else {  
                        last = sub.count;  
                    }  
                }  
                String s = sub.sb.toString();  
                try {  
                    System.out.println(new String(s.getBytes(ORIG_CODEC),  
                            TRANSLATE_CODEC));  
                } catch (UnsupportedEncodingException e) {  
                    System.out.println(s);  
                }  
                sub = null;  
            }  
  
//          System.out.println("===========ReadThread end=============");  
        }  
    }  
  
    /** 
     * 读取子线程，完成实际读取 
     * @author chruan 
     * 
     */  
    class SubReadThread extends Thread {  
        int count = 0;  
        StringBuffer sb = new StringBuffer();  
  
        public void read() {  
            try {  
                char c;  
                int code = -1;  
                boolean ansiControl = false;  
                boolean start = true;  
                while ((code = (in.read())) != -1) {  
                    count++;  
                    c = (char) code;  
                    if (c == '\033') {  
                        ansiControl = true;  
                        int code2 = in.read();  
                        char cc = (char) code2;  
                        count++;  
                        if (cc == '[' || cc == '(') {  
                        }  
                    }  
                    if (!ansiControl) {  
                        if (c == '\r') {  
                            String olds = new String(sb.toString().getBytes(  
                                    ORIG_CODEC), TRANSLATE_CODEC);  
                            System.out.println(olds);  
                            sb.delete(0, sb.length());  
                        } else if (c == '\n')  
                            ;  
                        else  
                            sb.append(c);  
                    }  
  
                    if (ansiControl) {  
                        if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')  
                                || c == '"') {  
                            ansiControl = false;  
                        }  
                    }  
                }  
            } catch (Exception e) {  
            }  
        }  
  
        public void run() {  
            read();  
        }  
    }  
    
}
