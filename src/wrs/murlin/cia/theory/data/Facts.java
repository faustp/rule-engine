/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.theory.data;

import java.util.ArrayList;

/**
 *
 * @author faust
 */
public class Facts {

    private ArrayList<String> defaultFacts = new ArrayList<String>();
    private ArrayList<String> optionalFacts = new ArrayList<String>();

    public ArrayList<String> getDefaultFacts() {
        return defaultFacts;
    }

    public void setDefaultFacts(ArrayList<String> defaultFacts) {
        this.defaultFacts = defaultFacts;
    }

    public ArrayList<String> getOptionalFacts() {
        return optionalFacts;
    }

    public void setOptionalFacts(ArrayList<String> optionalFacts) {
        this.optionalFacts = optionalFacts;
    }

    @Override
    public String toString() {
        return "Facts{" + "defaultFacts=" + defaultFacts + ", optionalFacts=" + optionalFacts + '}';
    }
}
