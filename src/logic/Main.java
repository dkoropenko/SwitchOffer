package logic;

import javax.swing.*;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by Koropenkods on 02.02.16.
 */
public class Main extends Thread{

    //Переменный для хранения UI элементов
    private JComboBox hour, minutes, action;

    //Переменные для системного времени
    private Calendar systemTime;
    private int systemHour, systemMinutes;

    //Переменные для пользовательского времени
    private int userHour, userMinutes;

    //Проверка на "закрытие" потока.
    private boolean check = true;

    private Action logic;

    public Main (JComboBox hour, JComboBox minutes, JComboBox action){
        this.hour = hour;
        this.minutes = minutes;
        this.action = action;
        logic = new Action();

        //Создаем переменную для храниения системного времени.
        systemTime = Calendar.getInstance();
        systemTime.setTimeZone(TimeZone.getTimeZone("GMT+3"));
    }

    public void run(){
        //Берем пользовательское время выключения компьютера
        userHour = hour.getSelectedIndex();
        userMinutes = minutes.getSelectedIndex();

        while(check){
            //Если небыло команды "убить" поток, то сверяем системное время
            //и пользовательское время.
            if(!Thread.interrupted()){
                //Берем системное время и забираем оттуда часы и минуты
                systemTime.setTimeInMillis(System.currentTimeMillis());
                systemHour = systemTime.get(11);
                systemMinutes = systemTime.get(12);

                if (systemHour == userHour && systemMinutes == userMinutes){
                    logic.setParameter(action.getSelectedIndex());
                    logic.doAction();
                    this.interrupt();
                }
            }
            else check = false;

            try {
                //Периодичность проверки каждые 10 секунд
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                //Если прерывание пришло на момент "сна" потока, то добиваем его.
                this.interrupt();
            }
        }
    }
}
