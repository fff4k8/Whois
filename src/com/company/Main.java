package com.company;

import javax.swing.*;
import java.awt.*;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {

        try {
            Whois server = new Whois();
            WhoisGUI a = new WhoisGUI(server);
            a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            a.pack();
            EventQueue.invokeLater(new FrameShower(a));
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Could not locate default host "
                    + Whois.DEFAULT_HOST, "Error", JOptionPane.ERROR_MESSAGE);
        }




    }

    private static class FrameShower implements Runnable {
        private final Frame frame;
        FrameShower(Frame frame) {

            this.frame = frame;
        }
        @Override
        public void run() {
            frame.setVisible(true);
        }
    }
}





