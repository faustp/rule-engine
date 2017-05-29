/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.theory.data;

/**
 *
 * @author faust
 */
public class Rules {

    private String block = null;
    private String forward = null;
    private String email = null;
    private String log = null;

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "CIARules{" + "block=" + block + ", forward=" + forward + ", email=" + email + ", log=" + log + '}';
    }
}
