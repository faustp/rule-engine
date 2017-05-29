/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.data;

/**
 *
 * @author faust
 */
public class CIAResult {

    private boolean soln_block = false;
    private boolean soln_email = false;
    private boolean soln_log = false;
    private boolean soln_forward = false;

    public boolean isSoln_block() {
        return soln_block;
    }

    public void setSoln_block(boolean soln_block) {
        this.soln_block = soln_block;
    }

    public boolean isSoln_email() {
        return soln_email;
    }

    public void setSoln_email(boolean soln_email) {
        this.soln_email = soln_email;
    }

    public boolean isSoln_forward() {
        return soln_forward;
    }

    public void setSoln_forward(boolean soln_forward) {
        this.soln_forward = soln_forward;
    }

    public boolean isSoln_log() {
        return soln_log;
    }

    public void setSoln_log(boolean soln_log) {
        this.soln_log = soln_log;
    }

    @Override
    public String toString() {
        return "CIAResult{" + "soln_block=" + soln_block + ", soln_email=" + soln_email + ", soln_log=" + soln_log + ", soln_forward=" + soln_forward + '}';
    }
}
