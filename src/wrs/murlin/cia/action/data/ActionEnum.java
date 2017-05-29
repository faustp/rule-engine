/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.action.data;

/**
 *
 * @author faust
 */
public enum ActionEnum {
    /*
     * 
     */

    BLOCK, EMAIL, FORWARD, LOG;

    public ActionEnum getValue(String value) {
        if (value != null && value.length() > 0) {
            if (value.toLowerCase().equalsIgnoreCase(ActionEnum.BLOCK.toString())) {
                return ActionEnum.BLOCK;
            } else if (value.toLowerCase().equalsIgnoreCase(ActionEnum.EMAIL.toString())) {
                return ActionEnum.EMAIL;
            } else if (value.toLowerCase().equalsIgnoreCase(ActionEnum.LOG.toString())) {
                return ActionEnum.LOG;
            } else if (value.toLowerCase().equalsIgnoreCase(ActionEnum.FORWARD.toString())) {
                return ActionEnum.FORWARD;
            }
        }
        return null;
    }
}
