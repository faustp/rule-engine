/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.test.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.BasicConfigurator;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import wrs.common.murlin.GenericMURLinJSONObject;
import wrs.murlin.cia.CiaException;
import wrs.murlin.cia.datatype.checker.DataTypeChecker;
import wrs.murlin.cia.generator.GenerateFacts;
import wrs.murlin.cia.generator.impl.CIAGeneratorImpl;
import wrs.murlin.cia.theory.data.Facts;

/**
 *
 * @author faust
 */
public class testGenerateOptionalFacts extends CIAGeneratorImpl {

    private Facts actualOptionalFacts;
    private Facts expectedOptionalFacts;
    private GenericMURLinJSONObject murlinObj;

    @BeforeClass
    public static void beforeClass() {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
    }

    /**
     * Test of generate method, of class GenerateOptionalFacts.
     */
    @Test
    public void testValid() {
        System.out.println("UT-WRS-SE-042 : Test GenerateOptional Facts with valid or existing key reference");
        boolean ok = false;
        try {
            murlinObj = new GenericMURLinJSONObject();
            System.out.println("Test Optional Facts Generation using terms of facts");
            Map<String, String> results = new HashMap<String, String>();
            Map<String, String> dyn = new HashMap<String, String>();
            String[] mods = {"wcs", "psm", "mva"};
            String[] field = {"wcs.category", "wcs.layer", "psm.pattern", "psm.pattern_hit", "mva.bc", "mva.ns", "mva.ws"};
            dyn = generateFields(field);
            murlinObj.setDyn_conf(dyn);
            System.out.println(murlinObj.getDyn_conf().get("result_fields"));
            results = generateResult(mods);
            murlinObj.setResults(results);
            GenerateFacts instance = new GenerateFacts(murlinObj);
            Facts result = instance.generate();
            ok = true;
            System.out.println(result);
        } catch (Exception ex) {
            System.out.println(ex.getCause());
            fail(ex.getLocalizedMessage());
        }
        if (ok) {
            assertTrue(true);
            System.out.println("Facts Generation was successfully performed");
            System.out.println("*** Passed");
        }

    }

    @Test
    public void testInvalid() {
        System.out.println("UT-WRS-SE-043 :Test GenerateOptional Facts with invalid or non-existing key reference");
        boolean ok = false;
        try {
            murlinObj = new GenericMURLinJSONObject();
            Map<String, String> results = new HashMap<String, String>();
            Map<String, String> dyn = new HashMap<String, String>();
            String[] mods = {"wcs", "psm", "mva"};
            String[] field = {"wcs.category", "wcs.layer", "wcs.invalid", "nex.invalid", "psm.pattern", "psm.pattern_hit", "mva.bc", "mva.ns", "mva.ws"};
            dyn = generateFields(field);
            murlinObj.setDyn_conf(dyn);
            System.out.println(murlinObj.getDyn_conf().get("result_fields"));
            results = generateResult(mods);
            murlinObj.setResults(results);
            GenerateFacts instance = new GenerateFacts(murlinObj);
            Facts result = instance.generate();
            System.out.println(result);
            ok = true;
        } catch (Exception ex) {
            assertTrue(true);
            System.out.println("*** Passed :Exception error has successfully handled :: " + ex + " (Non-Existing or Invalid key reference)");
        }
        if (!ok) {
            fail("*** Failed : Process should throw CiaException error");
        }
    }

    private Map<String, String> generateResult(String[] module) {

        ArrayList<String> _module = new ArrayList<String>();
        _module.addAll(Arrays.asList(module));

        Map<String, String> result = new HashMap<String, String>();
        String value = "";
        for (String mod : _module) {
            if (mod.equalsIgnoreCase("wcs")) {
                value = "{\"category\":\"76,79\",\"layer\":\"wcs\",\"raw_url\":{\"domain\":\"medicarepillromney.com\",\"url\":\"medicarepillromney.com\",\"file\":\"\",\"query\":\"\",\"encode\":\"none\",\"path\":\"\",\"port\":80},\"cascaded\":{\"directory_level\":false,\"domain_level\":true},\"safetyrating\":49,\"normalized_url\":{\"domain\":\"medicarepillromney.com\",\"url\":\"http://medicarepillromney.com:80/\",\"file\":\"\",\"query\":\"\",\"encode\":\"none\",\"path\":\"/\",\"port\":80}}";
                result.put(mod, value);
            }
            if (mod.equalsIgnoreCase("psm")) {
                value = "{\"pattern\":\".*(pill|viagra|rolex|doc)+.*(.com|.ru)\",\"pattern_hit\":false,\"hint\":\"null\"}";
                result.put(mod, value);
            }
            if (mod.equalsIgnoreCase("mva")) {
                value = "{\"bc\":[70,71],\"ns\":[56,70,71],\"ws\":[90]}";
                result.put(mod, value);
            }
            if (mod.equalsIgnoreCase("ewl")) {
                value = "{\"query_result\":{\"hit\":true,\"rule\":\"trendmicro.com/\",\"cascade\":3,\"flag\":0,\"parent_url\":false,\"type\":1}}";
                result.put(mod, value);
            }

        }
        return result;
    }

