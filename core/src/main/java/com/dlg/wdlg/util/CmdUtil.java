package com.dlg.wdlg.util;


import java.io.IOException;

public class CmdUtil {

    public static void exec(String cmd) throws IOException {
            Runtime.getRuntime().exec(cmd);
    }

}
