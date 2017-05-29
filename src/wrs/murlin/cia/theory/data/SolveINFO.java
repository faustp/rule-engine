/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.theory.data;

import alice.tuprolog.SolveInfo;

/**
 *
 * @author faust
 */
public class SolveINFO {

    private SolveInfo solnBlock = null;
    private SolveInfo solnEmail = null;
    private SolveInfo solnForward = null;
    private SolveInfo solnLog = null;

    public SolveInfo getSolnBlock() {
        return solnBlock;
    }

    public void setSolnBlock(SolveInfo solnBlock) {
        this.solnBlock = solnBlock;
    }

    public SolveInfo getSolnEmail() {
        return solnEmail;
    }

    public void setSolnEmail(SolveInfo solnEmail) {
        this.solnEmail = solnEmail;
    }

    public SolveInfo getSolnForward() {
        return solnForward;
    }

    public void setSolnForward(SolveInfo solnForward) {
        this.solnForward = solnForward;
    }

    public SolveInfo getSolnLog() {
        return solnLog;
    }

    public void setSolnLog(SolveInfo solnLog) {
        this.solnLog = solnLog;
    }

    @Override
    public String toString() {
        return "SolveINFO{" + "solnBlock=" + solnBlock + ", solnEmail=" + solnEmail + ", solnForward=" + solnForward + ", solnLog=" + solnLog + '}';
    }
}
