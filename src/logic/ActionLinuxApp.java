package logic;

import java.io.IOException;

/**
 * Created by Диман on 15.02.2016.
 * * Класс для выполнения выбранного действия.
 * Класс имплементит интерфейс Action для
 * реализации паттерна фабрики.
 */
public class ActionLinuxApp implements Action {
    //Переменная для выбранной опции.
    private int options;

    //переменные для хранения cmd вызовов управлением системой.
    private String[] command = {"sudo shutdown -h",
                                "sudo shutdown -r",
                                "sudo exit",
                                "sudo pm-suspend"};

    @Override
    public void setParameter(int options) {
        this.options = options;
    }

    @Override
    public void doAction() {
        try {
            Process proc = Runtime.getRuntime().exec(command[options]);
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
