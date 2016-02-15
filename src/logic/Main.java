package logic;

import javax.swing.*;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Koropenkods on 02.02.16.
 * Этот хитрый класс выполняет всю работу программы.
 * Здесь мы смотрим системное время и сверяем уго с тем временем,
 * которое ввел пользователь.
 *
 * Реализовал паттерн фабрики для добавления разных ОС в дальнейшем.
 *
 * Пока реализовано под виндовс машины.
 * Под линукс не тестировалось.
 */
public class Main extends Thread{

    //Переменные для хранения UI элементов
    private JComboBox hour, minutes, action;

    //Переменные для системного времени
    private Calendar systemTime;
    private int systemHour, systemMinutes;

    //Переменные для пользовательского времени
    private int userHour, userMinutes;

    //Проверка на "закрытие" потока.
    private boolean check = true;

    //Переменная для выполнения действия.
    Action system;
    //Переменная для управления фабрикой ОС.
    Factory factory;


    public Main (JComboBox hour, JComboBox minutes, JComboBox action){
        this.hour = hour;
        this.minutes = minutes;
        this.action = action;

        //Инициализируем фабрику.
        factory = new Factory();

        //Создаем переменную для храниения системного времени.
        systemTime = Calendar.getInstance();
        systemTime.setTimeZone(TimeZone.getTimeZone("GMT+3"));
    }

    public void run(){
        //Берем время выключения компьютера, указанное пользователем.
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

                //Если время совпало, то выполняем выбранное действие.
                if (systemHour == userHour && systemMinutes == userMinutes){
                    //Узнаем систему, на которой запущена программа.
                    String osName = System.getProperty("os.name");

                    //Инициализируем класс из фабрики.
                    system = factory.getClient(osName);
                    if (system != null){
                        system.setParameter(action.getSelectedIndex()); //Указываем действие.
                        system.doAction(); //Выполняем.
                    }

                    //Прерываем поток, если что-то пошло не так.
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
