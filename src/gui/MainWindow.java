package gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        //Выставляем стандартные настройки
        setTitle("Switch Offer v: 0.2");
        setSize(250, 160);
        //setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Инициализация UI
        initGUIConfigureation();

        setVisible(true);
    }

    private void initGUIConfigureation(){
        time = new JLabel("Время");
        choose = new JLabel("Действие");

        time.setPreferredSize(new Dimension(70,15));
        choose.setPreferredSize(new Dimension(70,15));

        prefButtonsSize = new Dimension(110,25);

        initComboBox();

        //Создаем кнопку и вешаем на нее слушатель
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

        initPanels();
    }

    private void initComboBox(){
        hour = new JComboBox();
        minute = new JComboBox();
        action = new JComboBox();

        osVersion = System.getProperty("os.name");

        //Заполняем списки элементами
        for (int i = 0; i < hours.length; i++) hour.addItem(hours[i]);
        for (int i = 0; i < minutes.length; i++) minute.addItem(minutes[i]);

        //В зависимости от версии ОС показываем определенные возможные действия
        switch (osVersion){
            case "Windows XP":
                for (int i = 0; i < actionOptions.length-1; i++) action.addItem(actionOptions[i]);
                break;
            case "Windows 7":
                for (int i = 0; i < actionOptions.length; i++) action.addItem(actionOptions[i]);
                break;
            case "Linux":
                for (int i = 0; i < actionOptions.length; i++) action.addItem(actionOptions[i]);
                break;
        }
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

    public static void main(String[] args) {
        MainWindow test = new MainWindow();
    }
}
