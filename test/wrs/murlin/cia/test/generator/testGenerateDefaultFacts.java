/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrs.murlin.cia.test.generator;

import java.util.ArrayList;
import org.apache.log4j.BasicConfigurator;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import wrs.common.murlin.GenericMURLinJSONObject;
import wrs.murlin.cia.generator.GenerateFacts;
import wrs.murlin.cia.generator.impl.CIAGeneratorImpl;
import wrs.murlin.cia.test.data.DataGenerator;
import wrs.murlin.cia.theory.data.Facts;

/**
 *
 * @author faust
 */
public class testGenerateDefaultFacts extends CIAGeneratorImpl {

    private final static String COMPONENT_NAME = "cia";
    private final static String SOURCE_NAME_TEST = "unit-test-source-" + COMPONENT_NAME;
    private static GenericMURLinJSONObject murlinObj = new GenericMURLinJSONObject();
    private static DataGenerator dataGen = new DataGenerator();

    @BeforeClass
    public static void init() {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
        murlinObj = generateTestMURLinObject("roel.com");
    }

    @Test
    public void testGenerateDefaultFacts() throws Exception {
        System.out.println("UT-WRS-SE-041 : Test CIA Default facts generation");
        murlinObj = new GenericMURLinJSONObject();


        System.out.println("Test if MurlinObject will be properly converted to Facts");
        System.out.println("*********************");
        murlinObj.setSrcCHName("lj");
        ArrayList<String> wcsCat = new ArrayList<String>();
        wcsCat.add("76");
        wcsCat.add("79");
        murlinObj.setALPShitcnt("100");
        murlinObj.getCategory().setWcs(wcsCat);
        murlinObj.setWhiteListed(false);
        murlinObj.getUrl().setResolved_ip("65.87.66.55");
        murlinObj.getUrl().setRaw("sample.com");
        murlinObj.getUrl().setRaw_description("malware/Spam URL");
        murlinObj.getDbh().setCreator("murlin");
        murlinObj.getDbh().setOri_src("sqt.lj");
        murlinObj.getDbh().setInput_source("CTFAST");
        murlinObj.getCategory().setScore("60");
        GenerateFacts genDefaultFacts = new GenerateFacts(murlinObj);
        Facts actualGeneratedDefaultFacts = genDefaultFacts.generate();
        Facts expectedGeneratedDefaultFacts = new Facts();

        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(ALPS_HIT_CNT, murlinObj.getALPShitcnt()));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(CREATOR, murlinObj.getDbh().getCreator()));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(EWL_HIT, String.valueOf(murlinObj.isWhiteListed())));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(INPUT_SRC, murlinObj.getDbh().getInput_source()));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(ORI_SRC, murlinObj.getDbh().getOri_src()));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(RESOLVED_IP, murlinObj.getUrl().getResolved_ip()));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(SOURCE_NAME, murlinObj.getSrcCHName()));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(URL_DESC, murlinObj.getUrl().getRaw_description()));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(WCS_SCORE_TYPE, "malicious"));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(WCS_SCORE, "60"));
        expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(WCS_CASCADE, Boolean.FALSE.toString()));
        for (String wcs_result : murlinObj.getCategory().getWcs()) {
            expectedGeneratedDefaultFacts.getDefaultFacts().add(constructFacts(WCS_CAT, wcs_result));
        }
        boolean match = false;
        System.out.println("Expected Result : " + expectedGeneratedDefaultFacts.toString());
        System.out.println("Actual Result : " + actualGeneratedDefaultFacts.toString());
        for (String agf : actualGeneratedDefaultFacts.getDefaultFacts()) {
            for (String egf : expectedGeneratedDefaultFacts.getDefaultFacts()) {
                if (agf.trim().equalsIgnoreCase(egf.trim())) {
                    match = true;
                    break;
                } else {
                    match = false;
                }

            }
        }
        assertTrue(match);
        System.out.println("*** Passed : CIA Default facts generation was successfully tested");
    }

    private static GenericMURLinJSONObject generateTestMURLinObject(String url) {
        return dataGen.generateMURLinJSONObject(SOURCE_NAME_TEST, dataGen.generateURL(url));
    }
}
