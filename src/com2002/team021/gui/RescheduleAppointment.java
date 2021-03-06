package com2002.team021.gui;

import com2002.team021.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.CancelledKeyException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Created by joshua on 10/12/15.
 */
public class RescheduleAppointment extends JFrame {

    private AppointmentDayView dayView;
    private Appointment appointmentToModify;
    private TimeEntry startEntry;
    private TimeEntry endEntry;
    private JButton rescheduleButton;
    private Date startTimestamp;
    private Date endTimestamp;
    private DateWidget dateEntry;


    private class TimeEntry {
        private SpinnerNumberModel hourEntryModel;
        private SpinnerNumberModel minuteEntryModel;
        private JSpinner hourEntry;
        private JSpinner minuteEntry;
        private JPanel timeContainer;

        public TimeEntry() {
            hourEntryModel = new SpinnerNumberModel(9, 9, 16, 1);
            minuteEntryModel = new SpinnerNumberModel(0, 0, 59, 1);
            hourEntry = new JSpinner(hourEntryModel);
            minuteEntry = new JSpinner(minuteEntryModel);

            timeContainer = new JPanel(new GridLayout(2, 2));
            timeContainer.add(new JLabel("HH"));
            timeContainer.add(new JLabel("MM"));
            timeContainer.add(hourEntry);
            timeContainer.add(minuteEntry);
        }

        public JPanel getTimeContainer() {
            return timeContainer;
        }

        public SpinnerNumberModel getHourEntryModel() {
            return hourEntryModel;
        }

        public SpinnerNumberModel getMinuteEntryModel() {
            return minuteEntryModel;
        }

    }

    private class RescheduleButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            startTimestamp = dateEntry.getDate();
            computeTimestamps();
            Patient patient = appointmentToModify.getPatient();
            Practitioner practitioner = appointmentToModify.getPractitioner();

            ArrayList<Treatment> emptyTreatment = new ArrayList<>();

            Appointment newAppointment = new Appointment(startTimestamp, endTimestamp, patient, practitioner, emptyTreatment);
            if (dayView.updateAppointment(appointmentToModify, newAppointment)) {
                setVisible(false);
                dayView.setEnabled(true);
            }
        }
    }


    private void computeTimestamps() {
        int startHour = startEntry.getHourEntryModel().getNumber().intValue();
        int startMinute = startEntry.getMinuteEntryModel().getNumber().intValue();
        int endHour = endEntry.getHourEntryModel().getNumber().intValue();
        int endMinute = endEntry.getMinuteEntryModel().getNumber().intValue();
        Calendar startTimeCal = Calendar.getInstance();
        startTimeCal.setTime(startTimestamp);
        startTimeCal.set(Calendar.HOUR_OF_DAY, startHour);
        startTimeCal.set(Calendar.MINUTE, startMinute);
        Calendar endTimeCal = Calendar.getInstance();
        endTimeCal.setTime(endTimestamp);
        endTimeCal.set(Calendar.HOUR_OF_DAY, endHour);
        endTimeCal.set(Calendar.MINUTE, endMinute);
        startTimestamp = startTimeCal.getTime();
        endTimestamp = endTimeCal.getTime();
    }

    public RescheduleAppointment(Appointment appointmentToModify, AppointmentDayView dayView) {
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dayView.setEnabled(true);
            }
        });

        this.dayView = dayView;
        this.appointmentToModify = appointmentToModify;
        setTitle("Reschedule Appointment");
        setSize(300,400);
        Container contentPane = getContentPane();
        //contentPane.setLayout(new GridLayout(5, 2));
        contentPane.setLayout(null);

        Practitioner practitioner = appointmentToModify.getPractitioner();
        Patient patient = appointmentToModify.getPatient();

        JLabel patientLabel = new JLabel("Patient: ");
        contentPane.add(patientLabel);
        JLabel patientName = new JLabel(patient.getName());
        contentPane.add(patientName);
        patientLabel.setBounds(45,50,50,20);
        patientName.setBounds(125,50,150,20);

        JLabel practitionerLabel = new JLabel("Practitioner: ");
        contentPane.add(practitionerLabel);
        JLabel practitionerName = new JLabel(practitioner.getName());
        contentPane.add(practitionerName);
        practitionerLabel.setBounds(19,90,100,20);
        practitionerName.setBounds(125,90,150,20);


        JLabel dateLabel = new JLabel("Date: ");
        dateLabel.setBounds(19, 130, 100, 20);
        contentPane.add(dateLabel);
        dateEntry = new DateWidget(appointmentToModify.getStart());
        contentPane.add(dateEntry);
        dateEntry.setBounds(125, 130, 150, 40);

        startEntry = new TimeEntry();
        endEntry = new TimeEntry();
        JLabel startTime = new JLabel("Start time:");
        contentPane.add(startTime);
        startTime.setBounds(30,170,100,58);
        contentPane.add(startEntry.getTimeContainer());
        startEntry.getTimeContainer().setBounds(125,170,150,40);
        JLabel endTime = new JLabel("End time:");
        contentPane.add(endTime);
        endTime.setBounds(35,210,100,58);
        contentPane.add(endEntry.getTimeContainer());
        endEntry.getTimeContainer().setBounds(125,210,150,40);

        contentPane.add(new JLabel());
        rescheduleButton = new JButton("Reschedule");
        rescheduleButton.addActionListener(new RescheduleButtonHandler());
        contentPane.add(rescheduleButton);
        rescheduleButton.setBounds(125,250,150,20);

        // Get current time values of appointment
        startTimestamp = appointmentToModify.getStart();
        endTimestamp = appointmentToModify.getEnd();
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startTimestamp);
        int startHour = startCal.get(Calendar.HOUR_OF_DAY);
        int startMinute = startCal.get(Calendar.MINUTE);
        startEntry.getHourEntryModel().setValue(startHour);
        startEntry.getMinuteEntryModel().setValue(startMinute);

        Calendar endCal = Calendar.getInstance();

        endCal.setTime(endTimestamp);

        int endHour = endCal.get(Calendar.HOUR_OF_DAY);
        int endMinute = endCal.get(Calendar.MINUTE);
        endEntry.getHourEntryModel().setValue(endHour);
        endEntry.getMinuteEntryModel().setValue(endMinute);

        //pack();
        setResizable(false);
        setVisible(true);
    }
}
