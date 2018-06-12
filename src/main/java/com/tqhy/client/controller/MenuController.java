package com.tqhy.client.controller;

import com.tqhy.client.view.TqhyDialog;
import javafx.scene.input.MouseEvent;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class MenuController {

    public void detail(MouseEvent event) {
        System.out.println("detail click...");
    }

    public void config(MouseEvent event) {
        System.out.println("config click...");
    }

    public void exit(MouseEvent event) {
        System.out.println("exit click...");
        System.exit(0);
    }

    public void min(MouseEvent event) {
        System.out.println("min click...");
    }

    public void info(MouseEvent event) {
        System.out.println("info click...");
        TqhyDialog tqhyDialog = new TqhyDialog();
        tqhyDialog.show();
    }
}
