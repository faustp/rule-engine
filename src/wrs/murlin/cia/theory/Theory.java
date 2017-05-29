/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.theory;

import wrs.murlin.cia.theory.data.Facts;
import wrs.murlin.cia.theory.data.Rules;

/**
 *
 * @author faust
 */
public class Theory {

    private Facts facts;
    private Rules rules;

    public Facts getFacts() {
        return facts;
    }

    public void setFacts(Facts facts) {
        this.facts = facts;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "Theory{" + "facts=" + facts + ", rules=" + rules + '}';
    }
}