    @Test
    public void testGenerateOptionalFacts() throws Exception {
        System.out.println("UT-WRS-SE-036 : Test CIA Optional facts generation");
        HashMap<String, String> factsTerm = new HashMap<String, String>();
        murlinObj = new GenericMURLinJSONObject();
        HashMap<String, String> dynConf = new HashMap<String, String>();
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("wcs", "{\"category\":\"40\",\"layer\":\"wcs\",\"raw_url\":{\"domain\":\"google.com\",\"file\":\"\",\"query\":\"\",\"encode\":\"none\",\"path\":\"\",\"port\":80},\"cascaded\":{\"directory_level\":false,\"domain_level\":true},\"safetyrating\":81,\"normalized_url\":{\"domain\":\"gogle.com\",\"url\":\"http://google.com:80/\",\"file\":\"\",\"query\":\"\",\"encode\":\"none\",\"path\":\"\",\"port\":80}}");
        dynConf.put("result_fields", "{\"fields\":[\"wcs.category\",\"wcs.layer\",\"wcs.normalized_url.url\",\"wcs.normalized_url.encode\",\"wcs.normalized_url.port\",\"wcs.cascaded.domain_level\"]}");
        murlinObj.setDyn_conf(dynConf);
        murlinObj.setResults(result);
        actualOptionalFacts = new GenerateFacts(murlinObj).generate();
        String jsonReqFields = murlinObj.getDyn_conf().get("result_fields");
        factsTerm = generateFactsTerm(jsonReqFields);
        expectedOptionalFacts = generateExpectedOptionalFacts(factsTerm);
        System.out.println("Expected Result : " + expectedOptionalFacts.getOptionalFacts());
        System.out.println("Actual Result : " + actualOptionalFacts.getOptionalFacts());
        assertEquals(expectedOptionalFacts.getOptionalFacts(), actualOptionalFacts.getOptionalFacts());
        System.out.println("*** Passed");
    }

    private Map<String, String> generateFields(String[] fields) {
        ArrayList<String> _fields = new ArrayList<String>();
        ArrayList<String> _fieldf = new ArrayList<String>();

        _fields.addAll(Arrays.asList(fields));

        for (String str : _fields) {
            _fieldf.add("\"" + str + "\"");
        }

        Map<String, String> dyn = new HashMap<String, String>();
        String jsonsResultFields = "{\"fields\":" + _fieldf.toString().replaceAll("\\s+", "") + "}";
        dyn.put("result_fields", jsonsResultFields);

        return dyn;
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
            throw new CiaException(ex);
        }
        return validateNullValue(_jsonValue);
    }

    private HashMap<String, String> generateFactsTerm(String jsonReqFields) throws CiaException {
        HashMap<String, String> factsTerm = new HashMap<String, String>();
        JsonArray jsonarray = (JsonArray) ((JsonObject) new JsonParser().parse(jsonReqFields)).get("fields");
        ArrayList<String> fields = new ArrayList<String>();
        for (JsonElement s : jsonarray) {
            fields.add(s.getAsString());
        }
        for (String s : fields) {
            String[] _keyTree = s.split("\\.");
            String jsonModResult = murlinObj.getResults().get(_keyTree[0].trim());
            if (jsonModResult != null) {
                factsTerm.put(s, getValue(_keyTree, jsonModResult));
            }
        }

        return factsTerm;
    }

    private Facts generateExpectedOptionalFacts(HashMap<String, String> factsTerm) {
        Facts _expectedOptionalFacts = new Facts();
        try {
            DataTypeChecker dtypechckr;
            DataTypeChecker dtypechckrinr;
            for (Map.Entry<String, String> entry : factsTerm.entrySet()) {
                String key_term = entry.getKey();
                String value = validateNullValue(entry.getValue().trim().replaceAll("\\s+", ""));
                dtypechckr = new DataTypeChecker(value);
                if (dtypechckr.isArray()) {
                    String[] arrayValues = value.replaceAll("\"|\\[|\\]", "").split(",");
                    for (String val : arrayValues) {
                        dtypechckrinr = new DataTypeChecker(val);
                        if (dtypechckrinr.isInteger() || dtypechckr.isBoolean()) {
                            _expectedOptionalFacts.getOptionalFacts().add(constructFacts(key_term, val));
                        }
                        if (dtypechckrinr.isString()) {
                            _expectedOptionalFacts.getOptionalFacts().add(constructFacts(key_term, val));
                        }
                    }
                } else if (dtypechckr.isString()) {
                    _expectedOptionalFacts.getOptionalFacts().add(constructFacts(key_term, value));
                } else if (dtypechckr.isInteger() || dtypechckr.isBoolean()) {
                    _expectedOptionalFacts.getOptionalFacts().add(constructFacts(key_term, value));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return _expectedOptionalFacts;
    }
}
