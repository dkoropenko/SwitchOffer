package gui;

import javafx.application.Application;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Created by Koropenkods on 01.02.16.
 */
public class MainWindow extends JFrame {

    //Элементы UI
    private JLabel time, choose;
    private JComboBox hour, minute, action;
    private JPanel mainPanel, timePanel, actionPanel, optionsPanel, buttonsPanel;
    private JButton start, exit;

    //Предпочтительный размер кнопок.
    private Dimension prefButtonsSize;

    //Переменные для выпадающих списков
    private int[] hours = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    private int[] minutes = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,
    24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,
    53,54,55,56,57,58,59};
    private String[] actionOptions = {"Завершение работы", "Перезагрузить", "Завершение сеанса", "Сон"};

    //Переменная для определения ОС на которой запущена программа
    private String osVersion;

    public MainWindow (){
        //Определяем версию ОС
        osVersion = System.getProperty("os.name").toLowerCase();

        //Выставляем стандартные настройки
        setTitle("Switch Offer v: 1.0");
        setSize(250, 160);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Инициализация UI
        initLabels();
        initComboBox();
        initButtons();
        initPanels();
    }
    //Создаем метки.
    private void initLabels(){
        time = new JLabel("Время");
        choose = new JLabel("Действие");

        time.setPreferredSize(new Dimension(70, 15));
        choose.setPreferredSize(new Dimension(70, 15));
    }
    //Создаем списки времени и опций.
    private void initComboBox(){
        hour = new JComboBox();
        minute = new JComboBox();
        action = new JComboBox();

        //Заполняем списки элементами
        for (int i = 0; i < hours.length; i++) hour.addItem(hours[i]);
        for (int i = 0; i < minutes.length; i++) minute.addItem(minutes[i]);

        //В зависимости от версии ОС показываем определенные возможные действия
        switch (osVersion){
            case "windows xp":
                for (int i = 0; i < actionOptions.length-1; i++) action.addItem(actionOptions[i]);
                break;
            case "windows 7":
                for (int i = 0; i < actionOptions.length; i++) action.addItem(actionOptions[i]);
                break;
            case "linux":
                for (int i = 0; i < actionOptions.length; i++) action.addItem(actionOptions[i]);
                break;
        }
    }
    //Создаем управляющие кнопки.
    private void initButtons(){
        //Создаем кнопку и вешаем на нее слушатель
        prefButtonsSize = new Dimension(110,25);

        start = new JButton("Пуск");
        start.setPreferredSize(prefButtonsSize);
        start.addActionListener(new MyActionListener(hour,minute,action));

        //Добавляем кнопку выход и вешаем на нее слушатель с выходом.
        exit = new JButton("Закрыть");
        exit.setPreferredSize(prefButtonsSize);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    //Раскладываем элементы UI по панелям
    private void initPanels(){
        BorderLayout layout = new BorderLayout(5,5);
        FlowLayout elements = new FlowLayout(FlowLayout.LEFT);
        GridLayout options = new GridLayout(2,0);

        mainPanel = new JPanel(layout);
        mainPanel.setBorder(new EtchedBorder());

        timePanel = new JPanel(elements);
        actionPanel = new JPanel(elements);
        optionsPanel = new JPanel(options);
        buttonsPanel = new JPanel(elements);

        timePanel.add(time);
        timePanel.add(hour);
        timePanel.add(minute);
        timePanel.setBorder(new EtchedBorder());

        actionPanel.add(choose);
        actionPanel.add(action);
        actionPanel.setBorder(new EtchedBorder());

        optionsPanel.add(timePanel);
        optionsPanel.add(actionPanel);

        buttonsPanel.add(start);
        buttonsPanel.add(exit);

        mainPanel.add(optionsPanel,BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

     public void run(){
        //Не работает. Надо разобраться почему.
        //И размеры менял и что только не пробовал.

         ImageIcon icon = new ImageIcon("images/icon.png");
         setIconImage(icon.getImage());


        setVisible(true);
    }
}
