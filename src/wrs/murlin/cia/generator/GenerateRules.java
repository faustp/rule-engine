/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.generator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import wrs.common.murlin.GenericMURLinJSONObject;
import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.action.data.ActionEnum;
import wrs.murlin.cia.generator.impl.CIAGeneratorImpl;
import wrs.murlin.cia.theory.data.Rules;

/**
 *
 * @author faust
 */
public class GenerateRules extends CIAGeneratorImpl {

    private final static Logger _logger = Logger.getLogger(GenerateRules.class);
    private GenericMURLinJSONObject _murlinObj;
    private String ruleBlock = null;
    private String ruleForward = null;
    private String ruleEmail = null;
    private String ruleLog = null;

    public GenerateRules(GenericMURLinJSONObject murlinObj) throws CiaException {
        this._murlinObj = murlinObj;
    }

    @Override
    public Rules generate() throws CiaException {
        Rules rules = new Rules();
        try {
            String _rules = _murlinObj.getDyn_conf().get("cia");
            _logger.debug("Rules : " + _rules);
            JsonObject jsonobj = (JsonObject) new JsonParser().parse(_rules);
            jsonobj = (JsonObject) jsonobj.get("rule");
            if (jsonobj.get(ActionEnum.BLOCK.toString().toLowerCase()) != null) {
                ruleBlock = jsonobj.get(ActionEnum.BLOCK.toString().toLowerCase()).getAsString();
                if (ruleBlock.matches(".*" + ActionEnum.BLOCK.toString().toLowerCase() + ".*")) {
                    throw new CiaException("Block rule can't have block or not(block) operator... never ending loop will be triggered");
                }
            }
            if (jsonobj.get(ActionEnum.FORWARD.toString().toLowerCase()) != null) {
                ruleForward = jsonobj.get(ActionEnum.FORWARD.toString().toLowerCase()).getAsString();
                if (ruleForward.matches(".*" + ActionEnum.FORWARD.toString().toLowerCase() + ".*")) {
                    throw new CiaException("Forward rule can't have forward or not(forward) operator... never ending loop will be triggered");
                }
            }
            if (jsonobj.get(ActionEnum.EMAIL.toString().toLowerCase()) != null) {
                ruleEmail = jsonobj.get(ActionEnum.EMAIL.toString().toLowerCase()).getAsString();
                if (ruleEmail.matches(".*" + ActionEnum.EMAIL.toString().toLowerCase() + ".*")) {
                    throw new CiaException("Email rule can't have email or not(email) operator... never ending loop will be triggered");
                }
            }
            if (jsonobj.get(ActionEnum.LOG.toString().toLowerCase()) != null) {
                ruleLog = jsonobj.get(ActionEnum.LOG.toString().toLowerCase()).getAsString();
                if (ruleLog.matches(".*" + ActionEnum.LOG.toString().toLowerCase() + ".*")) {
                    throw new CiaException("Log rule can't have log or not(log) operator... never ending loop will be triggered");
                }
            }
            if (!nullReturn(ruleBlock)) {
                rules.setBlock(generateRuleHead(ActionEnum.BLOCK.toString().toLowerCase()) + ruleBlock);
            }
            if (!nullReturn(ruleForward)) {
                rules.setForward(generateRuleHead(ActionEnum.FORWARD.toString().toLowerCase()) + ruleForward);
            }
            if (!nullReturn(ruleEmail)) {
                rules.setEmail(generateRuleHead(ActionEnum.EMAIL.toString().toLowerCase()) + ruleEmail);
            }
            if (!nullReturn(ruleLog)) {
                rules.setLog(generateRuleHead(ActionEnum.LOG.toString().toLowerCase()) + ruleLog);
            }
            _logger.debug("Generated RULE(s) out of FACTS :: " + rules.toString());
            _rules = null;
        } catch (Exception ex) {
            throw new CiaException(ex);
        }
        return rules;
    }

    private boolean nullReturn(String s) {
        try {
            if (s == null || !(s.trim().length() > 0) || (s.trim().equalsIgnoreCase("") || s.isEmpty())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    private String generateRuleHead(String rulehead) {
        String _rulehead = "";
        _rulehead = rulehead.concat(":-");
        return _rulehead;
    }
}
