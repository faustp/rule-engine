/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.engine;

import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.action.data.ActionEnum;
import wrs.murlin.cia.theory.data.SolveINFO;

/**
 *
 * @author faust
 */
public interface CIAEngineInterface {

    public static final String BLOCK = ActionEnum.BLOCK.toString().concat(".").toLowerCase();
    public static final String EMAIL = ActionEnum.EMAIL.toString().concat(".").toLowerCase();
    public static final String LOG = ActionEnum.LOG.toString().concat(".").toLowerCase();
    public static final String FORWARD = ActionEnum.FORWARD.toString().concat(".").toLowerCase();

    public SolveINFO evaluate() throws CiaException;
}
