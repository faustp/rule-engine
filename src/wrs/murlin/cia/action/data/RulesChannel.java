/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.action.data;

import java.util.ArrayList;

/**
 *
 * @author faust
 */
public class RulesChannel {

    private ArrayList<String> block = new ArrayList<String>();
    private ArrayList<String> forward = new ArrayList<String>();

    public ArrayList<String> getBlock() {
        return block;
    }

    public void setBlock(ArrayList<String> block) {
        this.block = block;
    }

    public ArrayList<String> getForward() {
        return forward;
    }

    public void setForward(ArrayList<String> forward) {
        this.forward = forward;
    }

    @Override
    public String toString() {
        return "RulesChannel{" + "channelBlock=" + block + ", channelForward=" + forward + '}';
    }
}
