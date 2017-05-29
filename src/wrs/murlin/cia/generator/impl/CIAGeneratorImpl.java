/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.generator.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Arrays;
import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.datatype.checker.DataTypeChecker;

/**
 *
 * @author faust
 */
public abstract class CIAGeneratorImpl implements CIAGeneratorInterface {

    protected Gson gson = new Gson();

    @Override
    public Object generate() throws CiaException {
        return null;
    }

   
    protected String validateNullValue(String s) {
        try {
            s = s == null ? "null" : s;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }

  
    protected String validateDatatype(String strValue) {
        String _facts = "";
        DataTypeChecker dtypechckr = new DataTypeChecker(strValue);
        if (dtypechckr.isString()) {
            _facts = ENCLOSE.concat(strValue).concat(ENCLOSE);
        } else if ((dtypechckr.isInteger()) || (dtypechckr.isBoolean())) {
            _facts = strValue;
        }
        return _facts;
    }

    @Override
    public String constructFacts(String term, String termVal) {
        String facts = null;
        if (term != null && termVal != null) {
            facts = term.concat(OPEN_TERM).concat(validateDatatype(termVal)).concat(CLOSE_TERM);
        }
        return facts;
    }

    @Override
    public String getValue(String[] keyTree, String jsonString) throws CiaException {
        String _jsonValue = jsonString;
        ArrayList<String> _keyTree = new ArrayList<String>();
        try {
            _keyTree.addAll(Arrays.asList(keyTree));
            _keyTree.remove(0);
            for (String s : _keyTree) {
                JsonObject json = (JsonObject) new JsonParser().parse(_jsonValue);
                _jsonValue = json.get(s).toString();
            }
        } catch (Exception ex) {
            return null;
        }
        return validateNullValue(_jsonValue);
    }
}
