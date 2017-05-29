/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.theory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.operator.Operators;
import wrs.murlin.cia.theory.data.Rules;

/**
 *
 * @author faust
 */
public class TheoryBuilder {

    private final Logger _logger = Logger.getLogger(TheoryBuilder.class);
    private Theory theory;

    public TheoryBuilder(Theory _theory) {
        this.theory = _theory;
    }

    public StringBuilder build() throws CiaException {
        StringBuilder builtTheory = new StringBuilder();
        try {
            builtTheory.append("\n");
            _logger.trace("Loading Default Operators");
            builtTheory.append(Operators.GT_OP).append("\n");
            builtTheory.append(Operators.LT_OP).append("\n");
            _logger.trace("Building CIA Theory");
            builtTheory.append("\n");
            /**
             * Append Default Facts
             */
            for (String defaultFacts : theory.getFacts().getDefaultFacts()) {
                if (defaultFacts != null) {
                    builtTheory.append(defaultFacts).append(".\n");
                }
            }
            /**
             * Append Optional Facts
             */
            if (theory.getFacts().getOptionalFacts() != null) {
                for (String optionalFacts : theory.getFacts().getOptionalFacts()) {
                    if (optionalFacts != null) {
                        builtTheory.append(optionalFacts).append(".\n");
                    }
                }
            }
            /**
             * Append Rules and correct operators
             */
            builtTheory.append("\n");
            Rules rules = theory.getRules();
            String blockRule = applyOperators(rules.getBlock(), builtTheory);
            String forwardRule = applyOperators(rules.getForward(), builtTheory);
            String emailRule = applyOperators(rules.getEmail(), builtTheory);
            String logRule = rules.getLog();
            if (!nullReturn(blockRule)) {
                builtTheory.append(blockRule).append(".\n");
            }
            if (!nullReturn(forwardRule)) {
                builtTheory.append(forwardRule).append(".\n");
            }
            if (!nullReturn(emailRule)) {
                builtTheory.append(emailRule).append(".\n");
            }
            if (!nullReturn(logRule)) {
                builtTheory.append(logRule).append(".\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw new CiaException(ex);
        }
        return builtTheory;
    }

    private String applyOperators(String rule, StringBuilder sb1) {
        if (rule != null) {
            if (!rule.isEmpty()) {
                String[] seg = rule.split(":-");
                String type = seg[0];
                String _rule = seg[1];
                StringBuilder sb = new StringBuilder();
                sb.append(type).append(":- ");
                for (String data : _rule.split(",")) {
                    for (String data2 : data.split(";")) {
                        if (data2.contains(">")) {
                            StringBuilder _sb = new StringBuilder();
                            String head1 = data2.split(">")[0];
                            String head2 = data2.split(">")[1];
                            if (!head1.substring(0, 1).matches("[a-zA-Z]")) {
                                _sb.append(head1.substring(0, 1)).append("gt(");
                            } else {
                                _sb.append("gt(");
                            }
                            _sb.append(getFactsValue(head1, sb1));
                            if (!head2.substring(head2.length() - 1, head2.length()).matches("[a-zA-Z0-9]")) {
                                if (head2.substring(head2.length() - 1, head2.length()).matches("\\)")) {
                                    _sb.append(",").append(head2).append(")");
                                } else {
                                    _sb.append(",").append(head2.substring(0, head2.length() - 1)).append(")");
                                }
                            } else {
                                _sb.append(",").append(head2).append(")");
                            }
                            if (data.contains(";")) {
                                _sb.append(";");
                            } else {
                                _sb.append(",");
                            }
                            sb.append(_sb);
                            _sb = null;
                        } else if (data2.contains("<")) {
                            StringBuilder _sb = new StringBuilder();
                            String head1 = data2.split("<")[0];
                            String head2 = data2.split("<")[1];
                            if (!head1.substring(0, 1).matches("[a-zA-Z]")) {
                                _sb.append(head1.substring(0, 1)).append("lt(");
                            } else {
                                _sb.append("lt(");
                            }
                            _sb.append(getFactsValue(head1, sb1));
                            if (!head2.substring(head2.length() - 1, head2.length()).matches("[a-zA-Z0-9]")) {
                                if (head2.substring(head2.length() - 1, head2.length()).matches("\\)")) {
                                    _sb.append(",").append(head2).append(")");
                                } else {
                                    _sb.append(",").append(head2.substring(0, head2.length() - 1)).append(")");
                                }
                            } else {
                                _sb.append(",").append(head2).append(")");
                            }
                            if (data.contains(";")) {
                                _sb.append(";");
                            } else {
                                _sb.append(",");
                            }
                            sb.append(_sb);
                            _sb = null;
                        } else {
                            if (data.contains(";")) {
                                sb.append(data2).append(";");
                            } else {
                                sb.append(data2).append(",");
                            }
                        }
                    }
                }
                return sb.toString().replaceAll(",$", "").replaceAll(";$", "");
            } else {
                return rule;
            }
        } else {
            return rule;
        }
    }

    private String getFactsValue(String data, StringBuilder sb1) {
        String tmp = sb1.toString();
        Pattern p = Pattern.compile("^" + data.replaceAll("\\W", "") + "\\([0-9]+\\)\\.$", Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);
        Matcher matcher = p.matcher(tmp);
        if (matcher.find()) {
            return getValue(matcher.group());
        } else {
            return data;
        }
    }

    private String getValue(String data) {
        String _data = String.valueOf(data);
        Pattern p = Pattern.compile("-?\\d+");
        Matcher matcher = p.matcher(_data);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
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
}