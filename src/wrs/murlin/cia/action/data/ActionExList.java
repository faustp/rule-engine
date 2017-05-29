/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.action.data;

/**
 *
 * @author faust
 */
public class ActionExList {

    private String ex_listBlock;
    private String ex_listForward;
    private String ex_listEmail;
    private String ex_listLog;

    public String getEx_listBlock() {
        return ex_listBlock;
    }

    public void setEx_listBlock(String ex_listBlock) {
        this.ex_listBlock = ex_listBlock;
    }

    public String getEx_listLog() {
        return ex_listLog;
    }

    public void setEx_listLog(String ex_listLog) {
        this.ex_listLog = ex_listLog;
    }

    public String getEx_listEmail() {
        return ex_listEmail;
    }

    public void setEx_listEmail(String ex_listEmail) {
        this.ex_listEmail = ex_listEmail;
    }

    public String getEx_listForward() {
        return ex_listForward;
    }

    public void setEx_listForward(String ex_listForward) {
        this.ex_listForward = ex_listForward;
    }

    @Override
    public String toString() {
        return "ActionExList{" + "ex_listBlock=" + ex_listBlock + ", ex_listForward=" + ex_listForward + ", ex_listEmail=" + ex_listEmail + ", ex_listLog=" + ex_listLog + '}';
    }
}
