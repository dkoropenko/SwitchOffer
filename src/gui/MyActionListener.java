package gui;

import logic.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Koropenkods on 01.02.16.
 */
public class MyActionListener implements ActionListener {
    //Статус запуска программы
    private boolean status = false;

    //Переменная для запуска "слежения" программы за временем выключения
    Main startWatching;

    //Переменный для хранения UI элементов
    private JComboBox hour, minutes, action;

    public MyActionListener(JComboBox hour, JComboBox minutes, JComboBox action){
        this.hour = hour;
        this.minutes = minutes;
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Если программма не была включена, то при нажатии на кнопку пуск
        //запускаем процесс мониторинга времени срабатывания
        //В новом потоке
        if (status == false) {
            startWatching = new Main(hour,minutes,action);
            startWatching.start();

            JButton start = (JButton) e.getSource();
            start.setText("Выполняется");
            setUIstatus(false);
            status = true;
        }
        //Если программа работает, то останавливаем выполнение
        //и убиваем поток.
        else{
            startWatching.interrupt();

            JButton start = (JButton) e.getSource();
            start.setText("Пуск");
            setUIstatus(true);
            status = false;
        }
    }

    private void setUIstatus(boolean options){
        hour.setEnabled(options);
        minutes.setEnabled(options);
        action.setEnabled(options);
    }
}
