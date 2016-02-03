package logic;

import java.io.IOException;

/**
 * Created by Koropenkods on 03.02.16.
 */
public class Action {
    //переменные для хранения cmd вызовов управлением системой.
    private String[] win = {"shutdown /s /f",
            "shutdown /r /f",
            "shutdown /l /f",
            "shutdown /h /f",};

    private String[] linux = {"sudo shutdown -h",
            "sudo shutdown -r",
            "sudo exit",
            "sudo pm-suspend"};

    //Опция, выбранная пользователем.
    private int options = 99;
    private String osVersion;

    public void setParameter(int options){
        this.options = options;
        osVersion = System.getProperty("os.name");
    }

    public void doAction(){
        if (options == 99) System.out.println("Совсем неудачно");
        else{
            try {
                if (osVersion.indexOf("Windows") != -1){
                    Process proc = Runtime.getRuntime().exec(win[options]);
                    proc.waitFor();
                }
                else{
                    Process proc = Runtime.getRuntime().exec(linux[options]);
                    proc.waitFor();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
